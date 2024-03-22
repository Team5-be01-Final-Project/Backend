package com.sales.BPS.msales.controller;

// PpcController.java

import com.sales.BPS.msales.entity.Ppc;
import com.sales.BPS.msales.service.PpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients/{clientCode}/products")
public class PpcController {

    private final PpcService ppcService;
    @Autowired
    public PpcController(PpcService ppcService) {
        this.ppcService = ppcService;
    }

    @GetMapping
    public ResponseEntity<List<Ppc>> getPpcs(@PathVariable String clientCode) {
        List<Ppc> ppcs = ppcService.getPpcs(clientCode);
        return ResponseEntity.ok(ppcs);
    }

    @PostMapping("/{proCode}")
    public ResponseEntity<Ppc> addPpc(@PathVariable String clientCode, @PathVariable Integer proCode, @RequestParam Integer ppcSale) {
        Ppc addedPpc = ppcService.addPpc(clientCode, proCode, ppcSale);
        return ResponseEntity.ok(addedPpc);
    }

    @PutMapping("/{proCode}")
    public ResponseEntity<Ppc> updatePpc(@PathVariable String clientCode, @PathVariable Integer proCode, @RequestParam Integer ppcSale) {
        Ppc updatedPpc = ppcService.updatePpc(clientCode, proCode, ppcSale);
        return ResponseEntity.ok(updatedPpc);
    }

    @DeleteMapping("/{proCode}")
    public ResponseEntity<?> deletePpc(@PathVariable String clientCode, @PathVariable Integer proCode) {
        ppcService.deletePpc(clientCode, proCode);
        return ResponseEntity.noContent().build();
    }

    // 이 메소드는 모든 거래처의 정보를 반환합니다.
    @GetMapping("/all")
    public ResponseEntity<List<Ppc>> getAllPpcs() {
        List<Ppc> allPpcs = ppcService.getAllPpcs();
        return ResponseEntity.ok(allPpcs);
    }
}
