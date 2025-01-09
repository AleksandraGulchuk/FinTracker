package com.perscolas.fintracker.model.dto.transaction;

import com.perscolas.fintracker.model.TimeDuration;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing a summary of transactions, including total expense, income,
 * balance, and a list of individual transactions, along with time duration options.
 */
@Data
@Builder
public class TransactionsSummaryDto {

    private BigDecimal expenseAmount;
    private BigDecimal incomeAmount;
    private BigDecimal balance;
    private List<TransactionDto> transactions;
    private final TimeDuration[] timeDurations = TimeDuration.values();
    private TimeDuration active;

}
