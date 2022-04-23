package application.security;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {

  private String sub;
  private String name;
  private Instant issueDate;
  private Instant expDate;
  private String issuer;
  private Role role;

}
