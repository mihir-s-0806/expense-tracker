package com.tracker.expensetracker.controller;

import com.tracker.expensetracker.dto.CategoryDTO;
import com.tracker.expensetracker.dto.TransactionResponseDTO;
import com.tracker.expensetracker.entity.TransactionType;
import com.tracker.expensetracker.service.CsvExportService;
import com.tracker.expensetracker.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private CsvExportService csvExportService;

    @Test
    void getAllTransactions_ReturnsOkAndJsonList() throws Exception {
        CategoryDTO catDTO = CategoryDTO.builder()
                .id(1L)
                .name("Groceries")
                .type(TransactionType.EXPENSE)
                .colorHex("#F59E0B")
                .build();

        TransactionResponseDTO dto = TransactionResponseDTO.builder()
                .id(100L)
                .title("Supermarket Run")
                .amount(new BigDecimal("150.00"))
                .type(TransactionType.EXPENSE)
                .date(LocalDate.of(2026, 7, 20))
                .category(catDTO)
                .build();

        when(transactionService.getAllTransactions()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(100))
                .andExpect(jsonPath("$[0].title").value("Supermarket Run"))
                .andExpect(jsonPath("$[0].amount").value(150.00))
                .andExpect(jsonPath("$[0].category.name").value("Groceries"));
    }
}
