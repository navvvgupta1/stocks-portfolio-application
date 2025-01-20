package com.example.spring_stocks_application.service;

import com.example.spring_stocks_application.entity.Stock;
import com.example.spring_stocks_application.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockService stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStockById() {
        Stock stock = new Stock();
        stock.setId(1L);
        stock.setName("Test Stock");

        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        Stock result = stockService.getStockById(1L);

        assertNotNull(result);
        assertEquals("Test Stock", result.getName());
        verify(stockRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllStocks() {
        Stock stock1 = new Stock();
        Stock stock2 = new Stock();
        when(stockRepository.findAll()).thenReturn(Arrays.asList(stock1, stock2));

        List<Stock> result = stockService.getAllStocks();

        assertEquals(2, result.size());
        verify(stockRepository, times(1)).findAll();
    }

    @Test
    void testCreateStock() {
        Stock stock = new Stock();
        stock.setName("Test Stock");

        when(stockRepository.save(stock)).thenReturn(stock);

        Stock result = stockService.createStock(stock);

        assertNotNull(result);
        assertEquals("Test Stock", result.getName());
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    void testUpdateStock() {
        Stock existingStock = new Stock();
        existingStock.setId(1L);
        existingStock.setName("Old Stock");

        Stock updatedStock = new Stock();
        updatedStock.setName("Updated Stock");

        when(stockRepository.findById(1L)).thenReturn(Optional.of(existingStock));
        when(stockRepository.save(existingStock)).thenReturn(existingStock);

        Stock result = stockService.updateStock(1L, updatedStock);

        assertNotNull(result);
        assertEquals("Updated Stock", result.getName());
        verify(stockRepository, times(1)).findById(1L);
        verify(stockRepository, times(1)).save(existingStock);
    }

    @Test
    void testDeleteStock() {
        Stock stock = new Stock();
        stock.setId(1L);

        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        doNothing().when(stockRepository).delete(stock);

        stockService.deleteStock(1L);

        verify(stockRepository, times(1)).findById(1L);
        verify(stockRepository, times(1)).delete(stock);
    }
}
