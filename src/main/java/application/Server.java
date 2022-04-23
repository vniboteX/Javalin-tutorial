package application;

import static application.security.Role.ADMIN;
import static application.security.Role.USER;

import application.security.JwtToken;
import application.security.Role;
import application.web.UserResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.UserRepository;
import domain.service.UserService;
import infrastructure.InMemoryUserRepository;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.Base64;
import java.util.Optional;
import javax.swing.text.html.Option;

public class Server {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static void main(String[] args) {
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    Javalin app = Javalin.create(config -> config.accessManager(((handler, ctx, routeRoles) -> {
      Role userRole = getUserRole(ctx);
      if (routeRoles.contains(userRole)) {
        handler.handle(ctx);
      } else {
        ctx.status(401).result("Unauthorized");
      }
    }))).start(7070);

    UserRepository repository = new InMemoryUserRepository();
    UserService service = new UserService(repository);
    UserResource resource = new UserResource(service);

    app.before(ctx -> System.out.println("Incoming request"));
    app.after(ctx -> System.out.println("Sending response"));

    app.post("/user", resource::createUser, USER, ADMIN);
    app.get("/users", resource::getAllUsers, USER, ADMIN);
    app.put("/user/{id}", resource::updateUser, USER, ADMIN);
    app.delete("/user/{id}", resource::deleteUser, ADMIN);
  }

  private static Role getUserRole(Context ctx) throws JsonProcessingException {
    Optional<JwtToken> optionalJwtToken = getToken(ctx);
    return optionalJwtToken.map(JwtToken::getRole).orElse(null);

  }

  private static Optional<JwtToken> getToken(Context ctx) throws JsonProcessingException {
    String authorization = ctx.header("Authorization");
    if (authorization == null || authorization.isEmpty()) {
      return Optional.empty();
    }
    String[] chunks = authorization.split("\\.");
    Base64.Decoder decoder = Base64.getUrlDecoder();
    String payload = new String(decoder.decode(chunks[1]));
    return Optional.of(objectMapper.readValue(payload, JwtToken.class));

  }
}
