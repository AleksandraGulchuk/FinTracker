package com.perscolas.fintracker.model.mapper;

import com.perscolas.fintracker.model.dto.category.CategoryDto;
import com.perscolas.fintracker.model.entity.ExpenseCategory;
import com.perscolas.fintracker.model.entity.IncomeCategory;
import org.springframework.stereotype.Component;

/**
 * Mapper component for converting ExpenseCategory and IncomeCategory entities to CategoryDto objects.
 */
@Component
public class CategoryMapper {

    public CategoryDto expenseCategoryToDto(ExpenseCategory category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public CategoryDto incomeCategoryToDto(IncomeCategory category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
