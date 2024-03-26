package com.sales.BPS.mproduct.service;

import com.sales.BPS.mproduct.dto.StockListDTO;
import com.sales.BPS.mproduct.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<StockListDTO> findAllStockList() {
        return stockRepository.AllStockList();
    }

    public List<StockListDTO> findStockListByCondition(Integer proCode, String proName) {
        return stockRepository.findStockList(proCode, proName);
    }

}
