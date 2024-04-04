package com.sales.BPS.mproduct.repository;

import com.sales.BPS.mproduct.dto.StockListDTO;
import com.sales.BPS.mproduct.entity.Product;
import com.sales.BPS.mproduct.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Integer> {

    // 모든 재고 리스트를 조회하는 쿼리
    // Product 엔티티와 Stock 엔티티를 조인하여 StockListDTO 객체를 반환합니다.
    @Query("SELECT new com.sales.BPS.mproduct.dto.StockListDTO(p.proCode, p.proName, s.stoAmo, p.proUnit) " +
            "FROM Product p " +
            "JOIN Stock s ON p.proCode = s.proCode")
    List<StockListDTO> AllStockList();

    // proCode와 proName을 기준으로 재고 리스트를 조회하는 쿼리
    // proCode 또는 proName이 null이면 해당 조건은 무시
    @Query("SELECT new com.sales.BPS.mproduct.dto.StockListDTO(p.proCode, p.proName, s.stoAmo, p.proUnit) "+
            "FROM Product p " +
            "JOIN Stock s ON p.proCode = s.proCode " +
            "WHERE (:proCode IS NULL OR CAST(p.proCode AS string) LIKE %:proCode%)" +
            "AND (:proName IS NULL OR p.proName LIKE %:proName%)")
    List<StockListDTO> findStockList(@Param("proCode") Integer proCode,
                                     @Param("proName") String proName);

    // proCode를 기준으로 Product를 조회하는 쿼리
    @Query("SELECT p FROM Product p WHERE p.proCode = :proCode")
    Optional<Product> findProductByProCode(@Param("proCode") Integer proCode);


    // 상품 코드로 재고 조회
    Stock findByProCode(Integer proCode);
}
