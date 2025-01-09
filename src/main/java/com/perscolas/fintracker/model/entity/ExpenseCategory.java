package com.perscolas.fintracker.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Represents a category for expenses, such as food, travel, or utilities.
 * Each category has a unique identifier and name.
 * This class is a JPA entity, meaning it is mapped to a database table and can be persisted in a relational database.
 */
@Entity
@Builder
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;

}
