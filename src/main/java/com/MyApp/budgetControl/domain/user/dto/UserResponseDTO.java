package com.MyApp.budgetControl.domain.user.dto;

import com.MyApp.budgetControl.domain.expense.dto.ExpenseResponseDTO;
import com.MyApp.budgetControl.domain.user.UserEntity;
import java.util.List;
import java.util.UUID;

public record UserResponseDTO(
    UUID userId,
    String userName,
    String userEmail,
    List<ExpenseResponseDTO> userExpenses
) {

  public UserResponseDTO(UserEntity entity) {
    this(
        entity.getUserId(),
        entity.getUserName(),
        entity.getUserEmail(),
        entity.getUserExpenses().stream()
            .map(ExpenseResponseDTO::new)
            .toList()
    );
  }
}
