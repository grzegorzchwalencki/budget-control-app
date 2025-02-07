package com.MyApp.budgetControl.domain.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserJpaRepository extends UserRepository, JpaRepository<UserEntity, String> {

  UserEntity save(UserEntity newUser);

  List<UserEntity> findAll();

  Optional<UserEntity> findById(String userId);

  void deleteById(String userId);

}
