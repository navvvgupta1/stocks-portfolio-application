package com.example.spring_stocks_application.controller;

import com.example.spring_stocks_application.entity.Stock;
import com.example.spring_stocks_application.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // Get stock details by ID
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Stock stock = stockService.getStockById(id);
        return ResponseEntity.ok(stock);
    }

    // Get all stocks
    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<Stock>> searchStocksByName(@PathVariable String name) {
        List<Stock> stocks = stockService.searchStocksByName(name);
        return ResponseEntity.ok(stocks);
    }

    // Create a new stock
    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        Stock newStock = stockService.createStock(stock);
        return ResponseEntity.ok(newStock);
    }

    // Update an existing stock
    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id, @RequestBody Stock stockDetails) {
        Stock updatedStock = stockService.updateStock(id, stockDetails);
        return ResponseEntity.ok(updatedStock);
    }

    // Delete a stock
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadStockData(@RequestParam("file") MultipartFile file) {
        try {
            System.out.println("Hi");
            stockService.saveStocksFromCsv(file);
            System.out.println("Hi");
            return ResponseEntity.ok("Stock data uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to upload stock data: " + e.getMessage());
        }
    }
}

