package com.perscolas.fintracker.model.dto.transaction;

import com.perscolas.fintracker.model.Period;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class TransactionsSummaryDto {

    private BigDecimal expenseAmount;
    private BigDecimal incomeAmount;
    private BigDecimal balance;
    private List<TransactionDto> transactions;
    //    private final Period[] periods = {Period.MONTH,Period.THREE_MONTHS, Period.SIX_MONTHS};
    private final Period[] periods = Period.values();
    private Period active;
}
