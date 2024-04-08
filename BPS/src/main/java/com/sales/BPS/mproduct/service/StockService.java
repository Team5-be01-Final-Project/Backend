package com.sales.BPS.mproduct.service;

import com.sales.BPS.mexception.InsufficientStockException;
import com.sales.BPS.mproduct.dto.StockListDTO;
import com.sales.BPS.mproduct.dto.StockProductDTO;
import com.sales.BPS.mproduct.dto.StockRegisterDTO;
import com.sales.BPS.mproduct.entity.Product;
import com.sales.BPS.mproduct.entity.Stock;
import com.sales.BPS.mproduct.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    // StockRepository 의존성 주입을 위한 생성자
    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    // 모든 재고 목록 조회
    public List<StockListDTO> findAllStockList() {
        return stockRepository.AllStockList();
    }

    // 조건에 따라 재고 목록 조회
    public List<StockListDTO> findStockListByCondition(Integer proCode, String proName) {
        return stockRepository.findStockList(proCode, proName);
    }

    @Transactional(readOnly = true)
    public Integer getStockAmountByProductCode(Integer proCode) {
        Stock stock = stockRepository.findByProCode(proCode);
        if (stock != null) {
            return stock.getStoAmo();
        } else {
            // 재고가 없는 경우 0을 반환하도록 설정
            return 0;
        }
    }

    // 재고 등록
    public void registerStock(StockRegisterDTO stockRegisterDTO) {
        // 상품 정보 확인
        Optional<Product> product = stockRepository.findProductByProCode(stockRegisterDTO.getProCode());

        if (product.isPresent()) {
            // 기존 재고가 있는지 확인
            Optional<Stock> existingStock = stockRepository.findById(stockRegisterDTO.getProCode());

            if (existingStock.isPresent()) {
                // 기존 재고가 있으면 재고 수량 업데이트
                Stock stock = existingStock.get();
                stock.setStoAmo(stock.getStoAmo() + stockRegisterDTO.getStoAmo());
                stockRepository.save(stock);
            } else {
                // 기존 재고가 없으면 새로 등록
                Stock stock = new Stock();
                stock.setProCode(stockRegisterDTO.getProCode());
                stock.setStoAmo(stockRegisterDTO.getStoAmo());
                stock.setProduct(product.get());
                stockRepository.save(stock);
            }
        } else {
            throw new IllegalArgumentException("상품 정보가 없어 재고를 등록할 수 없습니다.");
        }
    }

    // 상품 코드로 상품 정보 조회
    public StockProductDTO getProductByCode(Integer proCode) {
        Optional<Product> product = stockRepository.findProductByProCode(proCode);
        if (product.isPresent()) {
            StockProductDTO stockProductDTO = new StockProductDTO();
            stockProductDTO.setProCode(product.get().getProCode());
            stockProductDTO.setProName(product.get().getProName());
            return stockProductDTO;
        } else {
            return null;
        }
    }

    @Transactional //재고 차감 로직
    public boolean decreaseStock(Integer proCode, Integer stoAmo) {
        Stock stock = stockRepository.findById(proCode)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (stock.getStoAmo() >= stoAmo) {
            stock.setStoAmo(stock.getStoAmo() - stoAmo);
            stockRepository.save(stock);
            return true;
        } else {
            throw new InsufficientStockException(proCode + " 의 재고가 부족합니다. 다시 확인해주세요.");
        }
    }
}
