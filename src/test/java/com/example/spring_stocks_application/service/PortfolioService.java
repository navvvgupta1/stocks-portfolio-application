package com.example.spring_stocks_application.service;

import com.example.spring_stocks_application.dto.HoldingResponseDTO;
import com.example.spring_stocks_application.dto.PortfolioSummaryDTO;
import com.example.spring_stocks_application.entity.Holding;
import com.example.spring_stocks_application.repository.HoldingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PortfolioServiceTest {

    @Mock
    private HoldingRepository holdingRepository;

    @InjectMocks
    private PortfolioService portfolioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreateHolding_NewHolding() {
        // Arrange
        Holding newHolding = new Holding(null, 1L, 101L, 10, 100, 120);

        when(holdingRepository.findByUserIdAndStockId(1L, 101L)).thenReturn(Optional.empty());
        when(holdingRepository.save(newHolding)).thenReturn(newHolding);

        // Act
        Holding createdHolding = portfolioService.createHolding(newHolding);

        // Assert
        assertNotNull(createdHolding);
        verify(holdingRepository, times(1)).findByUserIdAndStockId(1L, 101L);
        verify(holdingRepository, times(1)).save(newHolding);
    }

    @Test
    void testCreateHolding_UpdateExistingHolding() {
        // Arrange
        Holding existingHolding = new Holding(1L, 1L, 101L, 10, 100, 120);
        Holding updatedHolding = new Holding(1L, 1L, 101L, 20, 100, 120);

        when(holdingRepository.findByUserIdAndStockId(1L, 101L)).thenReturn(Optional.of(existingHolding));
        when(holdingRepository.save(existingHolding)).thenReturn(updatedHolding);

        // Act
        Holding result = portfolioService.createHolding(new Holding(null, 1L, 101L, 10, 100, 120));

        // Assert
        assertNotNull(result);
        assertEquals(20, result.getQuantityHold());
        verify(holdingRepository, times(1)).findByUserIdAndStockId(1L, 101L);
        verify(holdingRepository, times(1)).save(existingHolding);
    }

    @Test
    void testSellStocks_SufficientQuantity() {
        // Arrange
        Holding holding = new Holding(1L, 1L, 101L, 10, 100, 120);

        when(holdingRepository.findByUserIdAndStockId(1L, 101L)).thenReturn(Optional.of(holding));

        // Act
        portfolioService.sellStocks(1L, 101L, 5);

        // Assert
        assertEquals(5, holding.getQuantityHold());
        verify(holdingRepository, times(1)).findByUserIdAndStockId(1L, 101L);
        verify(holdingRepository, times(1)).save(holding);
    }

    @Test
    void testSellStocks_DeleteHolding() {
        // Arrange
        Holding holding = new Holding(1L, 1L, 101L, 5, 100, 120);

        when(holdingRepository.findByUserIdAndStockId(1L, 101L)).thenReturn(Optional.of(holding));

        // Act
        portfolioService.sellStocks(1L, 101L, 5);

        // Assert
        verify(holdingRepository, times(1)).findByUserIdAndStockId(1L, 101L);
        verify(holdingRepository, times(1)).delete(holding);
    }

    @Test
    void testSellStocks_InsufficientQuantity() {
        // Arrange
        Holding holding = new Holding(1L, 1L, 101L, 5, 100, 120);

        when(holdingRepository.findByUserIdAndStockId(1L, 101L)).thenReturn(Optional.of(holding));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> portfolioService.sellStocks(1L, 101L, 10));
        assertEquals("Insufficient quantity to sell. Available: 5", exception.getMessage());

        verify(holdingRepository, times(1)).findByUserIdAndStockId(1L, 101L);
        verify(holdingRepository, never()).delete(any());
        verify(holdingRepository, never()).save(any());
    }
}
