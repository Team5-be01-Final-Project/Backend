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
import com.sales.BPS.msystem.repository.DepartmentRepository;
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

    private final DepartmentRepository departmentRepository;


    @Autowired

    public VoucherService(VoucherRepository voucherRepository, StockRepository stockRepository, ApprovalCodeRepository approvalCodeRepository, EmployeeRepository employeeRepository, ProductRepository productRepository, ClientRepository clientRepository, DepartmentRepository departmentRepository, StockService stockService) {

        this.voucherRepository = voucherRepository;
        this.stockRepository = stockRepository;
        this.approvalCodeRepository = approvalCodeRepository;
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;

        this.departmentRepository = departmentRepository;

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
        dto.setDeptCode(voucher.getEmployee().getDepartment().getDeptCode()); // 부서 코드 설정

        return dto;
    }

    private void updateStock(Stock stock, int amountChange) {
        int updatedAmount = stock.getStoAmo() + amountChange;
        if (updatedAmount < 0) {
            throw new RuntimeException("Insufficient stock");
        }
        stock.setStoAmo(updatedAmount);
        stockRepository.save(stock);
    }

    public void approveVoucherDetails(Long voucId, String remarks) {
        List<Voucher> vouchers = voucherRepository.findByVoucId(voucId);
        for (Voucher voucher : vouchers) {
            voucher.setApprovalCode(approvalCodeRepository.findById("A01").orElseThrow(() -> new RuntimeException("Approval code not found")));
            voucher.setVoucApproval(LocalDate.now()); // 현재 날짜 설정
            voucher.setVoucNote(remarks); // 비고 업데이트
            // Update other fields as needed
            voucherRepository.save(voucher);
        }
    }


    @Transactional
    public void rejectVoucherDetails(Long voucId, String remarks) {
        List<Voucher> vouchers = voucherRepository.findByVoucId(voucId);
        for (Voucher voucher : vouchers) {
            // 출고전표 반려 처리
            voucher.setApprovalCode(approvalCodeRepository.findById("A02").orElseThrow(() -> new RuntimeException("Approval code not found")));
            voucher.setVoucApproval(LocalDate.now()); // 현재 날짜 설정
            voucher.setVoucNote(remarks); // 비고 업데이트
            // Update other fields as needed
            voucherRepository.save(voucher);

            // 상품의 재고량을 출고전표의 수량만큼 증가시킴
            Product product = productRepository.findById(voucher.getProCode()).orElseThrow(() -> new RuntimeException("Product not found"));
            // 상품의 재고량을 출고전표의 수량만큼 증가시킴
            Stock stock = stockRepository.findById(product.getProCode()).orElseThrow(() -> new RuntimeException("Stock not found"));


            if (stock != null) {
                int amountChange = voucher.getVoucAmount(); // 출고전표의 수량만큼 변경
                updateStock(stock, amountChange);
            } else {
                throw new RuntimeException("Stock not found");
            }
        }
    }


        @Transactional
        public void createVouchers(VoucherSaveDTO voucherSaveDTO){
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

    // 검색 조건에 맞는 출고전표를 조회
    public List<VoucherDTO> searchVouchers(String voucId, String empName, String clientName, LocalDate startDate, LocalDate endDate, String signerName, String approvalStatus, LocalDate approvalStartDate, LocalDate approvalEndDate) {
        // VoucherRepository의 searchVouchers 메서드 호출하여 검색 조건에 맞는 출고전표 엔티티 목록 조회
        List<Voucher> vouchers = voucherRepository.searchVouchers(voucId, empName, clientName, startDate, endDate, signerName, approvalStatus, approvalStartDate, approvalEndDate);
        // 조회된 출고전표 엔티티 목록을 VoucherDTO 목록으로 변환하여 반환
        return vouchers.stream().map(this::convertToDto).collect(Collectors.toList());
    }

}
