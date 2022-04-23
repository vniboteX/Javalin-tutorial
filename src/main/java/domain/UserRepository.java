package domain;

import java.util.List;

public interface UserRepository {

  void createUser(UserInfo user);

  List<UserInfo> getAllUsers();

  void updateUser(int userId, UserInfo userInfo);

  void deleteUser(int userId);
}
