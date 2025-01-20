package com.example.spring_stocks_application.service;

import com.example.spring_stocks_application.dto.HoldingResponseDTO;
import com.example.spring_stocks_application.dto.PortfolioSummaryDTO;
import com.example.spring_stocks_application.entity.Holding;
import com.example.spring_stocks_application.entity.Stock;
import com.example.spring_stocks_application.repository.HoldingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class PortfolioService {

    private final HoldingRepository holdingRepository;

    public PortfolioService(HoldingRepository holdingRepository){
        this.holdingRepository=holdingRepository;
    }

    public PortfolioSummaryDTO getPortfolioSummary(Long userId) {
        // Fetch the holdings from the repository
        List<Holding> holdings = holdingRepository.findByUserId(userId);

        // Map each Holding to HoldingResponseDTO
        List<HoldingResponseDTO> holdingResponseDTOs = holdings.stream().map(holding -> {
            int gainLoss = (holding.getCurrentPrice() - holding.getBuyPrice()) * holding.getQuantityHold();
            return new HoldingResponseDTO(
                    holding.getHoldingId(),
                    holding.getUserId(),
                    holding.getStockId(),
                    holding.getQuantityHold(),
                    holding.getBuyPrice(),
                    holding.getCurrentPrice(),
                    gainLoss
            );
        }).collect(Collectors.toList());

        // Calculate totals
        int totalBuyPrice = holdings.stream()
                .mapToInt(holding -> holding.getBuyPrice() * holding.getQuantityHold())
                .sum();

        int totalCurrentPrice = holdings.stream()
                .mapToInt(holding -> holding.getCurrentPrice() * holding.getQuantityHold())
                .sum();

        int totalPL = totalCurrentPrice - totalBuyPrice;

        double totalPLPercentage = totalBuyPrice > 0
                ? (totalPL * 100.0) / totalBuyPrice
                : 0.0;

        // Create and return the PortfolioSummaryDTO
        return new PortfolioSummaryDTO(
                totalPLPercentage,
                totalCurrentPrice,
                holdingResponseDTOs,
                totalBuyPrice,
                totalPL
        );
    }

    public Holding createHolding(Holding holding) {
        // Check if the stock already exists for the given user
        Optional<Holding> existingHolding = holdingRepository.findByUserIdAndStockId(holding.getUserId(), holding.getStockId());

        if (existingHolding.isPresent()) {
            // Update the quantityHold if the stock exists
            Holding updatedHolding = existingHolding.get();
            updatedHolding.setQuantityHold(updatedHolding.getQuantityHold() + holding.getQuantityHold());
            return holdingRepository.save(updatedHolding);
        } else {
            // Create a new holding if the stock doesn't exist
            return holdingRepository.save(holding);
        }
    }

    @Transactional
    public void sellStocks(Long userId, Long stockId, int quantity) {
        Holding holding = holdingRepository.findByUserIdAndStockId(userId, stockId)
                .orElseThrow(() -> new RuntimeException("Holding not found for userId: " + userId + " and stockId: " + stockId));

        if (holding.getQuantityHold() < quantity) {
            throw new IllegalArgumentException("Insufficient quantity to sell. Available: " + holding.getQuantityHold());
        }

        if (holding.getQuantityHold() == quantity) {
            // Delete the holding if all stocks are sold
            holdingRepository.delete(holding);
        } else {
            // Update the quantity
            holding.setQuantityHold(holding.getQuantityHold() - quantity);
            holdingRepository.save(holding);
        }
    }
}
