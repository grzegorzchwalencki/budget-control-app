package com.MyApp.budgetControl.domain.user.dto;

import com.MyApp.budgetControl.domain.expense.dto.ExpenseResponseDTO;
import com.MyApp.budgetControl.domain.user.UserEntity;
import lombok.Value;

import java.util.List;

@Value
public class UserResponseDTO {

  public UserResponseDTO(UserEntity userEntity) {
    this.userId = userEntity.getUserId();
    this.userName = userEntity.getUserName();
    this.userEmail = userEntity.getUserEmail();
    this.userExpenses = userEntity.getUserExpenses().stream().map(ExpenseResponseDTO::new).toList();
  }

  private final String userId;
  private final String userName;
  private final String userEmail;
  private final List<ExpenseResponseDTO> userExpenses;

}
