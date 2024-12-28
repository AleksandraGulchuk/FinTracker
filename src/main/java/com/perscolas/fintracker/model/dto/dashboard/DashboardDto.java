package com.perscolas.fintracker.model.dto.dashboard;

import com.perscolas.fintracker.model.Period;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class DashboardDto {

    private BigDecimal expenseAmount;
    private BigDecimal incomeAmount;
    private BigDecimal balance;
    private Map<String, BigDecimal> balancesChar;
    private Map<String, BigDecimal> categoriesChar;
    private final Period[] periods = Period.values();
    private Period active;
    Map<String, BigDecimal> incomesSummary;
    Map<String, BigDecimal> expensesSummary;

}
