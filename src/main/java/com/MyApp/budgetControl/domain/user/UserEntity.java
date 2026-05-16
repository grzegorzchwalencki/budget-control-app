package com.MyApp.budgetControl.domain.user;

import com.MyApp.budgetControl.domain.expense.ExpenseEntity;
import com.MyApp.budgetControl.domain.security.RoleType;
import com.MyApp.budgetControl.domain.user.dto.UserRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Value
@Entity
@Table(name = "users")
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

  UserEntity(UserRequestDTO userRequestDTO) {
    this.userId = UUID.randomUUID();
    this.userName = userRequestDTO.userName();
    this.password = "password";
    this.role = RoleType.USER;
    this.userEmail = userRequestDTO.userEmail();
    this.userExpenses = Collections.emptyList();
  }

  @Id
  UUID userId;

  @NotBlank(message = "Username is mandatory")
  @Size(max = 64, message = "Username max length is 64 char")
  @Column(nullable = false, unique = true)
  String userName;

  @Column(nullable = false)
  String password;

  @Enumerated(EnumType.STRING)
  RoleType role;

  @Email
  @NotBlank(message = "Email address is mandatory")
  @Size(max = 64, message = "Email address max length is 64 char")
  @Column(nullable = false, unique = true)
  String userEmail;

  @OneToMany(mappedBy = "userId")
  @JdbcTypeCode(SqlTypes.JSON)
  List<ExpenseEntity> userExpenses;

}
