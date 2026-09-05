package com.syfe.pfm.service;

import com.syfe.pfm.model.Category;
import com.syfe.pfm.model.User;
import com.syfe.pfm.repository.CategoryRepository;
import com.syfe.pfm.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<Category> getAllCategories() {
        User user = getCurrentUser();
        return categoryRepository.findByUserOrUserIsNull(user);
    }

    @Transactional
    public Category createCustomCategory(String name, String type) {
        User user = getCurrentUser();

        // Check if category already exists for this user
        if (categoryRepository.existsByNameAndUser(name, user)) {
            throw new RuntimeException("Category already exists: " + name);
        }

        // Check if default category with same name exists
        if (categoryRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Default category already exists: " + name);
        }

        Category category = new Category();
        category.setName(name);
        category.setType(type);
        category.setCustom(true);
        category.setUser(user);

        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCustomCategory(String name) {
        User user = getCurrentUser();

        Category category = categoryRepository.findByNameAndUser(name, user)
                .orElseThrow(() -> new RuntimeException("Category not found: " + name));

        // Check if category is being used by any transaction
        if (!transactionRepository.findByUserAndCategory(user, category).isEmpty()) {
            throw new RuntimeException("Category cannot be deleted as it's referenced by transactions");
        }

        categoryRepository.delete(category);
    }
}