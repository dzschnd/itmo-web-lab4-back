package web.rest.http.resources;

import lombok.Value;
import web.rest.model.Role;

@Value
public class UserResource {
    Long id;
    String username;
    Role role;
}
