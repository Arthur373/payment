package ru.aeon.payment.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.aeon.payment.entity.UserEntity;
import ru.aeon.payment.security.LoginAttemptService;
import ru.aeon.payment.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Implementation of {@link UserDetailsService} interface.
 *
 * @author Arthur
 * @version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final HttpServletRequest request;
    private final LoginAttemptService loginAttemptService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService, HttpServletRequest request,
                                  LoginAttemptService loginAttemptService) {
        this.userService = userService;
        this.request = request;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("blocked");
        }
        UserEntity userEntity = userService.getUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));
        return new User(userEntity.getEmail(), userEntity.getPassword(), new ArrayList<>());
    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
