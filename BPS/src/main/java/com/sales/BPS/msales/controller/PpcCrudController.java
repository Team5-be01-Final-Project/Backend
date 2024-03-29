package com.sales.BPS.msales.controller;// PpcCrudController.java

import com.sales.BPS.msales.dto.PpcDTO;
import com.sales.BPS.msales.service.PpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ppc")
public class PpcCrudController {

    private final PpcService ppcService;

    @Autowired
    public PpcCrudController(PpcService ppcService) {
        this.ppcService = ppcService;
    }

    // 거래처별 상품 가격 정보 조회
    @GetMapping("/client/{clientCode}")
    public ResponseEntity<List<PpcDTO>> getPpcsByClientCode(@PathVariable String clientCode) {
        List<PpcDTO> ppcDTOs = ppcService.getPpcs(clientCode);
        return ResponseEntity.ok(ppcDTOs);
    }

    // 거래처별 상품 가격 정보 추가 또는 업데이트
    @PutMapping("/update/{proCode}")
    public ResponseEntity<Void> updatePpc(@PathVariable Integer proCode, @RequestBody PpcDTO ppcDTO) {
        ppcService.updatePpc(proCode, ppcDTO);
        return ResponseEntity.ok().build();
    }

    // 거래처별 상품 가격 정보 삭제
    @DeleteMapping("/{proCode}")
    public ResponseEntity<?> deletePpc(@PathVariable Integer proCode) {
        ppcService.deletePpcByProCode(proCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<PpcDTO>> getAllPpcs() {
        List<PpcDTO> allPpcDTOs = ppcService.getAllPpcs();
        return ResponseEntity.ok(allPpcDTOs);
    }

    @PostMapping("/register")
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



