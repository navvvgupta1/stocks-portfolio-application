package com.example.spring_stocks_application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PortfolioSummaryDTO {
    private List<HoldingResponseDTO> holdings;
    private int totalBuyPrice;
    private int totalCurrentPrice;
    private int totalPL; // Total Profit/Loss
    private double totalPLPercentage; // P/L %

    public PortfolioSummaryDTO(double totalPLPercentage, int totalCurrentPrice, List<HoldingResponseDTO> holdings, int totalBuyPrice, int totalPL) {
        this.totalPLPercentage = totalPLPercentage;
        this.totalCurrentPrice = totalCurrentPrice;
        this.holdings = holdings;
        this.totalBuyPrice = totalBuyPrice;
        this.totalPL = totalPL;
    }

    public List<HoldingResponseDTO> getHoldings() {
        return holdings;
    }

    public void setHoldings(List<HoldingResponseDTO> holdings) {
        this.holdings = holdings;
    }

    public int getTotalBuyPrice() {
        return totalBuyPrice;
    }

    public void setTotalBuyPrice(int totalBuyPrice) {
        this.totalBuyPrice = totalBuyPrice;
    }

    public int getTotalCurrentPrice() {
        return totalCurrentPrice;
    }

    public void setTotalCurrentPrice(int totalCurrentPrice) {
        this.totalCurrentPrice = totalCurrentPrice;
    }

    public int getTotalPL() {
        return totalPL;
    }

    public void setTotalPL(int totalPL) {
        this.totalPL = totalPL;
    }

    public double getTotalPLPercentage() {
        return totalPLPercentage;
    }

    public void setTotalPLPercentage(double totalPLPercentage) {
        this.totalPLPercentage = totalPLPercentage;
    }
}
