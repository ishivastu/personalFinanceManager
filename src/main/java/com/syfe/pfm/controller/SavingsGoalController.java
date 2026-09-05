package com.syfe.pfm.controller;

import com.syfe.pfm.dto.SavingsGoalDTO;
import com.syfe.pfm.dto.SavingsGoalRequest;
import com.syfe.pfm.dto.SavingsGoalUpdateRequest;
import com.syfe.pfm.service.SavingsGoalService;  // ← CORRECT import from service package
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class SavingsGoalController {
    private final SavingsGoalService savingsGoalService;  // ← Uses the service

    @PostMapping
    public ResponseEntity<SavingsGoalDTO> createGoal(@Valid @RequestBody SavingsGoalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savingsGoalService.createGoal(request));
    }

    @GetMapping
    public ResponseEntity<Map<String, List<SavingsGoalDTO>>> getAllGoals() {
        List<SavingsGoalDTO> goals = savingsGoalService.getAllGoals();

        Map<String, List<SavingsGoalDTO>> response = new HashMap<>();
        response.put("goals", goals);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SavingsGoalDTO> getGoal(@PathVariable Long id) {
        return ResponseEntity.ok(savingsGoalService.getGoal(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SavingsGoalDTO> updateGoal(
            @PathVariable Long id,
            @Valid @RequestBody SavingsGoalUpdateRequest request) {
        return ResponseEntity.ok(savingsGoalService.updateGoal(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteGoal(@PathVariable Long id) {
        savingsGoalService.deleteGoal(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Goal deleted successfully");
        return ResponseEntity.ok(response);
    }
}