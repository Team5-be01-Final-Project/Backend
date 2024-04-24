package com.sales.BPS.msales.service;

import com.sales.BPS.mproduct.entity.Voucher;
import com.sales.BPS.mproduct.repository.VoucherRepository;
import com.sales.BPS.msales.dto.ClientSalesDTO;
import com.sales.BPS.msales.dto.MonthlySalesDTO;
import com.sales.BPS.msales.dto.ProductSalesDTO;
import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
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
            List<Voucher> vouchers = voucherRepository.findByClientClientCodeAndYearAndMonth(client.getClientCode(), year, month);


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
            LocalDate appro = null;
            Integer proUnit  = vouchersForProduct.get(0).getProduct().getProUnit();
            for (Voucher voucher : vouchersForProduct) {
                appro = voucher.getVoucApproval();
                totalVoucAmount += voucher.getVoucAmount();
                totalVoucSales += (long) voucher.getVoucSale() * voucher.getVoucAmount();
                totalCostOfSales += (long) voucher.getProduct().getProUnit() * voucher.getVoucAmount();
            }
            long totalGrossProfit = totalVoucSales - totalCostOfSales;
            double profitMargin = totalVoucSales > 0 ? (double) totalGrossProfit / totalVoucSales * 100 : 0;

            ProductSalesDTO aggregatedProduct = new ProductSalesDTO();
            aggregatedProduct.setProName(proName);
            aggregatedProduct.setProUnit(proUnit);
            aggregatedProduct.setVoucApproval(appro);
            aggregatedProduct.setVoucAmount(totalVoucAmount);
            aggregatedProduct.setVoucSales(totalVoucSales);
            aggregatedProduct.setGrossProfit(totalGrossProfit);
            aggregatedProduct.setCostOfSales((int) totalCostOfSales);
            aggregatedProduct.setProfitMargin(profitMargin);

            aggregatedProducts.add(aggregatedProduct);
        }

        return aggregatedProducts;
    }

    public List<MonthlySalesDTO> getMonthlySales(int year) {
        List<Voucher> vouchers = voucherRepository.findAllByYear(year);
        Map<YearMonth, List<Voucher>> monthlyVouchers = vouchers.stream()
                .collect(Collectors.groupingBy(voucher -> YearMonth.from(voucher.getVoucApproval())));

        List<MonthlySalesDTO> monthlySalesList = new ArrayList<>();

        for (Map.Entry<YearMonth, List<Voucher>> entry : monthlyVouchers.entrySet()) {
            YearMonth yearMonth = entry.getKey();
            List<Voucher> monthlyVoucherList = entry.getValue();

            long totalVoucSales = 0;
            long totalCostOfSales = 0;

            for (Voucher voucher : monthlyVoucherList) {
                long voucSales = voucher.getVoucSales();
                long costOfSales = voucher.getProduct().getProUnit() * voucher.getVoucAmount();

                totalVoucSales += voucSales;
                totalCostOfSales += costOfSales;
            }

            long totalGrossProfit = totalVoucSales - totalCostOfSales;

            MonthlySalesDTO monthlySalesDTO = new MonthlySalesDTO();
            monthlySalesDTO.setYearMonth(yearMonth);
            monthlySalesDTO.setVoucSales(totalVoucSales);
            monthlySalesDTO.setTotalGrossProfit(totalGrossProfit);

            monthlySalesList.add(monthlySalesDTO);
        }

        return monthlySalesList;
    }

    // My Sales 내 매출 보기 기능을 위한 메서드
    public List<ClientSalesDTO> getEmployeeSalesData(Integer empCode, int year, int month) {
        List<ClientSalesDTO> employeeSalesData = new ArrayList<>();
        List<Client> clients = clientRepository.findByEmployeeEmpCode(empCode);

        // 최근 3개월 계산
        LocalDate currentDate = LocalDate.of(year, month, 1);
        LocalDate[] recentMonths = new LocalDate[3];
        for (int i = 0; i < 3; i++) {
            recentMonths[i] = currentDate.minusMonths(2 - i);
        }

        for (Client client : clients) {
            ClientSalesDTO dto = new ClientSalesDTO();
            dto.setClientName(client.getClientName());

            // 최근 3개월의 매출액 계산
            Long[] monthlySales = new Long[3];
            for (int i = 0; i < 3; i++) {
                LocalDate targetMonth = recentMonths[i];
                List<Voucher> vouchers = voucherRepository.findByClientClientCodeAndYearAndMonthAndApprovalCodeAppCode(
                        client.getClientCode(),
                        targetMonth.getYear(),
                        targetMonth.getMonthValue(),
                        "A01"       // 승인 코드를 가진 전표만 매출로 집계
                );

                long totalSales = vouchers.stream()
                        .mapToLong(voucher -> (long) voucher.getVoucSale() * voucher.getVoucAmount())
                        .sum();

                monthlySales[i] = totalSales;
            }

            dto.setMonthlySales(monthlySales);
            employeeSalesData.add(dto);
        }

        return employeeSalesData;
    }
}