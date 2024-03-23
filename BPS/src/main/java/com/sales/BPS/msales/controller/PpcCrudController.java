package com.sales.BPS.msales.controller;

import com.sales.BPS.mproduct.entity.Product;
import com.sales.BPS.mproduct.service.ProductService;
import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.entity.Ppc;
import com.sales.BPS.msales.service.ClientService;
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
    private final ProductService productService;
    private final ClientService clientService;
    @Autowired
    public PpcCrudController(PpcService ppcService, ProductService productService, ClientService clientService) {
        this.ppcService = ppcService;
        this.productService = productService;
        this.clientService = clientService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Ppc>> getAllPpcs() {
        List<Ppc> allPpcs = ppcService.getAllPpcs();
        return ResponseEntity.ok(allPpcs);
    }

    @PostMapping("/{proCode}")
    public ResponseEntity<?> updateSale(@PathVariable Integer proCode, @RequestBody Map<String, Object> request) {
        try {
            String clientCode = (String) request.get("clientCode");
            Integer ppcSale = (Integer) request.get("ppcSale");

            if (clientCode == null || clientCode.isEmpty()) {
                throw new IllegalArgumentException("Client code must not be null or empty");
            }

            // Update or add PPC entry
            Ppc updatedPpc = ppcService.addOrUpdatePpc(clientCode, proCode, ppcSale);

            return ResponseEntity.ok(updatedPpc);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
    @DeleteMapping("/{proCode}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer proCode) {
        try {
            ppcService.deletePpcByProCode(proCode);
            return ResponseEntity.ok("Product deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete product: " + e.getMessage());
        }
    }

    @GetMapping("/data")
    public ResponseEntity<Map<String, Object>> getData() {
        List<Ppc> ppcs = ppcService.getAllPpcs();
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
