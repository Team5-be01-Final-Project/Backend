package com.sales.BPS.mproduct.entity.repository;

import com.sales.BPS.mproduct.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository <Product, Integer> {

    Page<Product> findAll(Pageable pageable);

    // Search By Production Code
    Optional<Product> findByProCode (Integer proCode);

    List<Product> findByProName(String proName);

    List<Product> findByProSeg(String proSeg);

    List<Product> findByProIngre(String

}
