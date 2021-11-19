package com.classpath.ordersapi.config;

import com.classpath.ordersapi.model.LineItem;
import com.classpath.ordersapi.model.Order;
import com.classpath.ordersapi.repository.OrderRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
public class BootstrapAppData implements ApplicationListener<ApplicationReadyEvent> {

    private final OrderRepository orderRepository;
    private final Faker faker = new Faker();

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        IntStream.range(1,1000).forEach(
                (index) -> {
                    Order order = Order
                                        .builder()
                                            .orderPrice(faker.number().randomDouble(2, 10000, 45000))
                                            .name(faker.name().fullName())
                                            .orderDate(faker.date().past( 5, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                                            .emailAddress(faker.internet().emailAddress().toLowerCase())
                                        .build();
                    IntStream.range(1,4).forEach(val -> {
                        LineItem lineItem = LineItem.builder()
                                                .qty(faker.number().numberBetween(2,4))
                                                .price(faker.number().randomDouble(2, 25000, 30000))
                                                .name(faker.commerce().productName())
                                            .build();
                        order.addLineItem(lineItem);
                    });
                    this.orderRepository.save(order);
                }
        );
    }
}