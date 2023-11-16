package com.example.user_service.user.application.ports.output;

import com.example.user_service.user.application.ports.output.dto.AutoSearchCreatorsDto;
import com.example.user_service.user.domain.User;

import java.util.List;

public interface UserPort {

    User logInUser(User user);
    User signUpUser(User user);
    User comeBackUser(User user);
    User changeUsername(User user); // todo: User로 넘겨주지 말고 username과 uuid만 넘겨주기(파라미터로 바로 넘겨주기)
    Boolean checkCreator(User user);
    User registerCreator(User user);
    User changeCreatorCategory(User user);
    User deleteCreator(User user);
    User getUserInfo(User user);
    User updateUserInfo(User user);
    User softDeleteUser(User user);
    User toggleDarkMode(User user);
    List<AutoSearchCreatorsDto> autoSearchCreators(User user);
    void checkDuplicateUsername(String username);
}
