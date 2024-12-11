package com.MyApp.budgetControl.domain.user;

import lombok.Value;

@Value
public class UserResponseDTO {

  public UserResponseDTO(UserEntity userEntity) {
    this.userId = userEntity.getUserId();
    this.userName = userEntity.getUserName();
    this.userEmail = userEntity.getUserEmail();
  }

  private final String userId;
  private final String userName;
  private final String userEmail;

}
