package com.tracker.expensetracker.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetResponseDTO {
    private Long id;
    private CategoryDTO category;
    private BigDecimal monthlyLimit;
    private String monthYear;
}
