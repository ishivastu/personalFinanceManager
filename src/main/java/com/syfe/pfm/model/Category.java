package com.syfe.pfm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(name = "is_custom")
    private boolean isCustom = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // Prevents infinite recursion
    private User user;

    // Helper method for response
    public boolean getIsCustom() {
        return isCustom;
    }
}