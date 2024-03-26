package com.sales.BPS.mproduct.repository;

import com.sales.BPS.mproduct.dto.StockListDTO;
import com.sales.BPS.mproduct.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Integer> {

    @Query("SELECT new com.sales.BPS.mproduct.dto.StockListDTO(p.proCode, p.proName, s.stoAmo, p.proUnit) " +
            "FROM Product p " +
            "JOIN Stock s ON p.proCode = s.proCode")
    List<StockListDTO> AllStockList();

    @Query("SELECT new com.sales.BPS.mproduct.dto.StockListDTO(p.proCode, p.proName, s.stoAmo, p.proUnit) "+
            "FROM Product p " +
            "JOIN Stock s ON p.proCode = s.proCode " +
            "WHERE (:proCode IS NULL OR CAST(p.proCode AS string) LIKE %:proCode%)" +
            "AND (:proName IS NULL OR p.proName LIKE %:proName%)")
    List<StockListDTO> findStockList(@Param("proCode") Integer proCode,
                                     @Param("proName") String proName);

    @Modifying
    @Query("UPDATE Stock s SET s.stoAmo = s.stoAmo + :stoAmo WHERE s.proCode = :proCode")
    void updateStockAmount(@Param("proCode") Integer proCode, @Param("stoAmo") Integer stoAmo);


}
