package com.MyApp.budgetControl.domain.security.services;

import com.MyApp.budgetControl.domain.security.CustomUserDetails;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
  private final JwtEncoder jwtEncoder;
  private final JwtDecoder jwtDecoder;

  @Value("${jwt.expiration}")
  private long jwtExpirationSeconds;

  public String generateToken(CustomUserDetails userDetails) {

    Instant now = Instant.now();
    Instant expiry = now.plusSeconds(jwtExpirationSeconds);

    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .toList();

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("budget-controll-app")
        .issuedAt(now)
        .expiresAt(expiry)
        .subject(userDetails.getUserId())
        .claim("roles", roles)
        .claim("email", userDetails.getUserEmail())
        .build();

    JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

    return jwtEncoder.encode(JwtEncoderParameters.from(header, claims))
        .getTokenValue();
  }

  public String extractUserId(String token) {
    Jwt jwt = jwtDecoder.decode(token);
    return jwt.getSubject();
  }

  public String extractRole(String token) {
    Jwt jwt = jwtDecoder.decode(token);
    return jwt.getSubject();
  }

  public boolean isTokenValid(String token, CustomUserDetails userDetails) {
    try {
      Jwt jwt = jwtDecoder.decode(token);

      if (jwt.getExpiresAt() == null || jwt.getExpiresAt().isBefore(Instant.now())) {
        return false;
      }

      String userId = jwt.getSubject();
      return userId.equals(userDetails.getUserId());

    } catch (JwtException e) {
      return false;
    }
  }

}

