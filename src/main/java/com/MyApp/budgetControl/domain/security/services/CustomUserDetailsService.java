package com.MyApp.budgetControl.domain.security.services;

import com.MyApp.budgetControl.domain.security.CustomUserDetails;
import com.MyApp.budgetControl.domain.user.UserEntity;
import com.MyApp.budgetControl.domain.user.UserRepository;
import com.MyApp.budgetControl.domain.user.credentials.RoleType;
import com.MyApp.budgetControl.domain.user.credentials.UserCredentials;
import com.MyApp.budgetControl.domain.user.credentials.UserCredentialsRepository;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;
  private final UserCredentialsRepository userCredentialsRepository;

  @Override
  public UserDetails loadUserByUsername(String userName) {
    UserEntity user = userRepository.findByUserName(userName)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with userName: " + userName));
    UserCredentials userCredentials = userCredentialsRepository.findByUserId(user.getUserId())
        .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Credentials not found for userId: " + user.getUserId()));


    return new CustomUserDetails(
        String.valueOf(user.getUserId()),
        user.getUserName(),
        userCredentials.getPasswordHash(),
        user.getUserEmail(),
        getAuthorities(userCredentials)
    );
  }

  private Collection<? extends GrantedAuthority> getAuthorities(UserCredentials user) {

    if (user.getRole() == null) {
      return List.of(new SimpleGrantedAuthority(String.valueOf(RoleType.USER)));
    }

    return List.of(new SimpleGrantedAuthority(String.valueOf(user.getRole())));
  }

}
