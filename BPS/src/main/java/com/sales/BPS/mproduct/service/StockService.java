package com.sales.BPS.mproduct.service;

import com.sales.BPS.mproduct.dto.StockListDTO;
import com.sales.BPS.mproduct.dto.StockRegisterDTO;
import com.sales.BPS.mproduct.entity.Stock;
import com.sales.BPS.mproduct.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void registerStock(StockRegisterDTO stockRegisterDTO) {
        Stock stock = new Stock();
        stock.setProCode(stockRegisterDTO.getProCode());
        stock.setStoAmo(stockRegisterDTO.getStoAmo());

        // 기존 재고가 있는지 확인
        Optional<Stock> existingStock = stockRepository.findById(stockRegisterDTO.getProCode());

        if (existingStock.isPresent()) {
            // 기존 재고가 있으면 재고 수량 업데이트
            Stock updateStock = existingStock.get();
            updateStock.setStoAmo(updateStock.getStoAmo() + stockRegisterDTO.getStoAmo());
            stockRepository.save(updateStock);
        } else {
            // 기존 재고가 없으면 새로 등록
            stockRepository.save(stock);
        }
    }

}
