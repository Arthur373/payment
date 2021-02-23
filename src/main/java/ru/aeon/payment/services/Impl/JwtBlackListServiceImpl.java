package ru.aeon.payment.services.Impl;

import org.springframework.stereotype.Service;
import ru.aeon.payment.entity.JwtBlackList;
import ru.aeon.payment.repositories.JwtBlackListRepository;
import ru.aeon.payment.services.JwtBlackListService;

/**
 * Implementation of {@link JwtBlackListService} interface.
 *
 * @author Arthur
 * @version 1.0
 */
@Service
public class JwtBlackListServiceImpl implements JwtBlackListService {

    private final JwtBlackListRepository jwtBlackListRepository;

    public JwtBlackListServiceImpl(JwtBlackListRepository jwtBlackListRepository) {
        this.jwtBlackListRepository = jwtBlackListRepository;
    }

    @Override
    public JwtBlackList getByToken(String token) {
        return this.jwtBlackListRepository.findJwtBlacklistByToken(token);
    }

    @Override
    public JwtBlackList saveToken(JwtBlackList jwtBlacklist) {
        return this.jwtBlackListRepository.save(jwtBlacklist);
    }
}
