package com.example.spring_stocks_application.controller;

import com.example.spring_stocks_application.dto.HoldingResponseDTO;
import com.example.spring_stocks_application.dto.PortfolioSummaryDTO;
import com.example.spring_stocks_application.entity.Holding;
import com.example.spring_stocks_application.entity.Stock;
import com.example.spring_stocks_application.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import com.example.spring_stocks_application.dto.SimpleHoldingResponse;
@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService){
        this.portfolioService=portfolioService;
    }

    @GetMapping("/{userId}")  // The path variable should be userId
    public PortfolioSummaryDTO getUserHoldings(@PathVariable Long userId) {
        System.out.println(userId);
        return portfolioService.getPortfolioSummary(userId);
    }

//    @GetMapping("/buy/")
//
    @PostMapping("/buy")
    public ResponseEntity<Holding> createStock(@RequestBody Holding holding) {
        Holding newHolding = portfolioService.createHolding(holding);
        return ResponseEntity.ok(newHolding);
    }

    @PutMapping("/sell/{userId}/{stockId}/{noOfStocks}")
    public ResponseEntity<String> sellStocks(@PathVariable Long userId, @PathVariable Long stockId,@PathVariable int noOfStocks) {
        portfolioService.sellStocks(userId, stockId,noOfStocks);
        return ResponseEntity.ok(noOfStocks+" stocks sold successfully for userId: " + userId + " and stockId: " + stockId);
    }
}

