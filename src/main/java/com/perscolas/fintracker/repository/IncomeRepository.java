package com.perscolas.fintracker.repository;


import com.perscolas.fintracker.model.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface IncomeRepository extends JpaRepository<Income, UUID> {

    List<Income> findAllByUserAccountIdOrderByDateDesc(UUID userId);

    List<Income> findAllByUserAccountIdAndDateAfterOrderByDateDesc(UUID userAccountId, LocalDate date);

}
