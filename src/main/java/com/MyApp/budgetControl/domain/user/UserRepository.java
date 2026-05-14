package com.MyApp.budgetControl.domain.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface UserRepository {

  UserEntity save(UserEntity newUser);

  List<UserEntity> findAll();

  Optional<UserEntity> findById(UUID userId);

  void deleteById(UUID userId);

}
