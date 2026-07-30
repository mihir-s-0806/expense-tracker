package com.tracker.expensetracker.controller;

import com.tracker.expensetracker.dto.SummaryResponseDTO;
import com.tracker.expensetracker.service.InsightsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/insights")
@RequiredArgsConstructor
public class InsightsController {

    private final InsightsService insightsService;

    @GetMapping("/summary")
    public ResponseEntity<SummaryResponseDTO> getSummary(
            @RequestParam(required = false) String monthYear) {
        String targetMonthYear = (monthYear != null) ? monthYear : YearMonth.now().toString();
        return ResponseEntity.ok(insightsService.getMonthlySummary(targetMonthYear));
    }
}
