package ru.aeon.payment.services;


import ru.aeon.payment.entity.OrderEntity;

import java.math.BigDecimal;

/**
 * Service class for {@link ru.aeon.payment.entity.OrderEntity}
 *
 * @author Arthur
 * @version 1.0
 */
public interface OrderService {

    OrderEntity getOrderByBought(BigDecimal bought);

    OrderEntity saveOrder(OrderEntity orderEntity);
}
