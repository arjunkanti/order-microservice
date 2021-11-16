package com.classpath.ordersapi.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTests {

    private Order order;

    @BeforeEach
    public void setUu(){
         order = Order.builder().name("Praveen").orderDate(LocalDate.of(2021, 10, 19)).emailAddress("praveen@gmail.com").orderPrice(25000).build();
    }
    @Test
    public void testOrderConstructor(){
        Assertions.assertNotNull(order);
    }
    @Test
    public void testGetterMethods(){
        assertEquals(order.getName(), "Praveen");
        assertEquals(order.getOrderDate(), LocalDate.of(2021, 10, 19));
        assertEquals(order.getEmailAddress(), "praveen@gmail.com");
        assertEquals(order.getOrderPrice(), 25000);
    }
    @Test
    public void testSetterMethods(){
        order.setName("Harish");
        order.setOrderDate(LocalDate.now());
        order.setEmailAddress("harish@gmail.com");
        order.setOrderPrice(10000);
        assertEquals(order.getName(), "Harish");
        assertEquals(order.getOrderDate(), LocalDate.now());
        assertEquals(order.getEmailAddress(), "harish@gmail.com");
        assertEquals(order.getOrderPrice(), 10000);
    }

    @Test
    public void testToString(){
        assertEquals("Order(name=Praveen, orderDate=2021-10-19, emailAddress=praveen@gmail.com, orderPrice=25000.0)", order.toString());
    }
}