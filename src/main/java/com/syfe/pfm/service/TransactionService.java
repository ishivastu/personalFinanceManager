package com.syfe.pfm.service;

import com.syfe.pfm.dto.TransactionRequest;
import com.syfe.pfm.dto.TransactionResponse;
import com.syfe.pfm.model.Category;
import com.syfe.pfm.model.Transaction;
import com.syfe.pfm.model.User;
import com.syfe.pfm.repository.CategoryRepository;
import com.syfe.pfm.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request) {
        User user = getCurrentUser();

        // First try to find user's custom category
        Category category = categoryRepository.findByNameAndUser(request.getCategory(), user)
                .orElseGet(() -> {
                    // If not found, try to find default category (user is null)
                    return categoryRepository.findByName(request.getCategory())
                            .orElseThrow(() -> new RuntimeException("Category not found: " + request.getCategory()));
                });

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setDate(request.getDate());
        transaction.setDescription(request.getDescription());
        transaction.setType(category.getType());
        transaction.setCategory(category);
        transaction.setUser(user);

        Transaction saved = transactionRepository.save(transaction);
        return mapToResponse(saved);
    }

    public List<TransactionResponse> getTransactions(LocalDate startDate, LocalDate endDate,
                                                     Long categoryId, String type) {
        User user = getCurrentUser();
        List<Transaction> transactions;

        if (startDate != null && endDate != null) {
            transactions = transactionRepository.findByUserAndDateBetweenOrderByDateDesc(user, startDate, endDate);
        } else {
            transactions = transactionRepository.findByUserOrderByDateDesc(user);
        }

        return transactions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TransactionResponse updateTransaction(Long id, TransactionRequest request) {
        User user = getCurrentUser();
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());

        if (request.getCategory() != null) {
            Category category = categoryRepository.findByNameAndUser(request.getCategory(), user)
                    .orElseGet(() -> {
                        return categoryRepository.findByName(request.getCategory())
                                .orElseThrow(() -> new RuntimeException("Category not found: " + request.getCategory()));
                    });
            transaction.setCategory(category);
            transaction.setType(category.getType());
        }

        Transaction updated = transactionRepository.save(transaction);
        return mapToResponse(updated);
    }

    @Transactional
    public void deleteTransaction(Long id) {
        User user = getCurrentUser();
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        transactionRepository.delete(transaction);
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .date(transaction.getDate())
                .category(transaction.getCategory().getName())
                .description(transaction.getDescription())
                .type(transaction.getType())
                .build();
    }
}