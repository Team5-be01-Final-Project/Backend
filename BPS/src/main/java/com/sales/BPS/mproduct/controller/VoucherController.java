package com.sales.BPS.mproduct.controller;

import com.sales.BPS.mproduct.dto.VoucherApprovalDTO;
import com.sales.BPS.mproduct.dto.VoucherDTO;
import com.sales.BPS.mproduct.entity.Voucher;
import com.sales.BPS.mproduct.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    @Autowired
    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<List<VoucherDTO>> getAllVouchers() {
        List<VoucherDTO> vouchers = voucherService.getAllVouchers();
        return ResponseEntity.ok(vouchers);
    }

    // 특정 voucId에 대한 출고전표 목록을 조회하는 엔드포인트
    @GetMapping("/{voucId}/details")
    public ResponseEntity<List<VoucherDTO>> getVoucherDetailsByVoucId(@PathVariable Long voucId) {
        List<VoucherDTO> dtoList = voucherService.findVouchersByVoucIdAsDto(voucId);
        if (dtoList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dtoList);
    }

    @PutMapping("/{voucId}/approve")
    public ResponseEntity<Void> approveVoucher(@PathVariable Long voucId, @RequestBody VoucherApprovalDTO request) {
        voucherService.approveVoucher(voucId, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{voucId}/reject")
    public ResponseEntity<Void> rejectVoucher(@PathVariable Long voucId, @RequestBody VoucherApprovalDTO request) {
        voucherService.rejectVoucher(voucId, request);
        return ResponseEntity.ok().build();
    }

}
