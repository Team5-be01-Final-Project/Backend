package com.sales.BPS.mproduct.controller;

import com.sales.BPS.config.CookieUtil;
import com.sales.BPS.mproduct.entity.Product;
import com.sales.BPS.mproduct.service.ProductService;
import com.sales.BPS.msales.dto.PpcDTO;
import com.sales.BPS.msales.dto.PpcVoucherDTO;
import com.sales.BPS.msales.entity.Ppc;
import com.sales.BPS.msales.service.PpcService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products API", description = "상품관리 API입니다.")
public class ProductController {

    private final ProductService productService;
    private final PpcService ppcService;

    @Autowired
    public ProductController(ProductService productService, PpcService ppcService) {
        this.productService = productService;
        this.ppcService = ppcService;
    }

    @GetMapping
    @Tag(name = "Products API")
    @Operation(summary = "상품조회", description = "상품 리스트를 조회합니다.")
    public ResponseEntity<List<Product>> getAllProducts(HttpServletRequest request) {
        List<Product> products = productService.findAll();

        // 쿠키에서 권한 코드 가져오기
        String authCode = CookieUtil.getCookieValue(request, "empAuthCode");

        // 팀장 권한 이상이 아닌 경우 단가를 null로 설정
        if (!Arrays.asList("AUTH001", "AUTH002", "AUTH003").contains(authCode)) {
            products.forEach(product -> product.setProUnit(null));
        }

        return ResponseEntity.ok(products);
    }


    @GetMapping("/{clientCode}/ppcs")
    public ResponseEntity<List<PpcVoucherDTO>> getProductsByClient(@PathVariable String clientCode) {
        List<PpcVoucherDTO> ppcVoucherDTO = ppcService.findPpcByClient(clientCode);
        System.out.println(clientCode);
        System.out.println(ppcVoucherDTO); // 콘솔에 출력
        return ResponseEntity.ok(ppcVoucherDTO);
    }
}
