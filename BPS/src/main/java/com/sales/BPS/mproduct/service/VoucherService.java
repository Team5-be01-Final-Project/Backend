package com.sales.BPS.mproduct.service;

import com.sales.BPS.mproduct.dto.VoucherDTO;
import com.sales.BPS.mproduct.dto.VoucherSaveDTO;
import com.sales.BPS.mproduct.entity.*;
import com.sales.BPS.mproduct.repository.ApprovalCodeRepository;
import com.sales.BPS.mproduct.repository.ProductRepository;
import com.sales.BPS.mproduct.repository.StockRepository;
import com.sales.BPS.mproduct.repository.VoucherRepository;
import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.repository.ClientRepository;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final StockRepository stockRepository;
    private final ApprovalCodeRepository approvalCodeRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final StockService stockService;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository, StockRepository stockRepository, ApprovalCodeRepository approvalCodeRepository, EmployeeRepository employeeRepository, ProductRepository productRepository, ClientRepository clientRepository, StockService stockService) {
        this.voucherRepository = voucherRepository;
        this.stockRepository = stockRepository;
        this.approvalCodeRepository = approvalCodeRepository;
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.stockService = stockService;
    }


    public List<VoucherDTO> getAllVouchers() {
        return voucherRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 특정 voucId에 해당하는 Voucher 엔티티들을 조회하고, 그 결과를 VoucherDTO로 변환하여 반환
    public List<VoucherDTO> findVouchersByVoucIdAsDto(Long voucId) {
        List<Voucher> vouchers = voucherRepository.findByVoucId(voucId);
        return vouchers.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private VoucherDTO convertToDto(Voucher voucher) {
        VoucherDTO dto = new VoucherDTO();
        dto.setVoucId(voucher.getVoucId());
        dto.setProCode(voucher.getProduct().getProCode());
        dto.setProName(voucher.getProduct().getProName());
        dto.setVoucDate(voucher.getVoucDate());
        dto.setVoucSale(voucher.getVoucSale());
        dto.setVoucAmount(voucher.getVoucAmount());
        dto.setVoucSales(voucher.getVoucSales());
        dto.setVoucApproval(voucher.getVoucApproval());
        dto.setClientName(voucher.getClient().getClientName());
        dto.setEmpName(voucher.getEmployee().getEmpName());
        dto.setSignerName(voucher.getEmployeeSign().getEmpName());
        dto.setVoucNote(voucher.getVoucNote());

        // 수정 시작
        String appCode = voucher.getApprovalCode().getAppCode();
        dto.setAppCode(appCode != null ? appCode : "A00");
        dto.setApprovalStatus(appCode != null ? voucher.getApprovalCode().getAppName() : "대기중");
        dto.setShowApproveButton("A00".equals(appCode));
        dto.setShowRejectButton("A00".equals(appCode));
        // 수정 끝

        return dto;
    }


/*    private Voucher findVoucherById(Long voucId, Integer proCode) {
        Optional<Voucher> voucher = voucherRepository.findById(new VoucherPK(voucId, proCode));
        if (voucher.isPresent()) {
            return voucher.get();
        } else {
            throw new RuntimeException("Voucher not found");
        }
    }*/

/*
    private Stock findStockById(Integer proCode) {
        Optional<Stock> stock = stockRepository.findById(proCode);
        if (stock.isPresent()) {
            return stock.get();
        }
        return stock.get();///////////////////////////////////////////////////////////////////////////!!!!!!!!!!!
    }*/


   /* public void approveVoucher(Long voucId, VoucherApprovalDTO request) {
        VoucherPK voucherPK = new VoucherPK();
        voucherPK.setVoucId(voucId);
        voucherPK.setProduct(request.getProCode());

        Voucher voucher = voucherRepository.findById(voucherPK)
                .orElseThrow(() -> new RuntimeException("Voucher not found"));

        // 재고 차감 로직 수정
        Optional<Stock> stockOptional = stockRepository.findById(request.getProCode());
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            int remainingStock = stock.getStoAmo() - voucher.getVoucAmount();
            if (remainingStock < 0) {
                throw new RuntimeException("Insufficient stock");
            }
            stock.setStoAmo(remainingStock);
            stockRepository.save(stock);

        } else {
            throw new RuntimeException("Stock not found");
        }
    }*/

        private void updateStock (Stock stock,int amountChange){
            int updatedAmount = stock.getStoAmo() + amountChange;
            if (updatedAmount < 0) {
                throw new RuntimeException("Insufficient stock");
            }
            stock.setStoAmo(updatedAmount);
            stockRepository.save(stock);
        }

        public void approveVoucherDetails (Long voucId){
            List<Voucher> vouchers = voucherRepository.findByVoucId(voucId);
            for (Voucher voucher : vouchers) {
                voucher.setApprovalCode(approvalCodeRepository.findById("A01").orElseThrow(() -> new RuntimeException("Approval code not found")));
                voucher.setVoucApproval(LocalDate.now()); // 현재 날짜 설정
                // Update other fields as needed
                voucherRepository.save(voucher);
            }
        }


        public void rejectVoucherDetails (Long voucId){
            List<Voucher> vouchers = voucherRepository.findByVoucId(voucId);
            for (Voucher voucher : vouchers) {
                voucher.setApprovalCode(approvalCodeRepository.findById("A02").orElseThrow(() -> new RuntimeException("Approval code not found")));
                voucher.setVoucApproval(LocalDate.now()); // 현재 날짜 설정
                // Update other fields as needed
                voucherRepository.save(voucher);
            }
        }

        @Transactional
        public void createVouchers(VoucherSaveDTO voucherSaveDTO) {
            Long voucId = voucherSaveDTO.getVoucId();
            LocalDate voucDate = voucherSaveDTO.getVoucDate();
            Employee employee = employeeRepository.findById(voucherSaveDTO.getEmpCode()).orElseThrow();
            Employee signer = employeeRepository.findById(voucherSaveDTO.getSignerCode()).orElseThrow();
            Client client = clientRepository.findById(voucherSaveDTO.getClientCode()).orElseThrow();
            ApprovalCode approvalCode = approvalCodeRepository.findById(voucherSaveDTO.getApprovalCode()).orElseThrow();

            for (VoucherSaveDTO.VoucherItem item : voucherSaveDTO.getItems()) {
                Voucher voucher = new Voucher();
                voucher.setVoucId(voucId);
                voucher.setVoucDate(voucDate);
                voucher.setEmployee(employee);
                voucher.setEmployeeSign(signer);
                voucher.setClient(client);
                voucher.setApprovalCode(approvalCode);

                voucher.setProCode(item.getProCode());
                voucher.setVoucSale(item.getVoucSale());
                voucher.setVoucAmount(item.getVoucAmount());
                voucher.setVoucSales(item.getVoucSales());

                stockService.decreaseStock(item.getProCode(), item.getVoucAmount());
                voucherRepository.save(voucher);
            }
        }
    }
