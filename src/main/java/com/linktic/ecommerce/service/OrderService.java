package com.linktic.ecommerce.service;

import com.linktic.ecommerce.model.Order;
import com.linktic.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setItems(updatedOrder.getItems());
                    order.setTotal(updatedOrder.getTotal());
                    return orderRepository.save(order);
                }).orElseGet(() -> {
                    updatedOrder.setId(id);
                    return orderRepository.save(updatedOrder);
                });
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
