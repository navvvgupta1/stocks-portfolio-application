package com.example.spring_stocks_application.repository;


import com.example.spring_stocks_application.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    // Additional query methods if needed
    List<Stock> findByNameContainingIgnoreCase(String name);
}
