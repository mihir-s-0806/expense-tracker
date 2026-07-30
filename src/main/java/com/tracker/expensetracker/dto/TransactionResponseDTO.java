package com.tracker.expensetracker.dto;

import com.tracker.expensetracker.entity.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponseDTO {
    private Long id;
    private String title;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDate date;
    private String notes;
    private CategoryDTO category;
}
