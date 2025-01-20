package com.example.spring_stocks_application.service;


import com.example.spring_stocks_application.entity.Stock;
import com.example.spring_stocks_application.repository.StockRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    private static final String BHAV_FILE_URL="";

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock getStockById(Long id) {
        Optional<Stock> stock = stockRepository.findById(id);
        return stock.orElseThrow(() -> new RuntimeException("Stock not found with ID: " + id));
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Stock updateStock(Long id, Stock stockDetails) {
        Stock stock = getStockById(id);

        stock.setName(stockDetails.getName());
        stock.setOpenPrice(stockDetails.getOpenPrice());
        stock.setClosePrice(stockDetails.getClosePrice());
        stock.setHighPrice(stockDetails.getHighPrice());
        stock.setLowPrice(stockDetails.getLowPrice());
        stock.setSettlementPrice(stockDetails.getSettlementPrice());

        return stockRepository.save(stock);
    }

    public void deleteStock(Long id) {
        Stock stock = getStockById(id);
        stockRepository.delete(stock);
    }

    public void saveStocksFromCsv(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            List<Stock> stocks = new ArrayList<>();
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) { // Skip the header row
                    isHeader = false;
                    continue;
                }

                String[] fields = line.split(",");
                Stock stock = new Stock();
                stock.setName(fields[0]);
                stock.setOpenPrice(Double.parseDouble(fields[2]));
                stock.setHighPrice(Double.parseDouble(fields[3]));
                stock.setLowPrice(Double.parseDouble(fields[4]));
                stock.setClosePrice(Double.parseDouble(fields[5]));
                stock.setSettlementPrice(Double.parseDouble(fields[6]));
                stock.setCurrentPrice(Double.parseDouble(fields[7]));

                stocks.add(stock);
            }

            stockRepository.saveAll(stocks);

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage(), e);
        }
    }

    @Scheduled(cron = "0 0 10 * * ?") // Runs daily at 10 AM
    public void downloadAndUpdateStockData() {
        try {
            // Download the Bhav file

            URL url = new URL(BHAV_FILE_URL);
            try (InputStream inputStream = url.openStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                String line;
                List<Stock> stocks = new ArrayList<>();
                boolean isHeader = true;

                while ((line = reader.readLine()) != null) {
                    if (isHeader) { // Skip the header row
                        isHeader = false;
                        continue;
                    }

                    String[] fields = line.split(",");
                    Stock stock = new Stock();
                    stock.setName(fields[0]);
                    stock.setOpenPrice(Double.parseDouble(fields[2]));
                    stock.setHighPrice(Double.parseDouble(fields[3]));
                    stock.setLowPrice(Double.parseDouble(fields[4]));
                    stock.setClosePrice(Double.parseDouble(fields[5]));
                    stock.setSettlementPrice(Double.parseDouble(fields[6]));
                    stock.setCurrentPrice(Double.parseDouble(fields[7]));

                    stocks.add(stock);
                }

                // Save all stocks to the database
                stockRepository.saveAll(stocks);

                System.out.println("Stock data updated successfully at 10 AM.");
            }
        } catch (Exception e) {
            System.err.println("Failed to download or update stock data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

