package com.MyApp.budgetControl.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class UserRequestDTO {

  @NotBlank(message = "Username is mandatory")
  @Size(max = 64, message = "Username max length is 64 char")
  @JsonProperty("userName")
  private final String userName;

  @Email(message = "Please provide a valid email address")
  @NotBlank(message = "Email address is mandatory")
  @Size(max = 64, message = "Email address max length is 64 char")
  @JsonProperty("userEmail")
  private final String userEmail;

}
