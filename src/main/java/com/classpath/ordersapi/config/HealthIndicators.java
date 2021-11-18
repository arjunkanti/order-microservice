package com.classpath.ordersapi.config;

import com.classpath.ordersapi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
class DBHealthIndicator implements HealthIndicator {

    private final OrderRepository orderRepository;

    @Override
    public Health health() {
        final long count = this.orderRepository.count();
        log.info("Count from DB Service :: {}", count);
        if(count < 10) {
            return Health.down().withDetail("DB-Service", "DB service is down").build();
        }
        return Health.up().withDetail("DB-Service", "DB service is UP!!").build();
    }
}

@Configuration
class KafkaHealthIndicator implements HealthIndicator{
    @Override
    public Health health() {
        return Health.up().withDetail("Kafka-Service", "Kafka service is up !!").build();
    }
}
