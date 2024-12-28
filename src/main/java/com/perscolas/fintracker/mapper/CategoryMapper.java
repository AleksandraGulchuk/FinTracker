package com.perscolas.fintracker.mapper;

import com.perscolas.fintracker.model.dto.category.CategoryDto;
import com.perscolas.fintracker.model.entity.ExpenseCategory;
import com.perscolas.fintracker.model.entity.IncomeCategory;
import org.springframework.stereotype.Component;

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
