package com.MyApp.budgetControl.domain.expense;

import com.MyApp.budgetControl.domain.category.CategoryEntity;
import com.MyApp.budgetControl.domain.user.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

  @Mock
  ExpenseRepository mockRepository;
  @InjectMocks
  ExpenseService subject;
  static Instant date = new Date().toInstant();
  static String rndUUID1 =  UUID.randomUUID().toString();
  static String rndUUID2 =  UUID.randomUUID().toString();
  static String rndUUID3 =  UUID.randomUUID().toString();
  static List<ExpenseEntity> expenses;
  static UserEntity user = new UserEntity(rndUUID3, "testUserName", "testUser@email.com", new ArrayList<>());
  static CategoryEntity category = new CategoryEntity(rndUUID3, "testCategory", new ArrayList<>());
  static ExpenseEntity expected1 = new ExpenseEntity(rndUUID1, 49.99,
      category, "Example market", date, user);
  static ExpenseEntity expected2 = new ExpenseEntity(rndUUID2, 10.00,
      category, "chinese food", date, user);
  static ExpenseRequestDTO expenseDTO = new ExpenseRequestDTO(10, rndUUID3, "chinese food", rndUUID3);

  @BeforeAll
  static void setup() {
    expenses = Arrays.asList(expected1, expected2);
  }

  @Test
  void getExpensesShouldReturnAtLeastOneExpanse() {
    when(mockRepository.findAll()).thenReturn(expenses);
    List<ExpenseResponseDTO> expanses = subject.findAllExpenses();
    ExpenseResponseDTO expectedDTO = new ExpenseResponseDTO(expected1);
    assertTrue(expanses.contains(expectedDTO));
  }

  @Test
  void getExpenseByIdForExistingIdIsReturningCorrectExpense() {
    when(mockRepository.findById(rndUUID2)).thenReturn(Optional.of(
        new ExpenseEntity(rndUUID2, 10.00, category, "chinese food", date, user)));
    ExpenseResponseDTO result = subject.findExpenseById(rndUUID2);
    ExpenseResponseDTO expectedDTO = new ExpenseResponseDTO(expected2);
    assertEquals(expectedDTO, result);
  }

  @Test
  void getExpenseByIdFoNotExistingIdShouldThrowException() {
    String randomUUID = UUID.randomUUID().toString();
    NoSuchElementException exception =
        assertThrows(NoSuchElementException.class, () -> subject.findExpenseById(randomUUID));
    assertEquals("No value present", exception.getMessage());
  }

  @Test
  void saveNewExpenseShouldAddItToUserExpensesAndCategoryExpensesLists() {
    ExpenseEntity newExpense = subject.saveExpense(expenseDTO, category, user);
    assertEquals(newExpense.getExpenseId(), category.getCategoryExpenses().get(0).getExpenseId());
    assertEquals(newExpense.getExpenseId(), user.getUserExpenses().get(0).getExpenseId());
  }
}