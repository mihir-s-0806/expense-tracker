package com.tracker.expensetracker.service;

import com.tracker.expensetracker.dto.TransactionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvExportService {

    public ByteArrayInputStream exportTransactionsToCsv(List<TransactionResponseDTO> transactions) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PrintWriter writer = new PrintWriter(out)) {
            // Write CSV Header
            writer.println("ID,Date,Title,Type,Category,Amount,Notes");

            // Write Data Rows
            for (TransactionResponseDTO t : transactions) {
                String notes = t.getNotes() != null ? t.getNotes().replace("\"", "\"\"") : "";
                String line = String.format("%d,%s,\"%s\",%s,\"%s\",%.2f,\"%s\"",
                        t.getId(),
                        t.getDate(),
                        t.getTitle().replace("\"", "\"\""),
                        t.getType(),
                        t.getCategory().getName(),
                        t.getAmount(),
                        notes
                );
                writer.println(line);
            }
            writer.flush();
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
