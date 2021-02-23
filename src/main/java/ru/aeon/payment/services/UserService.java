package ru.aeon.payment.services;

import ru.aeon.payment.entity.UserEntity;

import java.util.Optional;

/**
 * Service class for {@link UserEntity}
 *
 * @author Arthur
 * @version 1.0
 */
public interface UserService {

    Optional<UserEntity> getUserByEmail(String email);

    UserEntity saveUser(UserEntity userEntity);

}
