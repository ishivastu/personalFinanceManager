package com.syfe.pfm.config;

import com.syfe.pfm.model.Category;
import com.syfe.pfm.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryInitializer implements CommandLineRunner {
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        // Check if categories already exist
        if (categoryRepository.count() == 0) {
            System.out.println("🌱 Creating default categories...");

            createCategory("Salary", "INCOME");
            createCategory("Food", "EXPENSE");
            createCategory("Rent", "EXPENSE");
            createCategory("Transportation", "EXPENSE");
            createCategory("Entertainment", "EXPENSE");
            createCategory("Healthcare", "EXPENSE");
            createCategory("Utilities", "EXPENSE");

            System.out.println("✅ Default categories created successfully!");
        } else {
            System.out.println("✅ Categories already exist. Count: " + categoryRepository.count());
        }
    }

    private void createCategory(String name, String type) {
        Category category = new Category();
        category.setName(name);
        category.setType(type);
        category.setCustom(false);
        category.setUser(null); // null = default category for all users
        categoryRepository.save(category);
        System.out.println("   Created category: " + name + " (" + type + ")");
    }
}