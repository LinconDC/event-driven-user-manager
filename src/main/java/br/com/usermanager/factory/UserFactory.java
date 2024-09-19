package br.com.usermanager.factory;

import br.com.usermanager.model.Login;
import br.com.usermanager.model.Secret;
import br.com.usermanager.model.User;
import br.com.usermanager.model.request.UserRequest;
import br.com.usermanager.model.response.UserResponse;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class UserFactory {

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.userId(),
                user.name(),
                user.email()
        );
    }

    public static User toUser(UserRequest userRequest) {
        List<Login> logins = Collections.emptyList();
        Secret secret = new Secret(userRequest.userPassword());

        return new User(
                null,
                UUID.randomUUID().toString(),
                userRequest.userName(),
                userRequest.userEmail(),
                secret,
                LocalDate.now(),
                logins,
                "INACTIVE",
                ""
        );
    }
}
