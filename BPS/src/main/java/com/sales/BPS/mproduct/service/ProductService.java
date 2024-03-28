package com.sales.BPS.mproduct.service;

import com.sales.BPS.mproduct.entity.Product;
import com.sales.BPS.mproduct.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 모든 제품 조회
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // 제품명으로 검색
    public List<Product> findByProName(String proName) {
        return productRepository.findByProName(proName);
    }

    // 품목 구분으로 검색
    public List<Product> findByProSeg(String proSeg) {
        return productRepository.findByProSeg(proSeg);
    }

    // 주성분으로 검색
    public List<Product> findByProIngre(String proIngre) {
        return productRepository.findByProIngre(proIngre);
    }

    // 생산 ATC 코드로 검색
    public List<Product> findByProAtc(String proAtc) {
        return productRepository.findByProAtc(proAtc);
    }

    public Product findProductByProCode(Integer proCode) {
        return productRepository.findByProCode(proCode);
    }

}
