package com.MyApp.budgetControl.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Value
@Entity
@RequiredArgsConstructor
@Table(name = "app_user")
@NoArgsConstructor(force = true)
@EntityListeners(AuditingEntityListener.class)
class UserEntity {

  UserEntity(UserRequestDTO userRequestDTO) {
    this.userId = UUID.randomUUID().toString();
    this.userName = userRequestDTO.getUserName();
    this.userEmail = userRequestDTO.getUserEmail();
  }

  @Id
  private final String userId;

  @NotBlank(message = "Username is mandatory")
  @Size(max = 64, message = "Username max length is 64 char")
  @Column(unique = true)
  private final String userName;

  @Email
  @NotBlank(message = "Email address is mandatory")
  @Size(max = 64, message = "Email address max length is 64 char")
  private final String userEmail;

}
