package com.MyApp.budgetControl.api.controller;

import com.MyApp.budgetControl.domain.ServicesOrchestrator;
import com.MyApp.budgetControl.domain.user.dto.UserRequestDTO;
import com.MyApp.budgetControl.domain.user.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "User")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final ServicesOrchestrator servicesOrchestrator;

  @Operation(summary = "Get the list of users")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found an users"),
      @ApiResponse(responseCode = "404", description = "Users not found")})
  @GetMapping
  public List<UserResponseDTO> getUsers() {
    return servicesOrchestrator.findAllUsers();
  }

  @Operation(summary = "Get the user by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the user"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "404", description = "User not found")})
  @GetMapping("/{userId}")
  public UserResponseDTO getUserById(@PathVariable String userId) {
    return servicesOrchestrator.findUserById(userId);
  }

  @Operation(summary = "Create new user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created the user")})
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void addNewUser(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "User to create", required = true,
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserRequestDTO.class),
              examples = @ExampleObject(value = "{ \"userName\": \"exampleUser\", \"userEmail\": \"example@email.com\" }")))
      @Valid @RequestBody UserRequestDTO newUser) {
    servicesOrchestrator.saveUser(newUser);
  }

  @Operation(summary = "Delete the user by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Deleted the user"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "404", description = "User not found")})
  @DeleteMapping("/{userId}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void deleteUser(@PathVariable String userId) {
    servicesOrchestrator.deleteUserById(userId);
  }

}