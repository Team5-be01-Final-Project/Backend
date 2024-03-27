package com.sales.BPS.msales.service;

import com.sales.BPS.mproduct.entity.Voucher;
import com.sales.BPS.mproduct.repository.VoucherRepository;
import com.sales.BPS.msales.dto.ClientSalesDTO;
import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesService {

    private final VoucherRepository voucherRepository;
    private final ClientRepository clientRepository;

    public SalesService(VoucherRepository voucherRepository, ClientRepository clientRepository) {
        this.voucherRepository = voucherRepository;
        this.clientRepository = clientRepository;
    }

    public List<ClientSalesDTO> getAllSalesData() {
        List<ClientSalesDTO> allSalesData = new ArrayList<>();
        List<Client> clients = clientRepository.findAll();

        for (Client client : clients) {
            List<Voucher> vouchers = voucherRepository.findByClient(client);


            for (Voucher voucher : vouchers) {
                ClientSalesDTO dto = new ClientSalesDTO();
                dto.setClientName(client.getClientName());
                dto.setProName(voucher.getProduct().getProName());
                dto.setProUnit(voucher.getProduct().getProUnit());
                dto.setVoucSale(voucher.getVoucSale());
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
}
