package com.example.spring_stocks_application.service;

import com.example.spring_stocks_application.entity.Holding;
import com.example.spring_stocks_application.entity.Stock;
import com.example.spring_stocks_application.repository.HoldingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final HoldingRepository holdingRepository;

    public PortfolioService(HoldingRepository holdingRepository){
        this.holdingRepository=holdingRepository;
    }

    public List<Holding> getUserHoldings(Long userId) {
//        System.out.println(userId);
        List<Holding> hold=holdingRepository.findByUserId(userId);
        System.out.println(hold);
        return hold;
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
