package com.syfe.pfm.service;

import com.syfe.pfm.dto.SavingsGoalDTO;
import com.syfe.pfm.dto.SavingsGoalRequest;
import com.syfe.pfm.dto.SavingsGoalUpdateRequest;
import com.syfe.pfm.model.SavingsGoal;
import com.syfe.pfm.model.User;
import com.syfe.pfm.repository.SavingsGoalRepository;
import com.syfe.pfm.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavingsGoalService {
    private final SavingsGoalRepository savingsGoalRepository;
    private final TransactionRepository transactionRepository;

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Transactional
    public SavingsGoalDTO createGoal(SavingsGoalRequest request) {
        User user = getCurrentUser();

        SavingsGoal goal = new SavingsGoal();
        goal.setGoalName(request.getGoalName());
        goal.setTargetAmount(request.getTargetAmount());
        goal.setTargetDate(request.getTargetDate());
        goal.setStartDate(request.getStartDate() != null ? request.getStartDate() : LocalDate.now());
        goal.setUser(user);

        SavingsGoal saved = savingsGoalRepository.save(goal);
        return calculateProgress(saved);
    }

    public List<SavingsGoalDTO> getAllGoals() {
        User user = getCurrentUser();
        return savingsGoalRepository.findByUser(user)
                .stream()
                .map(this::calculateProgress)
                .collect(Collectors.toList());
    }

    public SavingsGoalDTO getGoal(Long id) {
        User user = getCurrentUser();
        SavingsGoal goal = savingsGoalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        if (!goal.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        return calculateProgress(goal);
    }

    @Transactional
    public SavingsGoalDTO updateGoal(Long id, SavingsGoalUpdateRequest request) {
        User user = getCurrentUser();
        SavingsGoal goal = savingsGoalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        if (!goal.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        if (request.getTargetAmount() != null) {
            goal.setTargetAmount(request.getTargetAmount());
        }
        if (request.getTargetDate() != null) {
            goal.setTargetDate(request.getTargetDate());
        }

        SavingsGoal updated = savingsGoalRepository.save(goal);
        return calculateProgress(updated);
    }

    @Transactional
    public void deleteGoal(Long id) {
        User user = getCurrentUser();
        SavingsGoal goal = savingsGoalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        if (!goal.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        savingsGoalRepository.delete(goal);
    }

    private SavingsGoalDTO calculateProgress(SavingsGoal goal) {
        User user = goal.getUser();
        LocalDate startDate = goal.getStartDate();
        LocalDate endDate = LocalDate.now();

        BigDecimal totalIncome = transactionRepository.getTotalByTypeAndDateRange(
                user, "INCOME", startDate, endDate);
        if (totalIncome == null) totalIncome = BigDecimal.ZERO;

        BigDecimal totalExpenses = transactionRepository.getTotalByTypeAndDateRange(
                user, "EXPENSE", startDate, endDate);
        if (totalExpenses == null) totalExpenses = BigDecimal.ZERO;

        BigDecimal currentProgress = totalIncome.subtract(totalExpenses);
        if (currentProgress.compareTo(BigDecimal.ZERO) < 0) {
            currentProgress = BigDecimal.ZERO;
        }

        BigDecimal targetAmount = goal.getTargetAmount();
        Double progressPercentage = 0.0;
        if (targetAmount.compareTo(BigDecimal.ZERO) > 0) {
            progressPercentage = currentProgress.divide(targetAmount, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .doubleValue();
            if (progressPercentage > 100) progressPercentage = 100.0;
        }

        BigDecimal remainingAmount = targetAmount.subtract(currentProgress);
        if (remainingAmount.compareTo(BigDecimal.ZERO) < 0) {
            remainingAmount = BigDecimal.ZERO;
        }

        return SavingsGoalDTO.builder()
                .id(goal.getId())
                .goalName(goal.getGoalName())
                .targetAmount(goal.getTargetAmount())
                .targetDate(goal.getTargetDate())
                .startDate(goal.getStartDate())
                .currentProgress(currentProgress)
                .progressPercentage(progressPercentage)
                .remainingAmount(remainingAmount)
                .build();
    }
}