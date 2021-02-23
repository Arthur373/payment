package ru.aeon.payment.services.Impl;

import org.hibernate.HibernateException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.aeon.payment.entity.UserEntity;
import ru.aeon.payment.repositories.UserRepository;
import ru.aeon.payment.services.UserService;

import java.util.Optional;

/**
 * Implementation of {@link UserService} interface.
 *
 * @author Arthur
 * @version 1.0
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        return this.userRepository.findUserEntityByEmail(email);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public UserEntity saveUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return this.userRepository.save(userEntity);
    }
}
