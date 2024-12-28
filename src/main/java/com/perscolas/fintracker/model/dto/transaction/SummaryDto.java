package com.perscolas.fintracker.model.dto.transaction;

import com.perscolas.fintracker.model.dto.category.CategoryDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class SummaryDto {

    private List<CategoryDto> categories;
    private List<TransactionDto> transactions;
    private Map<String, BigDecimal> summary;

}
