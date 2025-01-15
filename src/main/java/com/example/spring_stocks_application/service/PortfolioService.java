package com.example.spring_stocks_application.service;

import com.example.spring_stocks_application.dto.HoldingResponseDTO;
import com.example.spring_stocks_application.entity.Holding;
import com.example.spring_stocks_application.entity.Stock;
import com.example.spring_stocks_application.repository.HoldingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final HoldingRepository holdingRepository;

    public PortfolioService(HoldingRepository holdingRepository){
        this.holdingRepository=holdingRepository;
    }

    public List<HoldingResponseDTO> getUserHoldings(Long userId) {
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
        return holdingResponseDTOs;
    }

    public Holding createHolding(Holding holding) {
        return holdingRepository.save(holding);
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
