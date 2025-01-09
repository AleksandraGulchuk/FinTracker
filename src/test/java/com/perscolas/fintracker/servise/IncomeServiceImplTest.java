package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.model.dto.transaction.SummaryDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.model.entity.IncomeCategory;
import com.perscolas.fintracker.model.entity.Role;
import com.perscolas.fintracker.model.entity.UserAccess;
import com.perscolas.fintracker.model.entity.UserAccount;
import com.perscolas.fintracker.repository.IncomeCategoryRepository;
import com.perscolas.fintracker.repository.IncomeRepository;
import com.perscolas.fintracker.repository.RoleRepository;
import com.perscolas.fintracker.repository.UserAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class IncomeServiceImplTest {

    @Autowired
    private IncomeServiceImpl incomeService;
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private IncomeCategoryRepository incomeCategoryRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    private TransactionDto transactionDto;
    private final IncomeCategory category = IncomeCategory.builder().name("Salary").build();
    private final Role role = Role.builder().name("ROLE_USER").build();
    private final UserAccess userAccess = UserAccess.builder()
            .isAccountNonExpired(true)
            .isAccountNonLocked(true)
            .isCredentialsNonExpired(true)
            .isEnabled(true)
            .roles(Set.of(role))
            .build();
    private final UserAccount user = UserAccount.builder()
            .name("User")
            .email("email@email.com")
            .password("password")
            .userAccess(userAccess)
            .build();

    @BeforeEach
    void setUp() {
        roleRepository.save(role);
        userAccountRepository.save(user);
        incomeCategoryRepository.save(category);

        transactionDto = TransactionDto.builder()
                .amount(BigDecimal.valueOf(100))
                .categoryId(category.getId())
                .date(LocalDate.now())
                .build();
    }

    @AfterEach
    void tearDown() {
        incomeRepository.deleteAll();
        incomeCategoryRepository.deleteAll();
        userAccountRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void testCreateIncome() {
        incomeService.createIncome(user.getEmail(), transactionDto);
        var incomes = incomeRepository.findAll();

        assertFalse(incomes.isEmpty(), "Incomes should not be empty after creating an income");
        assertEquals(1, incomes.size(), "There should be exactly one income created");
    }

    @Test
    void testGetSummary() {
        incomeService.createIncome(user.getEmail(), transactionDto);
        SummaryDto summary = incomeService.getSummary(user.getEmail(), "This week");

        assertNotNull(summary, "Summary should not be null");
        assertFalse(summary.getTransactions().isEmpty(), "Transactions in summary should not be empty");
    }

    @Test
    void testDeleteIncome() {
        incomeService.createIncome(user.getEmail(), transactionDto);
        var incomes = incomeRepository.findAll();
        assertEquals(1, incomes.size(), "There should be one income before deletion");

        incomeService.deleteIncome(incomes.get(0).getId());
        incomes = incomeRepository.findAll();

        assertTrue(incomes.isEmpty(), "Incomes should be empty after deletion");
    }

    @Test
    void testUpdateIncome() {
        incomeService.createIncome(user.getEmail(), transactionDto);
        var income = incomeRepository.findAll().get(0);
        var newAmount = BigDecimal.valueOf(200.55);

        transactionDto.setTransactionId(income.getId());
        transactionDto.setAmount(newAmount);
        incomeService.updateIncome(user.getEmail(), transactionDto);

        var updatedIncome = incomeRepository.findById(income.getId()).orElseThrow();

        assertEquals(newAmount, updatedIncome.getAmount(), "Income amount should be updated");
    }
}