package com.syfe.pfm.repository;

import com.syfe.pfm.model.SavingsGoal;
import com.syfe.pfm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Long> {
    // Find all savings goals for a specific user
    List<SavingsGoal> findByUser(User user);

    // Optional: Check if a goal exists for a user by name
    boolean existsByUserIdAndGoalName(Long userId, String goalName);
}