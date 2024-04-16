package com.sales.BPS.msales.controller;// PpcCrudController.java

import com.sales.BPS.msales.dto.PpcDTO;
import com.sales.BPS.msales.service.PpcService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ppc")
@Tag(name = "Ppc API", description = "판매가관리 API입니다.")
public class PpcCrudController {

    private final PpcService ppcService;

    @Autowired
    public PpcCrudController(PpcService ppcService) {
        this.ppcService = ppcService;
    }

    // 거래처별 상품 가격 정보 조회
    @GetMapping("/client/{clientCode}")
    @Tag(name = "Ppc API")
    @Operation(summary = "상품 가격 조회",description = "거래처별 상품 가격 정보를 조회합니다.")
    public ResponseEntity<List<PpcDTO>> getPpcsByClientCode(@PathVariable String clientCode) {
        List<PpcDTO> ppcDTOs = ppcService.getPpcs(clientCode);
        return ResponseEntity.ok(ppcDTOs);
    }

    // 거래처별 상품 가격 정보 추가 또는 업데이트
    @PutMapping("/{clientCode}/{proCode}")
    @Tag(name = "Ppc API")
    @Operation(summary = "상품 가격 추가/수정",description = "거래처별 상품 가격 정보를 추가 또는 업데이트 합니다.")
    public ResponseEntity<Void> updatePpc(@PathVariable String clientCode, @PathVariable Integer proCode, @RequestBody PpcDTO ppcDTO) {
        ppcService.updatePpc(clientCode, proCode, ppcDTO);
        return ResponseEntity.ok().build();
    }


    // 거래처별 상품 가격 정보 삭제
    @DeleteMapping("/{clientCode}/{proCode}")
    @Tag(name = "Ppc API")
    @Operation(summary = "상품 가격 삭제",description = "거래처별 상품 가격 정보를 삭제합니다.")
    public ResponseEntity<?> deletePpc(@PathVariable String clientCode, @PathVariable Integer proCode) {
        if (clientCode == null || proCode == null) {
            return ResponseEntity.badRequest().build();
        }
        ppcService.deletePpc(clientCode, proCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    @Tag(name = "Ppc API")
    @Operation(summary = "상품 가격 조회",description = "상품 가격 정보를 조회합니다.")
    public ResponseEntity<List<PpcDTO>> getAllPpcs() {
        List<PpcDTO> allPpcDTOs = ppcService.getAllPpcs();
        return ResponseEntity.ok(allPpcDTOs);
    }

    @PostMapping("/register")
    @Tag(name = "Ppc API")
    @Operation(summary = "상품 가격 등록",description = "거래처별 상품 가격을 등록합니다.")
    public ResponseEntity<Void> registerPpc(@RequestBody PpcDTO ppcDTO) {
        boolean isExisting = ppcService.isExistingSale(ppcDTO.getClientCode(), ppcDTO.getProCode());

        if (isExisting) {
            return ResponseEntity.badRequest().build(); // Return bad request status if the combination already exists
        }

        // If the combination does not exist, proceed with registration
        ppcService.registerPpc(ppcDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/existing")
    public ResponseEntity<Boolean> checkExistingSale(@RequestBody PpcDTO ppcDTO) {
        boolean isExisting = ppcService.isExistingSale(ppcDTO.getClientCode(), ppcDTO.getProCode());
        return ResponseEntity.ok(isExisting);
    }
    }



