package ru.aeon.payment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aeon.payment.entity.OrderEntity;

import java.math.BigDecimal;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {

    OrderEntity findOrderEntityByBought(BigDecimal bought);
}
