package com.sales.BPS.msales.service;

import com.sales.BPS.mproduct.entity.Voucher;
import com.sales.BPS.mproduct.repository.VoucherRepository;
import com.sales.BPS.msales.dto.ClientSalesDTO;
import com.sales.BPS.msales.dto.ProductSalesDTO;
import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SalesService {

    private final VoucherRepository voucherRepository;
    private final ClientRepository clientRepository;

    public SalesService(VoucherRepository voucherRepository, ClientRepository clientRepository) {
        this.voucherRepository = voucherRepository;
        this.clientRepository = clientRepository;
    }

    //거래처별 매출조회
    public List<ClientSalesDTO> getAllSalesData(int year,int month) {
        List<ClientSalesDTO> allSalesData = new ArrayList<>();
        List<Client> clients = clientRepository.findAll();

        for (Client client : clients) {
            List<Voucher> vouchers = voucherRepository.findByYearAndMonth(year,month);


            for (Voucher voucher : vouchers) {
                ClientSalesDTO dto = new ClientSalesDTO();
                dto.setClientName(client.getClientName());
                dto.setProName(voucher.getProduct().getProName());
                dto.setProUnit(voucher.getProduct().getProUnit());
                dto.setVoucSale(voucher.getVoucSale());
                dto.setVoucApproval(voucher.getVoucApproval());
                dto.setVoucAmount(voucher.getVoucAmount());
                dto.setCostOfSales(voucher.getProduct().getProUnit() * voucher.getVoucAmount());
                dto.setVoucSales((long) voucher.getVoucSale() * voucher.getVoucAmount());
                dto.setGrossProfit(dto.getVoucSales() - dto.getCostOfSales());
                dto.setProfitMargin((dto.getGrossProfit() * 100.0) / dto.getVoucSales());

                allSalesData.add(dto);

            }

        }
        return allSalesData;
    }

    //판매상품별 매출조회
    public List<ProductSalesDTO> aggregateSalesByProduct(int year, int month) {
        List<Voucher> vouchers = voucherRepository.findByYearAndMonth(year,month);
        Map<String, List<Voucher>> groupedByProductName = new HashMap<>();

        // 상품 이름을 기준으로 데이터를 그룹화합니다.
        for (Voucher voucher : vouchers) {
            String proName = voucher.getProduct().getProName();
            groupedByProductName.putIfAbsent(proName, new ArrayList<>());
            groupedByProductName.get(proName).add(voucher);
        }

        List<ProductSalesDTO> aggregatedProducts = new ArrayList<>();
        for (Map.Entry<String, List<Voucher>> entry : groupedByProductName.entrySet()) {
            String proName = entry.getKey();
            List<Voucher> vouchersForProduct = entry.getValue();

            int totalVoucAmount = 0;
            long totalVoucSales = 0;
            long totalCostOfSales = 0;
            Integer proUnit  = vouchersForProduct.get(0).getProduct().getProUnit();
            for (Voucher voucher : vouchersForProduct) {
                totalVoucAmount += voucher.getVoucAmount();
                totalVoucSales += (long) voucher.getVoucSale() * voucher.getVoucAmount();
                totalCostOfSales += (long) voucher.getProduct().getProUnit() * voucher.getVoucAmount();
            }
            long totalGrossProfit = totalVoucSales - totalCostOfSales;
            double profitMargin = totalVoucSales > 0 ? (double) totalGrossProfit / totalVoucSales * 100 : 0;

            ProductSalesDTO aggregatedProduct = new ProductSalesDTO();
            aggregatedProduct.setProName(proName);
            aggregatedProduct.setProUnit(proUnit);
            aggregatedProduct.setVoucAmount(totalVoucAmount);
            aggregatedProduct.setVoucSales(totalVoucSales);
            aggregatedProduct.setGrossProfit(totalGrossProfit);
            aggregatedProduct.setCostOfSales((int) totalCostOfSales);
            aggregatedProduct.setProfitMargin(profitMargin);

            aggregatedProducts.add(aggregatedProduct);
        }

        return aggregatedProducts;
    }
}