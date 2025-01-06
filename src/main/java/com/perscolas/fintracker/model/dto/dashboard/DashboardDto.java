package com.perscolas.fintracker.model.dto.dashboard;

import com.perscolas.fintracker.model.TimeDuration;
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
    private Map<String, BigDecimal> balancesChart;
    private Map<String, BigDecimal> expenseCategoriesChart;
    private Map<String, BigDecimal> incomeCategoriesChart;
    private final TimeDuration[] timeDurations = TimeDuration.values();
    private TimeDuration active;
    Map<String, BigDecimal> incomesChart;
    Map<String, BigDecimal> expensesChart;

}
