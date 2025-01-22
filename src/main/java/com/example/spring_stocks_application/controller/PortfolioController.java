package com.example.spring_stocks_application.controller;

import com.example.spring_stocks_application.dto.HoldingResponseDTO;
import com.example.spring_stocks_application.dto.PortfolioSummaryDTO;
import com.example.spring_stocks_application.dto.SellRequest;
import com.example.spring_stocks_application.entity.Holding;
import com.example.spring_stocks_application.entity.Stock;
import com.example.spring_stocks_application.service.KafkaProducerService;
import com.example.spring_stocks_application.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import com.example.spring_stocks_application.dto.SimpleHoldingResponse;
@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final KafkaProducerService kafkaProducerService;

    public PortfolioController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buyStock(@RequestBody Holding holding) {
        kafkaProducerService.sendMessage("buy-topic", holding);
        return ResponseEntity.ok("Buy request submitted successfully.");
    }

    @PutMapping("/sell/{userId}/{stockId}/{quantity}")
    public ResponseEntity<String> sellStock(@PathVariable Long userId, @PathVariable Long stockId, @PathVariable int quantity) {
        SellRequest sellRequest = new SellRequest(userId, stockId, quantity);
        kafkaProducerService.sendMessage("sell-topic", sellRequest);
        return ResponseEntity.ok("Sell request submitted successfully.");
    }
}
