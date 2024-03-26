package com.sales.BPS.mproduct.service;

import com.sales.BPS.mproduct.dto.VoucherDTO;
import com.sales.BPS.mproduct.entity.Voucher;
import com.sales.BPS.mproduct.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
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
            dto.setVoucSale(voucher.getVoucSale());
            dto.setVoucAmount(voucher.getVoucAmount());
            dto.setVoucSales(voucher.getVoucSales());
            dto.setVoucApproval(voucher.getVoucApproval());
            dto.setClientName(voucher.getClient() != null ? voucher.getClient().getClientName() : null); // 클라이언트 엔티티에서 거래처명 추출, 널 체크 추가
            dto.setEmpName(voucher.getEmployee() != null ? voucher.getEmployee().getEmpName() : null); // 담당자 엔티티에서 이름 추출, 널 체크 추가
            dto.setSignerName(voucher.getEmployeeSign() != null ? voucher.getEmployeeSign().getEmpName() : null); // 결재자 엔티티에서 이름 추출, 널 체크 추가
            dto.setVoucNote(voucher.getVoucNote());
            dto.setApprovalStatus(voucher.getApprovalCode() != null ? voucher.getApprovalCode().getAppName() : null); // 승인 코드 엔티티에서 승인 상태 이름 추출, 널 체크 추가
        }

        return dto;
    }

}
