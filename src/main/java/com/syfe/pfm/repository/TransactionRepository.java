package com.syfe.pfm.repository;

import com.syfe.pfm.model.Category;
import com.syfe.pfm.model.Transaction;
import com.syfe.pfm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserOrderByDateDesc(User user);

    List<Transaction> findByUserAndDateBetweenOrderByDateDesc(
            User user, LocalDate startDate, LocalDate endDate);

    @Query("SELECT t FROM Transaction t WHERE t.user = :user " +
            "AND (:startDate IS NULL OR t.date >= :startDate) " +
            "AND (:endDate IS NULL OR t.date <= :endDate) " +
            "AND (:categoryId IS NULL OR t.category.id = :categoryId) " +
            "AND (:type IS NULL OR t.type = :type) " +
            "ORDER BY t.date DESC")
    List<Transaction> findWithFilters(User user, LocalDate startDate,
                                      LocalDate endDate, Long categoryId, String type);

    List<Transaction> findByUserAndCategory(User user, Category category);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user = :user " +
            "AND t.type = :type AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal getTotalByTypeAndDateRange(@Param("user") User user,
                                          @Param("type") String type,
                                          @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);
}