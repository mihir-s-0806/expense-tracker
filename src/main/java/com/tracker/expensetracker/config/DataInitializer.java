package com.tracker.expensetracker.config;

import com.tracker.expensetracker.entity.Category;
import com.tracker.expensetracker.entity.TransactionType;
import com.tracker.expensetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() > 0) return;

        // Default Income Categories
        categoryRepository.save(Category.builder().name("Salary").type(TransactionType.INCOME).colorHex("#10B981").icon("wallet").build());
        categoryRepository.save(Category.builder().name("Freelance").type(TransactionType.INCOME).colorHex("#3B82F6").icon("laptop").build());
        categoryRepository.save(Category.builder().name("Investment").type(TransactionType.INCOME).colorHex("#8B5CF6").icon("trending-up").build());
        categoryRepository.save(Category.builder().name("Other Income").type(TransactionType.INCOME).colorHex("#6B7280").icon("plus-circle").build());

        // Default Expense Categories
        categoryRepository.save(Category.builder().name("Housing & Rent").type(TransactionType.EXPENSE).colorHex("#EF4444").icon("home").build());
        categoryRepository.save(Category.builder().name("Groceries & Food").type(TransactionType.EXPENSE).colorHex("#F59E0B").icon("shopping-cart").build());
        categoryRepository.save(Category.builder().name("Utilities").type(TransactionType.EXPENSE).colorHex("#8B5CF6").icon("zap").build());
        categoryRepository.save(Category.builder().name("Transport").type(TransactionType.EXPENSE).colorHex("#06B6D4").icon("car").build());
        categoryRepository.save(Category.builder().name("Entertainment").type(TransactionType.EXPENSE).colorHex("#EC4899").icon("film").build());
        categoryRepository.save(Category.builder().name("Healthcare").type(TransactionType.EXPENSE).colorHex("#14B8A6").icon("activity").build());
        categoryRepository.save(Category.builder().name("Shopping").type(TransactionType.EXPENSE).colorHex("#6366F1").icon("shopping-bag").build());
        categoryRepository.save(Category.builder().name("Other Expense").type(TransactionType.EXPENSE).colorHex("#6B7280").icon("folder").build());
    }
}
