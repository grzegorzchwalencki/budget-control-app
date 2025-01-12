package com.MyApp.budgetControl.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

  UserEntity save(UserEntity newUser);

  List<UserEntity> findAll();

  Optional<UserEntity> findById(String userId);

  void deleteById(String userId);

}
