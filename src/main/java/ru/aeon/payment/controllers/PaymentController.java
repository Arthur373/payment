package ru.aeon.payment.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aeon.payment.entity.OrderEntity;
import ru.aeon.payment.entity.UserEntity;
import ru.aeon.payment.security.JwtTokenProvider;
import ru.aeon.payment.services.OrderService;
import ru.aeon.payment.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * Rest Controller for users payments.
 *
 * @author Arthur
 * @version 1.0
 */
@RestController
public class PaymentController {

    private final OrderService orderService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    @Value("${jwt.header}")
    private String authorizationHeader;

    public PaymentController(OrderService orderService, UserService userService,
                             JwtTokenProvider jwtTokenProvider) {
        this.orderService = orderService;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/payment")
    public ResponseEntity<?> payment(HttpServletRequest request) {
        String tokenHeader = request.getHeader(authorizationHeader);
        String jwtToken = null;
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            jwtToken = tokenHeader.substring(7);
        }
        try {
            // Get User from token
            UserEntity userEntity = this.userService.getUserByEmail(this.jwtTokenProvider.getUsernameFromToken(jwtToken)).orElseThrow(
                    () -> new UsernameNotFoundException("User not found "));
            if (userEntity.getBalance().doubleValue() > 1.1) {
                userEntity.setBalance(userEntity.getBalance().subtract(BigDecimal.valueOf(1.1)));
                this.userService.saveUser(userEntity);

                OrderEntity orderEntity = new OrderEntity(userEntity);
                orderEntity.setBought(BigDecimal.valueOf(1.1));
                this.orderService.saveOrder(orderEntity);
            } else {
                return new ResponseEntity<>("insufficient funds in the account", HttpStatus.OK);
            }

            return new ResponseEntity<>("payment was successful", HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("JWT token is expired or invalid", HttpStatus.FORBIDDEN);
        }
    }
}
