package com.MyApp.budgetControl.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
    @NotBlank(message = "Username is mandatory")
    @Size(max = 64, message = "Username max length is 64 char")
    @JsonProperty("userName")
    String userName,

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email address is mandatory")
    @Size(max = 64, message = "Email address max length is 64 char")
    @JsonProperty("userEmail")
    String userEmail) {
}
