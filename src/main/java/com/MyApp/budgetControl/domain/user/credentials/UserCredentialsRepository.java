package com.MyApp.budgetControl.domain.user.credentials;

import java.util.Optional;
import java.util.UUID;

public interface UserCredentialsRepository {

  UserCredentials save(UserCredentials newUserCredentials);

  Optional<UserCredentials> findByUserId(UUID id);
}
