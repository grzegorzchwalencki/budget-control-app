package com.MyApp.budgetControl.domain.user;

import java.util.List;
import java.util.Optional;

interface UserRepository {

  UserEntity save(UserEntity newUser);

  List<UserEntity> findAll();

  Optional<UserEntity> findByUserId(String userId);

  void deleteByUserId(String userId);

}
