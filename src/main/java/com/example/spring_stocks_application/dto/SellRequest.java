package com.example.spring_stocks_application.dto;

public class SellRequest {
    private Long userId;
    private Long stockId;
    private int quantity;

    public SellRequest(Long userId, Long stockId, int quantity) {
        this.userId = userId;
        this.stockId = stockId;
        this.quantity = quantity;
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
