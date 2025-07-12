package com.MyApp.budgetControl.api.controller;

import com.MyApp.budgetControl.domain.ServicesOrchestrator;
import com.MyApp.budgetControl.domain.expense.dto.ExpenseRequestDTO;
import com.MyApp.budgetControl.domain.expense.dto.ExpenseResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Expenses")
@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpensesController {

  private final ServicesOrchestrator servicesOrchestrator;

  @Operation(summary = "Get the list of expenses")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found an expenses"),
      @ApiResponse(responseCode = "404", description = "Expenses not found")})
  @GetMapping
  public List<ExpenseResponseDTO> getExpenses() {
    return servicesOrchestrator.findAllExpenses();
  }

  @Operation(summary = "Get the expense by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the expense"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "404", description = "Expense not found")})
  @GetMapping("/{expenseId}")
  public ExpenseResponseDTO getExpenseById(@PathVariable String expenseId) {
    return servicesOrchestrator.findExpenseById(expenseId);
  }

  @Operation(summary = "Create new expense")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Created the expense")})
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void addNewExpense(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Expense to create", required = true,
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ExpenseRequestDTO.class),
              examples = @ExampleObject(value =
                  "{ \"expenseCost\": 0.07, \"expenseCategory\": \"exampleCategory\"," +
                      "\"expenseComment\": \"exampleComment\", \"userId\": \"userIdThatAlreadyExist\" }")))
      @Valid @RequestBody ExpenseRequestDTO newExpense) {
    servicesOrchestrator.saveExpense(newExpense);
  }

  @Operation(summary = "Delete the expense by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Deleted the expense"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "404", description = "Expense not found")})
  @DeleteMapping("/{expenseId}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void deleteExpense(@PathVariable String expenseId) {
    servicesOrchestrator.deleteExpenseById(expenseId);
  }
}