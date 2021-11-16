package com.classpath.ordersapi.service;

import com.classpath.ordersapi.model.Order;
import com.classpath.ordersapi.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testCreateOrder(){
        Order order = Order
                        .builder()
                            .name("naveen")
                            .emailAddress("naveen@gmail.com")
                            .orderDate(LocalDate.now())
                            .orderPrice(25000)
                        .build();
        //set an expectation on the mock object
        order.setOrderId(12L);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        //execute the method in test
        Order savedOrder = orderService.saveOrder(order);

        //assert the behavior
        assertNotNull(savedOrder);
        assertEquals(savedOrder.getEmailAddress() , "naveen@gmail.com");
        assertEquals(savedOrder.getName() , "naveen");
        assertEquals(savedOrder.getOrderDate() , LocalDate.now());
        assertEquals(savedOrder.getOrderPrice(), 25_000);

        //verify the expectations set on the mock object
        Mockito.verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testFetchOrders(){
        //set the expectations
        when(orderRepository.findAll()).thenReturn(Arrays.asList(
                Order.builder().orderId(1).orderPrice(10000).orderDate(LocalDate.now()).emailAddress("harish@gmail.com").name("harish").build(),
                Order.builder().orderId(2).orderPrice(20000).orderDate(LocalDate.now()).emailAddress("suresh@gmail.com").name("suresh").build(),
                Order.builder().orderId(3).orderPrice(30000).orderDate(LocalDate.now()).emailAddress("naveen@gmail.com").name("naveen").build(),
                Order.builder().orderId(4).orderPrice(40000).orderDate(LocalDate.now()).emailAddress("priya@gmail.com").name("priya").build()
        ));
        //execute
        Set<Order> orders = new HashSet<>();

        //assert the behavior
        assertNotNull(orders);
        assertTrue(orders.size() == 4);

        //verify
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void testFetchOrderByOrderId(){
        //set the expectations
        Order order = Order.builder().orderId(12L).orderPrice(40000).orderDate(LocalDate.now()).emailAddress("priya@gmail.com").name("priya").build();
        when(orderRepository.findById(12L)).thenReturn(Optional.of(order));

        //execute
        //order?.getId();
        Order fetchedOrder = this.orderService.findById(12L);
        //assert the behavior
        assertNotNull(fetchedOrder);
        //verify
        verify(orderRepository, times(1)).findById(12L);
    }

    @Test
    public void testFetchOrderByInvalidOrderId(){
        //set the expectations
        Order order = Order.builder().orderId(12L).orderPrice(40000).orderDate(LocalDate.now()).emailAddress("priya@gmail.com").name("priya").build();
        when(orderRepository.findById(12L)).thenReturn(Optional.empty());

        //execute
        assertThrows(IllegalArgumentException.class, () -> this.orderService.findById(12L));

        //verify
        verify(orderRepository, times(1)).findById(12L);
    }

    @Test
    public void testDeleteOrderById(){

        //execute
        this.orderService.deleteOrderById(12);

        //verify
        verify(orderRepository, times(1)).deleteById(12L);
    }
}