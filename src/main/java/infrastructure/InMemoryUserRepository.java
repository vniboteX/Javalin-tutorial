package infrastructure;

import domain.UserInfo;
import domain.UserRepository;
import java.util.ArrayList;
import java.util.List;

public class InMemoryUserRepository implements UserRepository {

  private final List<UserInfo> users;

  public InMemoryUserRepository() {
    users = new ArrayList<>();
  }

  @Override
  public void createUser(UserInfo user) {
    user.setId(users.size());
    users.add(user);
  }

  @Override
  public List<UserInfo> getAllUsers() {
    return users;
  }

  @Override
  public void updateUser(int userId, UserInfo userInfo) {
    users.set(userId, userInfo);
  }

  @Override
  public void deleteUser(int userId) {
    users.remove(userId);
  }
}
