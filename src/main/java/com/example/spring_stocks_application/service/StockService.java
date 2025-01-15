package com.example.spring_stocks_application.service;


import com.example.spring_stocks_application.entity.Stock;
import com.example.spring_stocks_application.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock getStockById(Long id) {
        Optional<Stock> stock = stockRepository.findById(id);
        return stock.orElseThrow(() -> new RuntimeException("Stock not found with ID: " + id));
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Stock updateStock(Long id, Stock stockDetails) {
        Stock stock = getStockById(id);

        stock.setName(stockDetails.getName());
        stock.setOpenPrice(stockDetails.getOpenPrice());
        stock.setClosePrice(stockDetails.getClosePrice());
        stock.setHighPrice(stockDetails.getHighPrice());
        stock.setLowPrice(stockDetails.getLowPrice());
        stock.setSettlementPrice(stockDetails.getSettlementPrice());

        return stockRepository.save(stock);
    }

    public void deleteStock(Long id) {
        Stock stock = getStockById(id);
        stockRepository.delete(stock);
    }
}

