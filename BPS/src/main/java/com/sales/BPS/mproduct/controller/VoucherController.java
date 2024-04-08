package com.sales.BPS.mproduct.controller;

import com.sales.BPS.mproduct.dto.VoucherDTO;
import com.sales.BPS.mproduct.dto.VoucherSaveDTO;
import com.sales.BPS.mproduct.service.SequenceService;
import com.sales.BPS.mproduct.service.VoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vouchers")
@Tag(name = "Voucher API", description = "출고관리 API입니다.")
public class VoucherController {

    private final VoucherService voucherService;
    private final SequenceService sequenceService;

    @Autowired
    public VoucherController(VoucherService voucherService, SequenceService sequenceService) {
        this.voucherService = voucherService;
        this.sequenceService = sequenceService;
    }

    @GetMapping
    @Tag(name = "Voucher API")
    @Operation(summary = "출고전표조회",description = "출고 전표를 조회합니다.")
    public ResponseEntity<List<VoucherDTO>> getAllVouchers() {
        List<VoucherDTO> vouchers = voucherService.getAllVouchers();
        return ResponseEntity.ok(vouchers);
    }

    // 특정 voucId에 대한 출고전표 목록을 조회하는 엔드포인트
    // 특정 출고전표의 세부 정보 조회
    @GetMapping("/{voucId}/details")
    public ResponseEntity<List<VoucherDTO>> getVoucherDetailsByVoucId(@PathVariable Long voucId) {
        List<VoucherDTO> dtoList = voucherService.findVouchersByVoucIdAsDto(voucId);
        return dtoList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(dtoList);
    }
    

    @PutMapping("/{voucId}/reject/details")
    public ResponseEntity<Void> rejectVoucherDetails(@PathVariable Long voucId) {
        voucherService.rejectVoucherDetails(voucId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{voucId}/approve/details")
    public ResponseEntity<Void> approveVoucherDetails(@PathVariable Long voucId) {
        voucherService.approveVoucherDetails(voucId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/voucId") //전표 번호 생성
    public ResponseEntity<?> generateVoucherId() {
        Map<String, Long> voucId = new HashMap<>();
        voucId.put("voucId", sequenceService.generateVoucherId());
        return ResponseEntity.ok(voucId);
    }

    @PostMapping("/saveall") // 여러 전표 저장
    public ResponseEntity<?> createVouchers(@RequestBody VoucherSaveDTO VoucherSaveDTO) {
        System.out.println("VoucherSaveDTO: " + VoucherSaveDTO);
        try {
            voucherService.createVouchers(VoucherSaveDTO);
            return new ResponseEntity<>("Vouchers created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
