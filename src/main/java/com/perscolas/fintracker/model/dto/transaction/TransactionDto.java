package com.perscolas.fintracker.model.dto.transaction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) representing a transaction, including details such as transaction ID, user ID,
 * type, category, amount, date, and description, with validation constraints.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private UUID transactionId;
    private UUID userId;
    private String type;
    private String category;
    @NotNull(message = "Category must not be empty")
    private UUID categoryId;
    @NotNull(message = "Date must not be empty")
    private LocalDate date;
    @DecimalMin(value = "0.01")
    private BigDecimal amount;
    private String description;

}
