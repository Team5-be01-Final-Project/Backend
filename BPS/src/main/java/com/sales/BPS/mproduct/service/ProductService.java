package com.sales.BPS.mproduct.service;

import com.sales.BPS.mproduct.entity.Product;
import com.sales.BPS.mproduct.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
// Show all Products
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

// search by production name
    public List<Product> findByProName(String proName){
        return productRepository.findByProName(proName);
    }
//search by segmentation
    public List<Product> findByProSeg(String proSeg){
        return productRepository.findByProSeg(proSeg);
    }
// Search By Ingredient
    public List<Product> findByProIngre(String proIngre){
        return productRepository.findByProIngre(proIngre);
    }
// Search By Production ATCCode
    public List<Product> findByProAtc(String proAtc){
        return productRepository.findByProAtc(proAtc);
    }


}
