package com.dev.oms.orders;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(long id);

    List<Order> findAll(long offset, int size);

    void update(Long reviewId, Long orderId);

    void update(Order order);
}
