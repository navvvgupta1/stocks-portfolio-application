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

    public int getGainLoss() {
        return gainLoss;
    }

    public void setGainLoss(int gainLoss) {
        this.gainLoss = gainLoss;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHoldingId() {
        return holdingId;
    }

    public void setHoldingId(Long holdingId) {
        this.holdingId = holdingId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public int getQuantityHold() {
        return quantityHold;
    }

    public void setQuantityHold(int quantityHold) {
        this.quantityHold = quantityHold;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }
}

