package domain.service;

import domain.UserInfo;
import domain.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;

  public void createUser(UserInfo userInfo) {
    repository.createUser(userInfo);
  }

  public List<UserInfo> getAllUsers() {
    return repository.getAllUsers();
  }

  public void updateUser(int userId, UserInfo userInfo) {
    repository.updateUser(userId, userInfo);
  }

  public void deleteUser(int userId) {
    repository.deleteUser(userId);
  }
}
