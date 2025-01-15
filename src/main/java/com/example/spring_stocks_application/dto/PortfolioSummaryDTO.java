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
}
