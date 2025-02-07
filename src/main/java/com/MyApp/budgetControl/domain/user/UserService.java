package com.MyApp.budgetControl.domain.user;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public void saveUser(UserRequestDTO userRequestDTO) {
    UserEntity newUser = new UserEntity(userRequestDTO);
    userRepository.save(newUser);
  }

  public List<UserResponseDTO> findAllUsers() {
    return userRepository.findAll().stream().map(UserResponseDTO::new).toList();
  }

  public UserEntity findUserById(String userId) {
    return userRepository.findById(userId).get();
  }

  @Transactional
  public void deleteUserById(String userId) {
    UserEntity user = userRepository.findById(userId).get();
    userRepository.deleteById(userId);
  }

}
