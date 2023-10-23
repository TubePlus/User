package com.example.user_service.user.application.ports.output;

import com.example.user_service.user.domain.User;

public interface UserPort {

    User logInUser(User user);
    User signUpUser(User user);
    User changeUsername(User user);
    User getUserInfo(String uuid);
    Boolean checkDuplicateUsername(String username);
}
