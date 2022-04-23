package application.security;

import io.javalin.core.security.RouteRole;

public enum Role implements RouteRole {
  ADMIN,
  USER
}
