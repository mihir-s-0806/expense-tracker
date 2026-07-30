package com.tracker.expensetracker.controller;

import com.tracker.expensetracker.dto.BudgetRequestDTO;
import com.tracker.expensetracker.dto.BudgetResponseDTO;
import com.tracker.expensetracker.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping
    public ResponseEntity<List<BudgetResponseDTO>> getBudgets(
            @RequestParam String monthYear) {
        return ResponseEntity.ok(budgetService.getBudgetsByMonth(monthYear));
    }

    @PostMapping
    public ResponseEntity<BudgetResponseDTO> saveBudget(@Valid @RequestBody BudgetRequestDTO dto) {
        BudgetResponseDTO saved = budgetService.createOrUpdateBudget(dto);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}
