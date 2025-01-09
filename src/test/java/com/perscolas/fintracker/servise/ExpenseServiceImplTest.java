package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.model.dto.transaction.SummaryDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.model.entity.ExpenseCategory;
import com.perscolas.fintracker.model.entity.Role;
import com.perscolas.fintracker.model.entity.UserAccess;
import com.perscolas.fintracker.model.entity.UserAccount;
import com.perscolas.fintracker.repository.ExpenseCategoryRepository;
import com.perscolas.fintracker.repository.ExpenseRepository;
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
class ExpenseServiceImplTest {

    @Autowired
    private ExpenseServiceImpl expenseService;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private ExpenseCategoryRepository expenseCategoryRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    private TransactionDto transactionDto;
    private final ExpenseCategory category = ExpenseCategory.builder().name("Food").build();
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
        expenseCategoryRepository.save(category);

        transactionDto = TransactionDto.builder()
                .amount(BigDecimal.valueOf(100))
                .categoryId(category.getId())
                .date(LocalDate.now())
                .build();
    }

    @AfterEach
    void tearDown() {
        expenseRepository.deleteAll();
        expenseCategoryRepository.deleteAll();
        userAccountRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void testCreateExpense() {
        expenseService.createExpense(user.getEmail(), transactionDto);
        var expenses = expenseRepository.findAll();

        assertFalse(expenses.isEmpty(), "Expenses should not be empty after creating an expense");
        assertEquals(1, expenses.size(), "There should be exactly one expense created");
    }

    @Test
    void testGetSummary() {
        expenseService.createExpense(user.getEmail(), transactionDto);
        SummaryDto summary = expenseService.getSummary(user.getEmail(), "This week");

        assertNotNull(summary, "Summary should not be null");
        assertFalse(summary.getTransactions().isEmpty(), "Transactions in summary should not be empty");
    }

    @Test
    void testDeleteExpense() {
        expenseService.createExpense(user.getEmail(), transactionDto);
        var expenses = expenseRepository.findAll();
        assertEquals(1, expenses.size(), "There should be one expense before deletion");

        expenseService.deleteExpense(expenses.get(0).getId());
        expenses = expenseRepository.findAll();

        assertTrue(expenses.isEmpty(), "Expenses should be empty after deletion");
    }

    @Test
    void testUpdateExpense() {
        expenseService.createExpense(user.getEmail(), transactionDto);
        var expense = expenseRepository.findAll().get(0);
        var newAmount = BigDecimal.valueOf(200.55);

        transactionDto.setTransactionId(expense.getId());
        transactionDto.setAmount(newAmount);
        expenseService.updateExpense(user.getEmail(), transactionDto);

        var updatedExpense = expenseRepository.findById(expense.getId()).orElseThrow();

        assertEquals(newAmount, updatedExpense.getAmount(), "Expense amount should be updated");
    }
}