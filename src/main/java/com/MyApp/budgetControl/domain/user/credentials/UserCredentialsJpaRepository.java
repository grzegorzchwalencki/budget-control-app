package com.MyApp.budgetControl.domain.user.credentials;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserCredentialsJpaRepository extends UserCredentialsRepository, JpaRepository<UserCredentials, UUID> {
}
