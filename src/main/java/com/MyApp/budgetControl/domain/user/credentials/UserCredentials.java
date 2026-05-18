package com.MyApp.budgetControl.domain.user.credentials;

import com.MyApp.budgetControl.domain.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Entity
@Table(name = "users_credentials")
@NoArgsConstructor(force = true)
public class UserCredentials {

  @Id
  UUID id;

  @OneToOne(mappedBy = "id")
  @JoinColumn(name = "userId")
  UserEntity user;

  @Column(nullable = false)
  String passwordHash;

  @Enumerated(EnumType.STRING)
  RoleType role;


}
