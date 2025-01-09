package com.perscolas.fintracker.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private BigDecimal amount;
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;
    @ManyToOne
    @JoinColumn(name = "income_category_id")
    private IncomeCategory incomeCategory;

}
