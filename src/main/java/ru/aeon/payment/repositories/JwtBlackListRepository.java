package ru.aeon.payment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aeon.payment.entity.JwtBlackList;

@Repository
public interface JwtBlackListRepository extends JpaRepository<JwtBlackList,Long> {

    JwtBlackList findJwtBlacklistByToken(String token);
}
