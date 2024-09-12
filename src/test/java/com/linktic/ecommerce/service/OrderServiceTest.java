package com.linktic.ecommerce.service;

import com.linktic.ecommerce.model.Order;
import com.linktic.ecommerce.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class OrderServiceTest {

    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        orderService = new OrderService(orderRepository);
    }

    @Test
    void testGetAllOrders() {
        Order order1 = new Order();
        order1.setId(1L);
        order1.setCustomerEmail("customer1@example.com");
        order1.setOrderDate(LocalDateTime.now());
        order1.setTotal(1500.00);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setCustomerEmail("customer2@example.com");
        order2.setOrderDate(LocalDateTime.now());
        order2.setTotal(2500.00);

        List<Order> allOrders = Arrays.asList(order1, order2);

        given(orderRepository.findAll()).willReturn(allOrders);

        List<Order> result = orderService.getAllOrders();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCustomerEmail()).isEqualTo("customer1@example.com");
        assertThat(result.get(1).getCustomerEmail()).isEqualTo("customer2@example.com");
    }

    @Test
    void testUpdateOrder() {
        Order existingOrder = new Order();
        existingOrder.setId(1L);
        existingOrder.setCustomerEmail("customer1@example.com");
        existingOrder.setOrderDate(LocalDateTime.now());
        existingOrder.setTotal(1500.00);

        Order updatedInfo = new Order();
        updatedInfo.setTotal(2000.00); // Update the total amount

        given(orderRepository.findById(1L)).willReturn(Optional.of(existingOrder));
        given(orderRepository.save(existingOrder)).willReturn(existingOrder);

        Order result = orderService.updateOrder(1L, updatedInfo);

        assertThat(result.getTotal()).isEqualTo(2000.00);
    }

    @Test
    void testDeleteOrder() {
        Long orderId = 1L;

        willDoNothing().given(orderRepository).deleteById(orderId);

        orderService.deleteOrder(orderId);

        then(orderRepository).should().deleteById(orderId);
    }
}
