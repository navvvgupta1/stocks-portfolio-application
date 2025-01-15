package com.example.spring_stocks_application.repository;

import com.example.spring_stocks_application.entity.Holding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {

    // JPQL Query
    @Query("SELECT h FROM Holding h WHERE h.userId = :userId")
    List<Holding> findByUserId(@Param("userId") Long userId);

//    @Query("DELETE FROM Holding h WHERE h.stockId = :stockId")
//    void deleteAllByStockId(@Param("stockId") Long stockId);

    @Modifying
    @Query("DELETE FROM Holding h WHERE h.userId = :userId AND h.stockId = :stockId")
    void deleteByUserIdAndStockId(@Param("userId") Long userId, @Param("stockId") Long stockId);

    @Query("SELECT h FROM Holding h WHERE h.userId = :userId AND h.stockId = :stockId")
    Optional<Holding> findByUserIdAndStockId(@Param("userId") Long userId, @Param("stockId") Long stockId);
}

