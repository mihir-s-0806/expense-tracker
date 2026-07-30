package com.tracker.expensetracker.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetStatusDTO {
    private Long budgetId;
    private Long categoryId;
    private String categoryName;
    private String colorHex;
    private BigDecimal monthlyLimit;
    private BigDecimal currentSpent;
    private BigDecimal remainingAmount;
    private Double usagePercentage;
    private String alertStatus; // OK, WARNING, EXCEEDED
}
