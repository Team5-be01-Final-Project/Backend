package com.sales.BPS.mproduct.controller;

import com.sales.BPS.mexception.InsufficientStockException;
import com.sales.BPS.mproduct.dto.VoucherDTO;
import com.sales.BPS.mproduct.dto.VoucherSaveDTO;
import com.sales.BPS.mproduct.service.SequenceService;
import com.sales.BPS.mproduct.service.VoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    @Operation(summary = "출고전표조회", description = "출고 전표를 조회합니다.")
    public ResponseEntity<List<VoucherDTO>> getAllVouchers() {
        List<VoucherDTO> vouchers = voucherService.getAllVouchers();
        return ResponseEntity.ok(vouchers);
    }

    // 특정 voucId에 대한 출고전표 목록을 조회하는 엔드포인트
    // 특정 출고전표의 세부 정보 조회
    @GetMapping("/{voucId}/details")
    @Tag(name = "Voucher API")
    @Operation(summary = "출고전표조회", description = "특정 voucId에 대한 출고 전표를 조회합니다.")
    public ResponseEntity<List<VoucherDTO>> getVoucherDetailsByVoucId(@PathVariable Long voucId) {
        List<VoucherDTO> dtoList = voucherService.findVouchersByVoucIdAsDto(voucId);
        return dtoList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(dtoList);
    }


    @PutMapping("/{voucId}/approve/details")
    @Tag(name = "Voucher API")
    @Operation(summary = "출고전표승인", description = "출고전표를 승인합니다.")
    public ResponseEntity<Void> approveVoucherDetails(@PathVariable Long voucId, @RequestBody Map<String, String> requestBody) {
        String remarks = requestBody.get("remarks");
        voucherService.approveVoucherDetails(voucId, remarks);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{voucId}/reject/details")
    @Tag(name = "Voucher API")
    @Operation(summary = "출고전표반려", description = "출고전표를 반려합니다.")
    public ResponseEntity<Void> rejectVoucherDetails(@PathVariable Long voucId, @RequestBody Map<String, String> requestBody) {
        String remarks = requestBody.get("remarks");
        voucherService.rejectVoucherDetails(voucId, remarks);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/voucId") //전표 번호 생성
    @Tag(name = "Voucher API")
    @Operation(summary = "출고전표번호 생성", description = "전표번호를 생성합니다.")
    public ResponseEntity<?> generateVoucherId() {
        Map<String, Long> voucId = new HashMap<>();
        voucId.put("voucId", sequenceService.generateVoucherId());
        return ResponseEntity.ok(voucId);
    }

    @PostMapping("/saveall") // 여러 전표 저장
    @Tag(name = "Voucher API")
    @Operation(summary = "출고전표저장", description = "여러 전표를 저장합니다.")
    public ResponseEntity<?> createVouchers(@RequestBody VoucherSaveDTO VoucherSaveDTO) {
        System.out.println("VoucherSaveDTO: " + VoucherSaveDTO);
        try {
            voucherService.createVouchers(VoucherSaveDTO);
            return new ResponseEntity<>("Vouchers created successfully.", HttpStatus.CREATED);
        } catch (InsufficientStockException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 검색 조건에 맞는 출고전표를 조회
    @GetMapping("/search")
    @Tag(name = "Voucher API")
    @Operation(summary = "출고전표 검색", description = "조건에 따라 출고전표를 검색합니다.")
    public ResponseEntity<List<VoucherDTO>> searchVouchers(
            @RequestParam(required = false) String voucId, // 전표번호로 검색 (선택)
            @RequestParam(required = false) String empName, // 담당자 이름으로 검색 (선택)
            @RequestParam(required = false) String clientName, // 거래처명으로 검색 (선택)
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, // 등록일 시작일로 검색 (선택)
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate, // 등록일 종료일로 검색 (선택)
            @RequestParam(required = false) String signerName, // 결재자 이름으로 검색 (선택)
            @RequestParam(required = false) String approvalStatus, // 결재상태로 검색 (선택)
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate approvalStartDate, // 결재일 시작일로 검색 (선택)
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate approvalEndDate) { // 결재일 종료일로 검색 (선택)
        // VoucherService의 searchVouchers 메서드 호출하여 검색 조건에 맞는 출고전표 목록 조회
        List<VoucherDTO> vouchers = voucherService.searchVouchers(voucId, empName, clientName, startDate, endDate, signerName, approvalStatus, approvalStartDate, approvalEndDate);
        // 조회된 출고전표 목록을 응답으로 반환
        return ResponseEntity.ok(vouchers);
    }
}
