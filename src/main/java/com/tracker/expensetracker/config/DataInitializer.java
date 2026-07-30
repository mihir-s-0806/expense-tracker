package com.tracker.expensetracker.config;

import com.tracker.expensetracker.entity.Budget;
import com.tracker.expensetracker.entity.Category;
import com.tracker.expensetracker.entity.Transaction;
import com.tracker.expensetracker.entity.TransactionType;
import com.tracker.expensetracker.repository.BudgetRepository;
import com.tracker.expensetracker.repository.CategoryRepository;
import com.tracker.expensetracker.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() > 0) return;

        // Categories
        Category salary = categoryRepository.save(Category.builder().name("Salary").type(TransactionType.INCOME).colorHex("#10B981").icon("wallet").build());
        Category freelance = categoryRepository.save(Category.builder().name("Freelance").type(TransactionType.INCOME).colorHex("#3B82F6").icon("laptop").build());

        Category housing = categoryRepository.save(Category.builder().name("Housing & Rent").type(TransactionType.EXPENSE).colorHex("#EF4444").icon("home").build());
        Category groceries = categoryRepository.save(Category.builder().name("Groceries & Food").type(TransactionType.EXPENSE).colorHex("#F59E0B").icon("shopping-cart").build());
        Category utilities = categoryRepository.save(Category.builder().name("Utilities").type(TransactionType.EXPENSE).colorHex("#8B5CF6").icon("zap").build());
        Category entertainment = categoryRepository.save(Category.builder().name("Entertainment").type(TransactionType.EXPENSE).colorHex("#EC4899").icon("film").build());
        Category transport = categoryRepository.save(Category.builder().name("Transport").type(TransactionType.EXPENSE).colorHex("#06B6D4").icon("car").build());

        String currentMonth = YearMonth.now().toString();

        // Sample Budgets
        budgetRepository.save(Budget.builder().category(groceries).monthlyLimit(new BigDecimal("600.00")).monthYear(currentMonth).build());
        budgetRepository.save(Budget.builder().category(entertainment).monthlyLimit(new BigDecimal("150.00")).monthYear(currentMonth).build());
        budgetRepository.save(Budget.builder().category(utilities).monthlyLimit(new BigDecimal("200.00")).monthYear(currentMonth).build());
        budgetRepository.save(Budget.builder().category(transport).monthlyLimit(new BigDecimal("120.00")).monthYear(currentMonth).build());

        // Sample Transactions for current month
        LocalDate today = LocalDate.now();

        transactionRepository.save(Transaction.builder()
                .title("Tech Corp Salary Deposit")
                .amount(new BigDecimal("4500.00"))
                .type(TransactionType.INCOME)
                .date(today.minusDays(15))
                .notes("Monthly salary payout")
                .category(salary)
                .build());

        transactionRepository.save(Transaction.builder()
                .title("Web Design Freelance Project")
                .amount(new BigDecimal("850.00"))
                .type(TransactionType.INCOME)
                .date(today.minusDays(5))
                .notes("Client milestone payment")
                .category(freelance)
                .build());

        transactionRepository.save(Transaction.builder()
                .title("Apartment Monthly Rent")
                .amount(new BigDecimal("1400.00"))
                .type(TransactionType.EXPENSE)
                .date(today.minusDays(20))
                .notes("July Rent")
                .category(housing)
                .build());

        transactionRepository.save(Transaction.builder()
                .title("Whole Foods Supermarket")
                .amount(new BigDecimal("215.40"))
                .type(TransactionType.EXPENSE)
                .date(today.minusDays(10))
                .notes("Weekly grocery run")
                .category(groceries)
                .build());

        transactionRepository.save(Transaction.builder()
                .title("Organic Market Groceries")
                .amount(new BigDecimal("320.10"))
                .type(TransactionType.EXPENSE)
                .date(today.minusDays(2))
                .notes("Fresh veggies & fruits")
                .category(groceries)
                .build());

        transactionRepository.save(Transaction.builder()
                .title("Electric & Water Bill")
                .amount(new BigDecimal("175.50"))
                .type(TransactionType.EXPENSE)
                .date(today.minusDays(8))
                .notes("Monthly utility bill")
                .category(utilities)
                .build());

        transactionRepository.save(Transaction.builder()
                .title("Cinema & Snacks")
                .amount(new BigDecimal("48.00"))
                .type(TransactionType.EXPENSE)
                .date(today.minusDays(12))
                .notes("Weekend movie night")
                .category(entertainment)
                .build());

        transactionRepository.save(Transaction.builder()
                .title("Concert Tickets")
                .amount(new BigDecimal("120.00"))
                .type(TransactionType.EXPENSE)
                .date(today.minusDays(3))
                .notes("Live show tickets (Budget Exceeded Warning!)")
                .category(entertainment)
                .build());

        transactionRepository.save(Transaction.builder()
                .title("Subway Monthly Pass")
                .amount(new BigDecimal("90.00"))
                .type(TransactionType.EXPENSE)
                .date(today.minusDays(18))
                .notes("Transit card reload")
                .category(transport)
                .build());
    }
}
