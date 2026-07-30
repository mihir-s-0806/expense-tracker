package com.tracker.expensetracker.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummaryResponseDTO {
    private String monthYear;
    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal netSavings;
    private Double savingsRatePercentage;
    private List<CategorySpendingDTO> categoryBreakdown;
    private List<BudgetStatusDTO> budgetStatuses;
}
