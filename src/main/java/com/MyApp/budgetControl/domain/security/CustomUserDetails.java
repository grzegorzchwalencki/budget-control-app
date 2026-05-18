package com.MyApp.budgetControl.domain.security;

import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


@Getter
public class CustomUserDetails extends User {

  final String userId;
  final String userEmail;

  public CustomUserDetails(String userId, String username, String password, String userEmail,
                           Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.userId = userId;
    this.userEmail = userEmail;
  }

}
