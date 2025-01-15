package com.example.spring_stocks_application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HoldingResponseDTO {
    private Long holdingId;
    private Long userId;
    private Long stockId;
    private int quantityHold;
    private int buyPrice;
    private int currentPrice;
    private int gainLoss;

    public HoldingResponseDTO(Long holdingId, Long userId, Long stockId, int quantityHold, int buyPrice, int currentPrice, int gainLoss) {
        this.holdingId = holdingId;
        this.userId = userId;
        this.stockId = stockId;
        this.quantityHold = quantityHold;
        this.buyPrice = buyPrice;
        this.currentPrice = currentPrice;
        this.gainLoss = gainLoss;
    }
}

