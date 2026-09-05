package com.syfe.pfm.controller;

import com.syfe.pfm.dto.ReportDTO;
import com.syfe.pfm.dto.YearlyReportDTO;
import com.syfe.pfm.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/monthly/{year}/{month}")
    public ResponseEntity<ReportDTO> getMonthlyReport(
            @PathVariable int year,
            @PathVariable int month) {
        return ResponseEntity.ok(reportService.getMonthlyReport(year, month));
    }

    @GetMapping("/yearly/{year}")
    public ResponseEntity<YearlyReportDTO> getYearlyReport(@PathVariable int year) {
        return ResponseEntity.ok(reportService.getYearlyReport(year));
    }
}