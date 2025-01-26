package com.MyApp.budgetControl.domain;

import com.MyApp.budgetControl.domain.category.CategoryEntity;
import com.MyApp.budgetControl.domain.category.CategoryRequestDTO;
import com.MyApp.budgetControl.domain.category.CategoryResponseDTO;
import com.MyApp.budgetControl.domain.category.CategoryService;
import com.MyApp.budgetControl.domain.expense.ExpenseRequestDTO;
import com.MyApp.budgetControl.domain.expense.ExpenseResponseDTO;
import com.MyApp.budgetControl.domain.expense.ExpenseService;
import com.MyApp.budgetControl.domain.user.UserEntity;
import com.MyApp.budgetControl.domain.user.UserRequestDTO;
import com.MyApp.budgetControl.domain.user.UserResponseDTO;
import com.MyApp.budgetControl.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicesOrchestrator {

  private final CategoryService categoryService;
  private final ExpenseService expenseService;
  private final UserService userService;

  // CATEGORY
  public void saveCategory(CategoryRequestDTO categoryRequestDTO) {
    categoryService.saveCategory(categoryRequestDTO);
  }

  public List<CategoryResponseDTO> findAllCategories() {
    return categoryService.findAllCategories();
  }

  public CategoryResponseDTO findByCategoryId(String categoryId) {
    return new CategoryResponseDTO(categoryService.findCategoryById(categoryId));
  }

  // EXPENSE

  public void saveExpense(ExpenseRequestDTO expenseRequestDTO) {
    CategoryEntity category = categoryService.findCategoryById(expenseRequestDTO.getExpenseCategory());
    UserEntity user = userService.findUserById(expenseRequestDTO.getUserId());
    expenseService.saveExpense(expenseRequestDTO, category, user);
  }

  public List<ExpenseResponseDTO> findAllExpenses() {
    return expenseService.findAllExpenses();
  }

  public ExpenseResponseDTO findExpenseById(String expenseId) {
    return expenseService.findExpenseById(expenseId);
  }

  public void deleteExpenseById(String expenseId) {
    expenseService.deleteExpenseById(expenseId);
  }

  // USER
  public void saveUser(UserRequestDTO userRequestDTO) {
    userService.saveUser(userRequestDTO);
  }

  public List<UserResponseDTO> findAllUsers() {
    return userService.findAllUsers();
  }

  public UserResponseDTO findUserById(String userId) {
    return new UserResponseDTO(userService.findUserById(userId));
  }

  public void deleteUserById(String userId) {
    userService.deleteUserById(userId);
  }

}
