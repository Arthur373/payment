package ru.aeon.payment.config;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import ru.aeon.payment.entity.JwtBlackList;
import ru.aeon.payment.services.JwtBlackListService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom class for logout
 *
 * @author Arthur
 * @version 1
 */
@NoArgsConstructor
@Configuration
public class CustomLogoutHandler implements LogoutSuccessHandler {

    private JwtBlackListService jwtBlackListService;

    @Autowired
    public CustomLogoutHandler(JwtBlackListService jwtBlackListService) {
        this.jwtBlackListService = jwtBlackListService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        String tokenHeader = request.getHeader("Authorization");
        String jwtToken;
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            jwtToken = tokenHeader.substring(7);
            JwtBlackList jwtBlacklist = new JwtBlackList();
            jwtBlacklist.setToken(jwtToken);
            if (jwtBlackListService.getByToken(jwtToken) == null) {
                jwtBlackListService.saveToken(jwtBlacklist);
            }
        }
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, authentication);

    }
}
