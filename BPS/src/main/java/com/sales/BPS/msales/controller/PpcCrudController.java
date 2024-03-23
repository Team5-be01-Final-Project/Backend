package com.sales.BPS.msales.controller;

import com.sales.BPS.msales.entity.Ppc;
import com.sales.BPS.msales.service.PpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/ppc")
public class PpcCrudController {

    private final PpcService ppcService;

    @Autowired
    public PpcCrudController(PpcService ppcService) {
        this.ppcService = ppcService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Ppc>> getAllPpcs() {
        List<Ppc> allPpcs = ppcService.getAllPpcs();
        return ResponseEntity.ok(allPpcs);
    }

    @PostMapping("/{proCode}")
    public ResponseEntity<Ppc> updateSale(@PathVariable Integer proCode, @RequestBody Map<String, Object> request) {
        String clientCode = (String) request.get("clientCode");
        Integer ppcSale = (Integer) request.get("ppcSale");

        // 이미 해당 클라이언트에 대한 제품이 등록되어 있는지 확인
        Ppc existingPpc = ppcService.findPpcByClientCodeAndProCode(clientCode, proCode);
        if (existingPpc != null) {
            // 이미 등록된 경우 판매가를 업데이트하고 반환
            existingPpc.setPpcSale(ppcSale);
            Ppc updatedPpc = ppcService.updatePpc(existingPpc);
            return ResponseEntity.ok(updatedPpc);
        }

        // 등록되지 않은 경우 새로운 판매 등록
        Ppc addedPpc = ppcService.addPpc(clientCode, proCode, ppcSale);
        return ResponseEntity.ok(addedPpc);
    }


    @DeleteMapping("/{proCode}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer proCode) {
        try {
            // 상품 삭제
            ppcService.deletePpcByProCode(proCode);
            return ResponseEntity.ok("상품이 삭제되었습니다.");
        } catch (Exception e) {
            // 오류 로깅을 추가하여 문제를 더 쉽게 추적할 수 있습니다.
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 삭제에 실패했습니다.");
        }
    }


    @GetMapping("/data")
    public ResponseEntity<Map<String, Object>> getData() {
        List<Ppc> ppcs = ppcService.getAllPpcs();

        // clients 배열 생성
        Set<String> clientSet = new HashSet<>();
        List<Map<String, String>> clients = new ArrayList<>();
        for (Ppc ppc : ppcs) {
            String clientKey = ppc.getClientName() + "-" + ppc.getEmpName();
            if (clientSet.add(clientKey)) {
                Map<String, String> client = new HashMap<>();
                client.put("clientCode", ppc.getClientCode());
                client.put("clientName", ppc.getClientName());
                client.put("empName", ppc.getEmpName());
                clients.add(client);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("clients", clients);
        response.put("products", ppcs);

        return ResponseEntity.ok(response);
    }
}
