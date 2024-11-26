package com.MyApp.budgetControl.domain.expense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExpensesServiceTest {

  @Mock
  ExpenseRepository mockRepository;
  @InjectMocks
  ExpensesService subject;
  static Instant date = new Date().toInstant();
  static UUID rndUUID1 =  UUID.randomUUID();
  static UUID rndUUID2 =  UUID.randomUUID();
  static List<ExpenseEntity> expenses;

  @BeforeAll
  static void setup() {
    expenses = Arrays.asList(
              new ExpenseEntity(rndUUID1, 49.99, "groceries", "Biedronka market", date),
              new ExpenseEntity(rndUUID2, 10.00, "eating out ", "chinese food", date));
  }

  @Test
  void getExpensesShouldReturnAtLeastOneExpanse() {
    when(mockRepository.findAll()).thenReturn(expenses);
    List<ExpenseEntity> expanses = subject.findAllExpenses();
    ExpenseEntity expected = new ExpenseEntity(
              rndUUID1,
              49.99,
              "groceries",
              "Biedronka market",
              date);
    assertTrue(expanses.contains(expected));
  }

  @Test
  void getExpenseByIdForExistingIdIsReturningCorrectExpense() {
    when(mockRepository.findByExpenseId(rndUUID2)).thenReturn(Optional.of(
        new ExpenseEntity(rndUUID2, 10.00, "eating out ", "chinese food", date)));
    ExpenseEntity result = subject.findExpenseById(rndUUID2);
    ExpenseEntity expected = new ExpenseEntity(
              rndUUID2,
              10.00,
              "eating out ",
              "chinese food",
              date);
    assertEquals(expected, result);
  }

  @Test
  void getExpenseByIdFoNotExistingIdShouldThrowException() {
    UUID randomUUID = UUID.randomUUID();
    NoSuchElementException exception =
        assertThrows(NoSuchElementException.class, () -> subject.findExpenseById(randomUUID));
    assertEquals("No value present", exception.getMessage());
  }
}