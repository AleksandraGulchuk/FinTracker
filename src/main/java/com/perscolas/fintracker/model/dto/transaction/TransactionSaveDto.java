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
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSaveDto {

    private UUID transactionId;
    private UUID userId;
    private UUID categoryId;
    private LocalDate date;
    private BigDecimal amount;
    private String description;

}
