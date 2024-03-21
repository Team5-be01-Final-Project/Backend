package com.sales.BPS.mproduct.entity.repository;

import com.sales.BPS.mproduct.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product, Integer> {

}
