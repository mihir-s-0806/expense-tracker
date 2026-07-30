package com.tracker.expensetracker.repository;

import com.tracker.expensetracker.entity.Transaction;
import com.tracker.expensetracker.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByDateBetweenOrderByDateDesc(LocalDate startDate, LocalDate endDate);

    List<Transaction> findByCategoryIdOrderByDateDesc(Long categoryId);

    List<Transaction> findAllByOrderByDateDesc();

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal sumAmountByTypeAndDateBetween(@Param("type") TransactionType type,
                                            @Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate);

    @Query("SELECT t.category.id, t.category.name, t.category.colorHex, SUM(t.amount) " +
           "FROM Transaction t " +
           "WHERE t.type = 'EXPENSE' AND t.date BETWEEN :startDate AND :endDate " +
           "GROUP BY t.category.id, t.category.name, t.category.colorHex")
    List<Object[]> sumExpensesByCategoryAndDateBetween(@Param("startDate") LocalDate startDate,
                                                       @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.category.id = :categoryId AND t.type = 'EXPENSE' AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal sumExpenseByCategoryIdAndDateBetween(@Param("categoryId") Long categoryId,
                                                    @Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate);
}
