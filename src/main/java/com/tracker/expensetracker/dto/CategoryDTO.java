package com.tracker.expensetracker.dto;

import com.tracker.expensetracker.entity.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private Long id;

    @NotBlank(message = "Category name is required")
    private String name;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    private String colorHex;
    private String icon;
}
