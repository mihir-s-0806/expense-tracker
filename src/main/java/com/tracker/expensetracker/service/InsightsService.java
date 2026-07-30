package com.tracker.expensetracker.service;

import com.tracker.expensetracker.dto.BudgetStatusDTO;
import com.tracker.expensetracker.dto.CategorySpendingDTO;
import com.tracker.expensetracker.dto.SummaryResponseDTO;
import com.tracker.expensetracker.entity.Budget;
import com.tracker.expensetracker.entity.TransactionType;
import com.tracker.expensetracker.repository.BudgetRepository;
import com.tracker.expensetracker.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InsightsService {

    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;

    public SummaryResponseDTO getMonthlySummary(String monthYear) {
        YearMonth ym = YearMonth.parse(monthYear);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        BigDecimal totalIncome = transactionRepository.sumAmountByTypeAndDateBetween(TransactionType.INCOME, start, end);
        if (totalIncome == null) totalIncome = BigDecimal.ZERO;

        BigDecimal totalExpenses = transactionRepository.sumAmountByTypeAndDateBetween(TransactionType.EXPENSE, start, end);
        if (totalExpenses == null) totalExpenses = BigDecimal.ZERO;

        BigDecimal netSavings = totalIncome.subtract(totalExpenses);

        Double savingsRate = 0.0;
        if (totalIncome.compareTo(BigDecimal.ZERO) > 0) {
            savingsRate = netSavings.divide(totalIncome, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .doubleValue();
        }

        // Category breakdown
        List<Object[]> rawBreakdown = transactionRepository.sumExpensesByCategoryAndDateBetween(start, end);
        List<CategorySpendingDTO> categoryBreakdown = new ArrayList<>();

        for (Object[] row : rawBreakdown) {
            Long catId = (Long) row[0];
            String catName = (String) row[1];
            String color = (String) row[2];
            BigDecimal spent = (BigDecimal) row[3];

            Double percentage = 0.0;
            if (totalExpenses.compareTo(BigDecimal.ZERO) > 0) {
                percentage = spent.divide(totalExpenses, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100))
                        .doubleValue();
            }

            categoryBreakdown.add(CategorySpendingDTO.builder()
                    .categoryId(catId)
                    .categoryName(catName)
                    .colorHex(color)
                    .amountSpent(spent)
                    .percentageOfTotal(percentage)
                    .build());
        }

        // Budget Statuses & Alerts
        List<Budget> budgets = budgetRepository.findByMonthYear(monthYear);
        List<BudgetStatusDTO> budgetStatuses = new ArrayList<>();

        for (Budget b : budgets) {
            Long catId = b.getCategory().getId();
            BigDecimal limit = b.getMonthlyLimit();

            BigDecimal spent = transactionRepository.sumExpenseByCategoryIdAndDateBetween(catId, start, end);
            if (spent == null) spent = BigDecimal.ZERO;

            BigDecimal remaining = limit.subtract(spent);

            Double usagePct = 0.0;
            if (limit.compareTo(BigDecimal.ZERO) > 0) {
                usagePct = spent.divide(limit, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100))
                        .doubleValue();
            }

            String alertStatus = "OK";
            if (usagePct >= 100.0) {
                alertStatus = "EXCEEDED";
            } else if (usagePct >= 80.0) {
                alertStatus = "WARNING";
            }

            budgetStatuses.add(BudgetStatusDTO.builder()
                    .budgetId(b.getId())
                    .categoryId(catId)
                    .categoryName(b.getCategory().getName())
                    .colorHex(b.getCategory().getColorHex())
                    .monthlyLimit(limit)
                    .currentSpent(spent)
                    .remainingAmount(remaining)
                    .usagePercentage(usagePct)
                    .alertStatus(alertStatus)
                    .build());
        }

        return SummaryResponseDTO.builder()
                .monthYear(monthYear)
                .totalIncome(totalIncome)
                .totalExpenses(totalExpenses)
                .netSavings(netSavings)
                .savingsRatePercentage(savingsRate)
                .categoryBreakdown(categoryBreakdown)
                .budgetStatuses(budgetStatuses)
                .build();
    }
}
