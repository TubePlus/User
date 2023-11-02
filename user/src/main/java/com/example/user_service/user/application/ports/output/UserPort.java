package com.example.user_service.user.application.ports.output;

import com.example.user_service.user.domain.User;

public interface UserPort {

    User logInUser(User user);
    User signUpUser(User user);
    User comeBackUser(User user);
    User changeUsername(User user);
    Boolean checkCreator(User user);
    User registerCreator(User user);
    User changeCreatorCategory(User user);
    User deleteCreator(User user);
    User getUserInfo(User user);
    User softDeleteUser(User user);
    User toggleDarkMode(User user);
    User autoSearchCreators(User user);
    void checkDuplicateUsername(String username);
}
