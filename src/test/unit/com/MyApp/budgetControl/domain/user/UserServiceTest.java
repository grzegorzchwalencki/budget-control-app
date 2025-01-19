package com.MyApp.budgetControl.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  UserRepository mockRepository;
  @InjectMocks
  UserService subject;
  static List<UserEntity> users;

  @BeforeAll
  static void setup() {
    users = Arrays.asList(
        new UserEntity(new UserRequestDTO("testUsername1", "testEmail1@test.com")),
        new UserEntity(new UserRequestDTO("testUsername2", "testEmail2@test.com")));
  }

  @Test
  void getUsersShouldReturnAllUsers() {
    when(mockRepository.findAll()).thenReturn(users);
    List<UserResponseDTO> users = subject.findAllUsers();
    String expectedUserName = "testUsername2";
    String expectedUserEmail = "testEmail1@test.com";
    int expectedNumberOfUsers = 2;
    assertTrue(users.stream().anyMatch(x -> x.getUserName().equals(expectedUserName)));
    assertTrue(users.stream().anyMatch(x -> x.getUserEmail().equals(expectedUserEmail)));
    assertEquals(expectedNumberOfUsers, users.size());
  }

  @Test
  void getUserByIdForExistingIdIsReturningCorrectUser() {
    when(mockRepository.findAll()).thenReturn(users);
    List<UserResponseDTO> users = subject.findAllUsers();
    String userId = users.get(0).getUserId();
    when(mockRepository.findById(userId)).thenReturn(Optional.of(
        new UserEntity(userId, "testUsername1", "testEmail1@test.com", new ArrayList<>())));
    UserEntity result = subject.findUserById(userId);
    String expectedUserName = "testUsername1";
    assertEquals(result.getUserName(), expectedUserName);
  }

  @Test
  void getUserByIdForNotExistingIdReturnNotFoundError() {
    String randomUUID = UUID.randomUUID().toString();
    NoSuchElementException exception =
        assertThrows(NoSuchElementException.class, () -> subject.findUserById(randomUUID));
    assertEquals("No value present", exception.getMessage());
  }
}
