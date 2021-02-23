package ru.aeon.payment.services.Impl;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.aeon.payment.entity.OrderEntity;
import ru.aeon.payment.repositories.OrderRepository;
import ru.aeon.payment.services.OrderService;

import java.math.BigDecimal;

/**
 * Implementation of {@link OrderService} interface.
 *
 * @author Arthur
 * @version 1.0
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderEntity getOrderByBought(BigDecimal bought) {
        return this.orderRepository.findOrderEntityByBought(bought);
    }

    @Override
    @Transactional(rollbackFor = HibernateException.class)
    public OrderEntity saveOrder(OrderEntity orderEntity) {
        return orderRepository.save(orderEntity);
    }
}
