package com.example.spring_stocks_application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Holding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long holdingId;

//    @ManyToOne
//    @JoinColumn(name ="user_id", nullable = false)
    private Long userId;

    private Long stockId;
    private int quantityHold;
    private int buyPrice;
    private int currentPrice;

    public Holding() {
    }

    public Holding(Long holdingId, Long userId, Long stockId, int quantityHold,int buyPrice,int currentPrice) {
        this.holdingId = holdingId;
        this.userId = userId;
        this.stockId = stockId;
        this.quantityHold = quantityHold;
        this.buyPrice=buyPrice;
        this.currentPrice=currentPrice;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
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
}

// INSERT INTO HOLDING (HOLDING_ID, BUY_PRICE, CURRENT_PRICE,QUANTITY_HOLD, STOCK_ID, USER_ID)VALUES (2,12,13,10,2,1);