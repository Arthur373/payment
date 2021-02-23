package ru.aeon.payment.services;

import ru.aeon.payment.entity.JwtBlackList;

/**
 * Service class for {@link ru.aeon.payment.entity.JwtBlackList}
 *
 * @author Arthur
 * @version 1.0
 */
public interface JwtBlackListService {

    JwtBlackList getByToken(String token);

    JwtBlackList saveToken(JwtBlackList jwtBlacklist);
}
