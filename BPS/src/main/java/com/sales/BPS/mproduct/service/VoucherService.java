package com.sales.BPS.mproduct.service;

import com.sales.BPS.mproduct.dto.VoucherApprovalDTO;
import com.sales.BPS.mproduct.dto.VoucherDTO;
import com.sales.BPS.mproduct.entity.Stock;
import com.sales.BPS.mproduct.entity.Voucher;
import com.sales.BPS.mproduct.entity.VoucherPK;
import com.sales.BPS.mproduct.repository.ApprovalCodeRepository;
import com.sales.BPS.mproduct.repository.StockRepository;
import com.sales.BPS.mproduct.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.BPS.msystem.repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<VoucherDTO> getAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public VoucherDTO mapToDTO(Voucher voucher) {
        VoucherDTO dto = new VoucherDTO();

        if (voucher != null) {
            dto.setVoucId(voucher.getVoucId());
            dto.setProName(voucher.getProduct() != null ? voucher.getProduct().getProName() : null); // 제품 엔티티에서 제품명 추출, 널 체크 추가
            dto.setVoucDate(voucher.getVoucDate());
            dto.setVoucSale(Math.toIntExact(voucher.getVoucSale()));
            dto.setVoucAmount(voucher.getVoucAmount());
            dto.setVoucSales(Long.valueOf(voucher.getVoucSales()));
            dto.setVoucApproval(voucher.getVoucApproval());
            dto.setClientName(voucher.getClient() != null ? voucher.getClient().getClientName() : null); // 클라이언트 엔티티에서 거래처명 추출, 널 체크 추가
            dto.setEmpName(voucher.getEmployee() != null ? voucher.getEmployee().getEmpName() : null); // 담당자 엔티티에서 이름 추출, 널 체크 추가
            dto.setSignerName(voucher.getEmployeeSign() != null ? voucher.getEmployeeSign().getEmpName() : null); // 결재자 엔티티에서 이름 추출, 널 체크 추가
            dto.setVoucNote(voucher.getVoucNote());
            dto.setApprovalStatus(voucher.getApprovalCode() != null ? voucher.getApprovalCode().getAppName() : null); // 승인 코드 엔티티에서 승인 상태 이름 추출, 널 체크 추가
        }

        return dto;
    }

    private VoucherDTO convertToDto(Voucher voucher) {
        VoucherDTO dto = new VoucherDTO();
        dto.setVoucId(voucher.getVoucId());
        dto.setEmpName(voucher.getEmployee() != null ? voucher.getEmployee().getEmpName() : null);
        dto.setClientName(voucher.getClient() != null ? voucher.getClient().getClientName() : null);
        dto.setProName(voucher.getProduct() != null ? voucher.getProduct().getProName() : null);
        dto.setVoucDate(voucher.getVoucDate() != null ? LocalDate.parse(voucher.getVoucDate().toString()) : null); // LocalDate를 String으로 변환
        dto.setVoucSale(Math.toIntExact(voucher.getVoucSale()));
        dto.setVoucAmount(voucher.getVoucAmount());
        dto.setVoucSales(Long.valueOf(voucher.getVoucSales()));
        dto.setVoucApproval(voucher.getVoucApproval() != null ? LocalDate.parse(voucher.getVoucApproval().toString()) : null); // LocalDate를 String으로 변환
        dto.setSignerName(voucher.getEmployeeSign() != null ? voucher.getEmployeeSign().getEmpName() : null);
        dto.setVoucNote(voucher.getVoucNote());
        dto.setApprovalStatus(voucher.getApprovalCode() != null ? voucher.getApprovalCode().getAppName() : null);
        if (voucher.getEmployee() != null && voucher.getEmployee().getStorage() != null) {
            dto.setStorageCar(voucher.getEmployee().getStorage().getStorageCar());
        }
        return dto;
    }


    public List<VoucherDTO> findVouchersByVoucIdAsDto(Long voucId) {
        List<Voucher> vouchers = voucherRepository.findByVoucId(voucId);
        return vouchers.stream().map(this::convertToDto).collect(Collectors.toList());

    }

    // 매출액 계산 로직
    private void calculateVoucSales(Voucher voucher) {
        if (voucher.getVoucSale() != null && voucher.getVoucAmount() != null) {
            Long sales = ((long) voucher.getVoucSale() * voucher.getVoucAmount());
            voucher.setVoucSales(sales);
        }
    }

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ApprovalCodeRepository approvalCodeRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public void approveVoucher(Long voucId, VoucherApprovalDTO request) {
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

        // PPC 테이블에 판매 정보 저장 로직 추가
        // ...

        voucher.setApprovalCode(approvalCodeRepository.findById("A01").orElse(null));
        voucher.setEmployeeSign(employeeRepository.findById(request.getEmpCode()).orElse(null));
        voucher.setVoucApproval(LocalDate.now());
        voucherRepository.save(voucher);
    }

    public void rejectVoucher(Long voucId, VoucherApprovalDTO request) {
        VoucherPK voucherPK = new VoucherPK();
        voucherPK.setVoucId(voucId);
        voucherPK.setProduct(request.getProCode());

        Voucher voucher = voucherRepository.findById(voucherPK)
                .orElseThrow(() -> new RuntimeException("Voucher not found"));

        if (!voucher.getProduct().getProCode().equals(request.getProCode())) {
            throw new RuntimeException("Invalid product code");
        }

        voucher.setApprovalCode(approvalCodeRepository.findById("A02").orElse(null));
        voucher.setEmployeeSign(employeeRepository.findById(request.getEmpCode()).orElse(null));
        voucher.setVoucApproval(LocalDate.now());
        voucherRepository.save(voucher);
    }

}
