package com.MyApp.budgetControl.domain.security;

import com.MyApp.budgetControl.domain.user.UserEntity;
import com.MyApp.budgetControl.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByUserName(userName)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with userName: " + userName));

    return org.springframework.security.core.userdetails.User
        .builder()
        .username(user.getUserName())
        .password(user.getPassword())
        .roles(user.getRole().name())
        .build();
  }
}
