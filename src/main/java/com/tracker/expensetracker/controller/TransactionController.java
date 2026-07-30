package com.tracker.expensetracker.controller;

import com.tracker.expensetracker.dto.TransactionRequestDTO;
import com.tracker.expensetracker.dto.TransactionResponseDTO;
import com.tracker.expensetracker.service.CsvExportService;
import com.tracker.expensetracker.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final CsvExportService csvExportService;

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getTransactions(
            @RequestParam(required = false) String monthYear,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (monthYear != null) {
            return ResponseEntity.ok(transactionService.getTransactionsByMonth(monthYear));
        }
        if (categoryId != null) {
            return ResponseEntity.ok(transactionService.getTransactionsByCategory(categoryId));
        }
        if (startDate != null && endDate != null) {
            return ResponseEntity.ok(transactionService.getTransactionsByDateRange(startDate, endDate));
        }
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@Valid @RequestBody TransactionRequestDTO dto) {
        TransactionResponseDTO created = transactionService.createTransaction(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionRequestDTO dto) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export/csv")
    public ResponseEntity<InputStreamResource> exportCsv(
            @RequestParam(required = false) String monthYear) {

        List<TransactionResponseDTO> transactions = (monthYear != null)
                ? transactionService.getTransactionsByMonth(monthYear)
                : transactionService.getAllTransactions();

        ByteArrayInputStream in = csvExportService.exportTransactionsToCsv(transactions);

        String filename = "transactions_" + (monthYear != null ? monthYear : "all") + ".csv";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + filename);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(in));
    }
}
