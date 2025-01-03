package com.perscolas.fintracker.model.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private UUID id;
    private UUID userId;
    private String type;
    private String category;
    private UUID categoryId;
    private LocalDate date;
    private BigDecimal amount;
    private String description;

}
