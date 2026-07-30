package com.tracker.expensetracker.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategorySpendingDTO {
    private Long categoryId;
    private String categoryName;
    private String colorHex;
    private BigDecimal amountSpent;
    private Double percentageOfTotal;
}
