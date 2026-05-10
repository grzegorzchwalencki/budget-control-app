package com.MyApp.budgetControl.domain.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserJpaRepository extends UserRepository, JpaRepository<UserEntity, UUID> {

  UserEntity save(UserEntity newUser);

  List<UserEntity> findAll();

  Optional<UserEntity> findById(UUID userId);

  void deleteById(UUID userId);

}
