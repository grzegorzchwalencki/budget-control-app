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
  static String rndUUID1 =  UUID.randomUUID().toString();
  static String rndUUID2 =  UUID.randomUUID().toString();
  static List<ExpenseEntity> expenses;
  static ExpenseEntity expected1 = new ExpenseEntity(rndUUID1, 49.99,
      "groceries", "Biedronka market", date);
  static ExpenseEntity expected2 = new ExpenseEntity(rndUUID2, 10.00,
      "eating out ", "chinese food", date);

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
    when(mockRepository.findByExpenseId(rndUUID2)).thenReturn(Optional.of(
        new ExpenseEntity(rndUUID2, 10.00, "eating out ", "chinese food", date)));
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
}