package com.tracker.expensetracker.service;

import com.tracker.expensetracker.dto.BudgetRequestDTO;
import com.tracker.expensetracker.dto.BudgetResponseDTO;
import com.tracker.expensetracker.entity.Budget;
import com.tracker.expensetracker.entity.Category;
import com.tracker.expensetracker.exception.ResourceNotFoundException;
import com.tracker.expensetracker.repository.BudgetRepository;
import com.tracker.expensetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    public List<BudgetResponseDTO> getBudgetsByMonth(String monthYear) {
        return budgetRepository.findByMonthYear(monthYear).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public BudgetResponseDTO createOrUpdateBudget(BudgetRequestDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

        Optional<Budget> existingOpt = budgetRepository.findByCategoryIdAndMonthYear(dto.getCategoryId(), dto.getMonthYear());

        Budget budget;
        if (existingOpt.isPresent()) {
            budget = existingOpt.get();
            budget.setMonthlyLimit(dto.getMonthlyLimit());
        } else {
            budget = Budget.builder()
                    .category(category)
                    .monthlyLimit(dto.getMonthlyLimit())
                    .monthYear(dto.getMonthYear())
                    .build();
        }

        Budget saved = budgetRepository.save(budget);
        return mapToDTO(saved);
    }

    public void deleteBudget(Long id) {
        if (!budgetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Budget not found with id: " + id);
        }
        budgetRepository.deleteById(id);
    }

    public BudgetResponseDTO mapToDTO(Budget budget) {
        return BudgetResponseDTO.builder()
                .id(budget.getId())
                .category(categoryService.mapToDTO(budget.getCategory()))
                .monthlyLimit(budget.getMonthlyLimit())
                .monthYear(budget.getMonthYear())
                .build();
    }
}
