package com.MyApp.budgetControl.domain.security;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityConfig {


  @Bean
  public PasswordEncoder passwordEncoder() {

    Map<String, PasswordEncoder> encoders = new HashMap<>();

    encoders.put("argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());

    DelegatingPasswordEncoder delegatingPasswordEncoder =
        new DelegatingPasswordEncoder("argon2", encoders);

    return delegatingPasswordEncoder;
  }


}
