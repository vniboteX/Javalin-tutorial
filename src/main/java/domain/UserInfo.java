package domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {

  private int id;
  private final String firstname;
  private final String lastname;
  private final String email;
  private final String address;
}
