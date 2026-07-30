package com.tracker.expensetracker.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetRequestDTO {

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Monthly limit is required")
    @DecimalMin(value = "0.01", message = "Monthly limit must be greater than 0")
    private BigDecimal monthlyLimit;

    @NotBlank(message = "Month-Year is required")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$", message = "Month-Year must be in format YYYY-MM")
    private String monthYear;
}
