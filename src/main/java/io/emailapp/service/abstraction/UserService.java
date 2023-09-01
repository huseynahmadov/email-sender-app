package io.emailapp.service.abstraction;

import io.emailapp.dao.entity.UserEntity;

public interface UserService {

    UserEntity saveUser(UserEntity user);

    Boolean verifyToken(String token);

}
