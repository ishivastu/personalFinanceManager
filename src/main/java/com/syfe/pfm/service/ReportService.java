package com.syfe.pfm.service;

import com.syfe.pfm.dto.ReportDTO;
import com.syfe.pfm.dto.YearlyReportDTO;
import com.syfe.pfm.model.Transaction;
import com.syfe.pfm.model.User;
import com.syfe.pfm.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final TransactionRepository transactionRepository;

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public ReportDTO getMonthlyReport(int year, int month) {
        User user = getCurrentUser();

        // Get start and end date of the month
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        // Get all transactions for the month
        List<Transaction> transactions = transactionRepository.findByUserAndDateBetweenOrderByDateDesc(
                user, startDate, endDate);

        // Calculate totals by category
        Map<String, BigDecimal> incomeByCategory = new HashMap<>();
        Map<String, BigDecimal> expensesByCategory = new HashMap<>();
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpenses = BigDecimal.ZERO;

        for (Transaction t : transactions) {
            String categoryName = t.getCategory().getName();
            BigDecimal amount = t.getAmount();

            if ("INCOME".equals(t.getType())) {
                incomeByCategory.merge(categoryName, amount, BigDecimal::add);
                totalIncome = totalIncome.add(amount);
            } else {
                expensesByCategory.merge(categoryName, amount, BigDecimal::add);
                totalExpenses = totalExpenses.add(amount);
            }
        }

        // Calculate net savings
        BigDecimal netSavings = totalIncome.subtract(totalExpenses);

        return ReportDTO.builder()
                .month(month)
                .year(year)
                .totalIncome(incomeByCategory)
                .totalExpenses(expensesByCategory)
                .netSavings(netSavings)
                .build();
    }

    public YearlyReportDTO getYearlyReport(int year) {
        User user = getCurrentUser();

        // Get start and end date of the year
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        // Get all transactions for the year
        List<Transaction> transactions = transactionRepository.findByUserAndDateBetweenOrderByDateDesc(
                user, startDate, endDate);

        // Calculate totals by category
        Map<String, BigDecimal> incomeByCategory = new HashMap<>();
        Map<String, BigDecimal> expensesByCategory = new HashMap<>();
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpenses = BigDecimal.ZERO;

        for (Transaction t : transactions) {
            String categoryName = t.getCategory().getName();
            BigDecimal amount = t.getAmount();

            if ("INCOME".equals(t.getType())) {
                incomeByCategory.merge(categoryName, amount, BigDecimal::add);
                totalIncome = totalIncome.add(amount);
            } else {
                expensesByCategory.merge(categoryName, amount, BigDecimal::add);
                totalExpenses = totalExpenses.add(amount);
            }
        }

        // Calculate net savings
        BigDecimal netSavings = totalIncome.subtract(totalExpenses);

        return YearlyReportDTO.builder()
                .year(year)
                .totalIncome(incomeByCategory)
                .totalExpenses(expensesByCategory)
                .netSavings(netSavings)
                .build();
    }
}