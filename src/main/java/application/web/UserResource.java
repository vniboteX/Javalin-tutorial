package application.web;

import application.dto.UserInfoDto;
import domain.UserInfo;
import domain.service.UserService;
import io.javalin.http.Context;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserResource {

  private final UserService userService;

  public void createUser(Context context) {
    UserInfoDto userInfoDto = context.bodyValidator(UserInfoDto.class)
      .check(body -> Objects.nonNull(body.getEmail()), "email is mandatory")
      .check(body -> Objects.nonNull(body.getLastname()), "lastName is mandatory")
      .get();

    userService.createUser(toUserInfo(userInfoDto));
    System.out.printf("User %s created", userInfoDto.getLastname());
    System.out.println();
  }

  public void getAllUsers(Context context) {
    List<UserInfo> users = userService.getAllUsers();
    context.json(users.stream().map(this::toUserDto).collect(Collectors.toList()));
  }

  public void updateUser(Context context) {
    int userId = Integer.parseInt(context.pathParam("id"));
    UserInfoDto userInfoDto = context.bodyValidator(UserInfoDto.class)
      .check(body -> Objects.nonNull(body.getEmail()), "email is mandatory")
      .check(body -> Objects.nonNull(body.getLastname()), "lastName is mandatory")
      .get();
    userService.updateUser(userId, toUserInfo(userInfoDto));
    context.status(204);
  }

  public void deleteUser(Context context) {
    int userId = Integer.parseInt(context.pathParam("id"));
    userService.deleteUser(userId);
    context.status(200);
  }

  private UserInfoDto toUserDto(UserInfo info) {
    UserInfoDto dto = new UserInfoDto();
    dto.setLastname(info.getLastname());
    dto.setFirstname(info.getFirstname());
    dto.setEmail(info.getEmail());
    dto.setAddress(info.getAddress());
    return dto;
  }

  private UserInfo toUserInfo(UserInfoDto userInfoDto) {
    return UserInfo.builder()
      .firstname(userInfoDto.getFirstname())
      .lastname(userInfoDto.getLastname())
      .address(userInfoDto.getAddress())
      .email(userInfoDto.getEmail())
      .build();
  }
}
