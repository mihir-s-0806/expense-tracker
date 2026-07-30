package com.tracker.expensetracker.service;

import com.tracker.expensetracker.dto.SummaryResponseDTO;
import com.tracker.expensetracker.entity.Budget;
import com.tracker.expensetracker.entity.Category;
import com.tracker.expensetracker.entity.TransactionType;
import com.tracker.expensetracker.repository.BudgetRepository;
import com.tracker.expensetracker.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InsightsServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private BudgetRepository budgetRepository;

    @InjectMocks
    private InsightsService insightsService;

    private Category testCategory;
    private Budget testBudget;

    @BeforeEach
    void setUp() {
        testCategory = Category.builder()
                .id(1L)
                .name("Groceries")
                .type(TransactionType.EXPENSE)
                .colorHex("#F59E0B")
                .build();

        testBudget = Budget.builder()
                .id(10L)
                .category(testCategory)
                .monthlyLimit(new BigDecimal("500.00"))
                .monthYear("2026-07")
                .build();
    }

    @Test
    void getMonthlySummary_CalculatesCorrectSavingsAndAlertStatus() {
        String monthYear = "2026-07";

        when(transactionRepository.sumAmountByTypeAndDateBetween(eq(TransactionType.INCOME), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(new BigDecimal("3000.00"));

        when(transactionRepository.sumAmountByTypeAndDateBetween(eq(TransactionType.EXPENSE), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(new BigDecimal("1200.00"));

        when(budgetRepository.findByMonthYear(monthYear))
                .thenReturn(List.of(testBudget));

        when(transactionRepository.sumExpenseByCategoryIdAndDateBetween(eq(1L), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(new BigDecimal("450.00")); // 450 out of 500 = 90% -> WARNING alert status

        when(transactionRepository.sumExpensesByCategoryAndDateBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());

        SummaryResponseDTO summary = insightsService.getMonthlySummary(monthYear);

        assertNotNull(summary);
        assertEquals(new BigDecimal("3000.00"), summary.getTotalIncome());
        assertEquals(new BigDecimal("1200.00"), summary.getTotalExpenses());
        assertEquals(new BigDecimal("1800.00"), summary.getNetSavings());
        assertEquals(60.0, summary.getSavingsRatePercentage());

        assertEquals(1, summary.getBudgetStatuses().size());
        assertEquals("WARNING", summary.getBudgetStatuses().get(0).getAlertStatus());
        assertEquals(90.0, summary.getBudgetStatuses().get(0).getUsagePercentage());
    }
}
