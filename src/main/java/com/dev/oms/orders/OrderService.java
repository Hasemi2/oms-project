package com.dev.oms.orders;

import com.dev.oms.errors.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Transactional(readOnly = true)
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAll(long offset,  int size) {
        return orderRepository.findAll(offset, size);
    }

    public Optional<Order> findById(Long id) {
        checkNotNull(id, "orderId must be provided");
        return orderRepository.findById(id);
    }

    @Transactional
    public boolean accept(Long id) {
        checkNotNull(id, "orderId must be provided");
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("could not found for order " + id));

        if (!order.getState().equals(OrderState.REQUESTED)) return false;

        order.setStatus(OrderState.ACCEPTED);
        orderRepository.update(order);
        return order.getState() == OrderState.ACCEPTED;
    }

    @Transactional
    public boolean reject(Long id, OrderRejectRequest request) {
        checkNotNull(id, "orderId must be provided");

        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("could not found for order " + id));

        if (!order.getState().equals(OrderState.REQUESTED)) return false;

        order.rejectProcess(request.getMessage());
        orderRepository.update(order);
        return order.getState() == OrderState.REJECTED;
    }

    @Transactional
    public boolean shipping(Long id) {
        checkNotNull(id, "orderId must be provided");
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("could not found for order " + id));

        if (!order.getState().equals(OrderState.ACCEPTED)) return false;

        order.setStatus(OrderState.SHIPPING);
        orderRepository.update(order);
        return order.getState() == OrderState.SHIPPING;
    }

    @Transactional
    public boolean complete(Long id) {
        checkNotNull(id, "orderId must be provided");
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("could not found for order " + id));

        if (!order.getState().equals(OrderState.SHIPPING)) return false;


        order.setCompleteProcess();
        orderRepository.update(order);
        return order.getState() == OrderState.COMPLETED;
    }
}
