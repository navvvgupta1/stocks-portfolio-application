package com.example.spring_stocks_application.service;

import com.example.spring_stocks_application.dto.SellRequest;
import com.example.spring_stocks_application.entity.Holding;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final PortfolioService portfolioService;

    public KafkaConsumerService(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @KafkaListener(topics = "buy-topic", groupId = "portfolio-group")
    public void consumeBuyMessage(Holding holding) {
        portfolioService.createHolding(holding);
    }

    @KafkaListener(topics = "sell-topic", groupId = "portfolio-group")
    public void consumeSellMessage(SellRequest sellRequest) {
        portfolioService.sellStocks(sellRequest.getUserId(), sellRequest.getStockId(), sellRequest.getQuantity());
    }
}
