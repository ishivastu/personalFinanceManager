package com.syfe.pfm.repository;

import com.syfe.pfm.model.Category;
import com.syfe.pfm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Find categories for a specific user OR default categories (user is null)
    List<Category> findByUserOrUserIsNull(User user);

    // Find category by name and user (for custom categories)
    Optional<Category> findByNameAndUser(String name, User user);

    // Find category by name only (for default categories)
    Optional<Category> findByName(String name);

    // Find custom categories for a user
    List<Category> findByUserAndIsCustomTrue(User user);

    // Check if category exists for a user
    boolean existsByNameAndUser(String name, User user);
}