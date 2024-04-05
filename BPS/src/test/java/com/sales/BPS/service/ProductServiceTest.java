package com.sales.BPS.service;

import com.sales.BPS.mproduct.entity.Product;
import com.sales.BPS.mproduct.repository.ProductRepository;
import com.sales.BPS.mproduct.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private List<Product> productList;

    @BeforeEach
    void setUp() {
        // 테스트에 사용할 가짜 Product 데이터 생성
        productList = new ArrayList<>();

        Product product1 = new Product(dto.getProCode());
        product1.setProCode(1);
        product1.setProName("Product1");
        product1.setProSeg("Segment1");
        product1.setProIngre("Ingredient1");
        product1.setProAtc("ATC1");
        product1.setProCat("Category1");
        product1.setProUnit(1);
        productList.add(product1);

        Product product2 = new Product(dto.getProCode());
        product2.setProCode(2);
        product2.setProName("Product2");
        product2.setProSeg("Segment2");
        product2.setProIngre("Ingredient2");
        product2.setProAtc("ATC2");
        product2.setProCat("Category2");
        product2.setProUnit(2);
        productList.add(product2);

        lenient().when(productRepository.findAll()).thenReturn(productList);
        lenient().when(productRepository.findByProName("Product1")).thenReturn(List.of(product1));
        lenient().when(productRepository.findByProSeg("Segment1")).thenReturn(List.of(product1));
        lenient().when(productRepository.findByProIngre("Ingredient1")).thenReturn(List.of(product1));
        lenient().when(productRepository.findByProAtc("ATC1")).thenReturn(List.of(product1));
        lenient().when(productRepository.findByProCode(1)).thenReturn(product1);
    }

    @Test
    void testFindAll() {
        // 테스트: ProductService의 findAll() 메서드 호출 시 productList의 크기가 반환되는지 확인
        List<Product> result = productService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testFindByProName() {
        // 테스트: ProductService의 findByProName() 메서드 호출 시 productList의 내용이 반환되는지 확인
        List<Product> result = productService.findByProName("Product1");
        assertEquals(1, result.size());
        assertEquals("Product1", result.get(0).getProName());
    }

    @Test
    void testFindByProSeg() {
        // 테스트: ProductService의 findByProSeg() 메서드 호출 시 productList의 내용이 반환되는지 확인
        List<Product> result = productService.findByProSeg("Segment1");
        assertEquals(1, result.size());
        assertEquals("Segment1", result.get(0).getProSeg());
    }

    @Test
    void testFindByProIngre() {
        // 테스트: ProductService의 findByProIngre() 메서드 호출 시 productList의 내용이 반환되는지 확인
        List<Product> result = productService.findByProIngre("Ingredient1");
        assertEquals(1, result.size());
        assertEquals("Ingredient1", result.get(0).getProIngre());
    }

    @Test
    void testFindByProAtc() {
        // 테스트: ProductService의 findByProAtc() 메서드 호출 시 productList의 내용이 반환되는지 확인
        List<Product> result = productService.findByProAtc("ATC1");
        assertEquals(1, result.size());
        assertEquals("ATC1", result.get(0).getProAtc());
    }

    @Test
    void testFindProductByProCode() {
        // 테스트: ProductService의 findProductByProCode() 메서드 호출 시 productList에서 proCode가 일치하는 Product가 반환되는지 확인
        Product result = productService.findProductByProCode(1);
        assertEquals("Product1", result.getProName());
    }
}