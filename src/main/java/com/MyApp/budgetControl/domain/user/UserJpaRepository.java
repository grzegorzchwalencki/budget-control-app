package com.MyApp.budgetControl.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface UserJpaRepository extends UserRepository, JpaRepository<UserEntity, String> {

  UserEntity save(UserEntity newUser);

  List<UserEntity> findAll();

  Optional<UserEntity> findById(String userId);

  void deleteById(String userId);

}
