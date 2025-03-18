package com.MyApp.budgetControl.api.controller;

import com.MyApp.budgetControl.domain.ServicesOrchestrator;
import com.MyApp.budgetControl.domain.user.dto.UserRequestDTO;
import com.MyApp.budgetControl.domain.user.dto.UserResponseDTO;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final ServicesOrchestrator servicesOrchestrator;

  @GetMapping
  public List<UserResponseDTO> getUsers() {
    return servicesOrchestrator.findAllUsers();
  }

  @GetMapping("/{userId}")
  public UserResponseDTO getUserById(@PathVariable String userId) {
    return servicesOrchestrator.findUserById(userId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void addNewUser(@Valid @RequestBody UserRequestDTO newUser) {
    servicesOrchestrator.saveUser(newUser);
  }

  @DeleteMapping("/{userId}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void deleteUser(@PathVariable String userId) {
    servicesOrchestrator.deleteUserById(userId);
  }

}