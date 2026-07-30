package com.tracker.expensetracker.service;

import com.tracker.expensetracker.dto.CategoryDTO;
import com.tracker.expensetracker.dto.TransactionRequestDTO;
import com.tracker.expensetracker.dto.TransactionResponseDTO;
import com.tracker.expensetracker.entity.Category;
import com.tracker.expensetracker.entity.Transaction;
import com.tracker.expensetracker.exception.ResourceNotFoundException;
import com.tracker.expensetracker.repository.CategoryRepository;
import com.tracker.expensetracker.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    public List<TransactionResponseDTO> getAllTransactions() {
        return transactionRepository.findAllByOrderByDateDesc().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionResponseDTO> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByDateBetweenOrderByDateDesc(startDate, endDate).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionResponseDTO> getTransactionsByMonth(String monthYear) {
        YearMonth ym = YearMonth.parse(monthYear);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();
        return getTransactionsByDateRange(start, end);
    }

    public List<TransactionResponseDTO> getTransactionsByCategory(Long categoryId) {
        return transactionRepository.findByCategoryIdOrderByDateDesc(categoryId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public TransactionResponseDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
        return mapToResponseDTO(transaction);
    }

    public TransactionResponseDTO createTransaction(TransactionRequestDTO requestDTO) {
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + requestDTO.getCategoryId()));

        Transaction transaction = Transaction.builder()
                .title(requestDTO.getTitle())
                .amount(requestDTO.getAmount())
                .type(requestDTO.getType())
                .date(requestDTO.getDate())
                .notes(requestDTO.getNotes())
                .category(category)
                .build();

        Transaction saved = transactionRepository.save(transaction);
        return mapToResponseDTO(saved);
    }

    public TransactionResponseDTO updateTransaction(Long id, TransactionRequestDTO requestDTO) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));

        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + requestDTO.getCategoryId()));

        transaction.setTitle(requestDTO.getTitle());
        transaction.setAmount(requestDTO.getAmount());
        transaction.setType(requestDTO.getType());
        transaction.setDate(requestDTO.getDate());
        transaction.setNotes(requestDTO.getNotes());
        transaction.setCategory(category);

        Transaction updated = transactionRepository.save(transaction);
        return mapToResponseDTO(updated);
    }

    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);
    }

    private TransactionResponseDTO mapToResponseDTO(Transaction t) {
        return TransactionResponseDTO.builder()
                .id(t.getId())
                .title(t.getTitle())
                .amount(t.getAmount())
                .type(t.getType())
                .date(t.getDate())
                .notes(t.getNotes())
                .category(categoryService.mapToDTO(t.getCategory()))
                .build();
    }
}
