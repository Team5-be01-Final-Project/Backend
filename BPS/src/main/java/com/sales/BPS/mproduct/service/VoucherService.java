package com.sales.BPS.mproduct.service;

import com.sales.BPS.mproduct.dto.VoucherDTO;
import com.sales.BPS.mproduct.dto.VoucherDto;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final StockRepository stockRepository;
    private final ApprovalCodeRepository approvalCodeRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository, StockRepository stockRepository, ApprovalCodeRepository approvalCodeRepository, EmployeeRepository employeeRepository, ProductRepository productRepository, ClientRepository clientRepository) {
        this.voucherRepository = voucherRepository;
        this.stockRepository = stockRepository;
        this.approvalCodeRepository = approvalCodeRepository;
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
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


    private Voucher findVoucherById(Long voucId, Integer proCode) {
        Optional<Voucher> voucher = voucherRepository.findById(new VoucherPK(voucId, proCode));
        if (voucher.isPresent()) {
            return voucher.get();
        } else {
            throw new RuntimeException("Voucher not found");
        }
    }


    private Stock findStockById(Integer proCode) {
        Optional<Stock> stock = stockRepository.findById(proCode);
        if (stock.isPresent()) {
            return stock.get();
        }
        return stock.get();///////////////////////////////////////////////////////////////////////////!!!!!!!!!!!
    }


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

                // Update other fields as needed
                voucherRepository.save(voucher);
            }
        }


        public void rejectVoucherDetails (Long voucId){
            List<Voucher> vouchers = voucherRepository.findByVoucId(voucId);
            for (Voucher voucher : vouchers) {
                voucher.setApprovalCode(approvalCodeRepository.findById("A02").orElseThrow(() -> new RuntimeException("Approval code not found")));

                // Update other fields as needed
                voucherRepository.save(voucher);
            }
        }
        @Transactional//전표 생성
        public void createVoucher (VoucherDto voucherDto){
            Voucher voucher = new Voucher();
            voucher.setVoucId(voucherDto.getVoucId());
            Product product = productRepository.findById(voucherDto.getProCode()).orElseThrow();
            voucher.setProduct(product);
            voucher.setVoucDate(voucherDto.getVoucDate());
            voucher.setVoucSale(voucherDto.getVoucSale());
            voucher.setVoucAmount(voucherDto.getVoucAmount());
            voucher.setVoucSales(voucherDto.getVoucSales());
//        voucher.setVoucApproval(voucherDto.getVoucApproval());
            Client client = clientRepository.findById(voucherDto.getClientCode()).orElseThrow();
            voucher.setClient(client);
            ApprovalCode approvalCode = approvalCodeRepository.findById(voucherDto.getApprovalCode()).orElseThrow();
            voucher.setApprovalCode(approvalCode);
            Employee employee = employeeRepository.findById(voucherDto.getEmpCode()).orElseThrow();
            voucher.setEmployee(employee);
            Employee signer = employeeRepository.findById(voucherDto.getSignerCode()).orElseThrow();
            voucher.setEmployeeSign(signer);
//        voucher.setVoucNote(voucherDto.getVoucNote());
            voucherRepository.save(voucher);
        }

    }
