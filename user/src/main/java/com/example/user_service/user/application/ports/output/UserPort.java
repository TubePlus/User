package com.example.user_service.user.application.ports.output;

import com.example.user_service.user.domain.User;

public interface UserPort {

    User logInUser(User user);
    User signUpUser(User user);
    Boolean checkDuplicateUsername(String username);
    Boolean checkDuplicateGoogleAuth(String googleAuth);
    User getUserInfo(String uuid);
}
