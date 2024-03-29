package com.sales.BPS.mproduct.controller;

import com.sales.BPS.mproduct.dto.VoucherApprovalDTO;
import com.sales.BPS.mproduct.dto.VoucherDTO;
import com.sales.BPS.mproduct.service.VoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/vouchers")
@Tag(name = "Voucher API", description = "출고관리 API입니다.")
public class VoucherController {

    private final VoucherService voucherService;

    @Autowired
    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    @Tag(name = "Voucher API")
    @Operation(summary = "출고전표조회",description = "출고 전표를 조회합니다.")
    public ResponseEntity<List<VoucherDTO>> getAllVouchers() {
        List<VoucherDTO> vouchers = voucherService.getAllVouchers();
        return ResponseEntity.ok(vouchers);
    }

    // 특정 voucId에 대한 출고전표 목록을 조회하는 엔드포인트
    @GetMapping("/{voucId}/details")
    @Tag(name = "Voucher API")
    @Operation(summary = "출고전표조회",description = "특정 voucID에 대한 출고 전표를 조회합니다.")
    public ResponseEntity<List<VoucherDTO>> getVoucherDetailsByVoucId(@PathVariable Long voucId) {
        List<VoucherDTO> dtoList = voucherService.findVouchersByVoucIdAsDto(voucId);
        if (dtoList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dtoList);
    }

/*    @PutMapping("/{voucId}/approve")
    public ResponseEntity<Void> approveVoucher(@PathVariable Long voucId, @RequestBody VoucherApprovalDTO request) {
        try {
            voucherService.approveVoucher(voucId, request);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Voucher not found")) {
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }

    @PutMapping("/{voucId}/reject")
    public ResponseEntity<Void> rejectVoucher(@PathVariable Long voucId, @RequestBody VoucherApprovalDTO request) {
        try {
            voucherService.rejectVoucher(voucId, request);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Voucher not found")) {
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }*/

    @PutMapping("/{voucId}/approve")
    public ResponseEntity<VoucherDTO> approveVoucher(@PathVariable Long voucId, @RequestBody VoucherApprovalDTO request) {
        try {
            VoucherDTO updatedVoucher = voucherService.approveVoucher(voucId, request);
            return ResponseEntity.ok(updatedVoucher);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{voucId}/reject")
    public ResponseEntity<VoucherDTO> rejectVoucher(@PathVariable Long voucId, @RequestBody VoucherApprovalDTO request) {
        try {
            VoucherDTO updatedVoucher = voucherService.rejectVoucher(voucId, request);
            return ResponseEntity.ok(updatedVoucher);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
