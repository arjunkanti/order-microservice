package com.classpath.ordersapi.service;

import com.classpath.ordersapi.model.Order;
import com.classpath.ordersapi.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final Source source;

    @Transactional
    //@CircuitBreaker(name = "inventoryservice", fallbackMethod = "fallBackImpl")
    @Retry(name="retryService", fallbackMethod = "fallBackImpl")
    public Order saveOrder(Order order) {
        log.info("Sending the order payload the the topic:::: ");
        //update the inventory
        //call the post endpoint on the inventory microservice using rest template/web client
      //  final ResponseEntity<Integer> responseEntity = this.restTemplate.postForEntity("http://localhost:9222/api/v1/inventory", null, Integer.class);
       // log.info("Response from inventory microservice : {} ", responseEntity.getBody());

        //store the order object as an event object
        Order savedOrder = this.orderRepository.save(order);
        this.source.output().send(MessageBuilder.withPayload(savedOrder).build());
        return savedOrder;
    }

    private Order fallBackImpl(Exception exception){
        log.error("Error while invoking the inventory service:: {}", exception.getMessage());
        return Order.builder().orderDate(LocalDate.now()).orderId(1111).orderPrice(25000).name("user").emailAddress("user@gmail.com").build();
    }

    public Map<String, Object> findAllOrders(int offset, int records, String sortField) {
        PageRequest request = PageRequest.of(offset, records, Sort.by(sortField));
        final Page<Order> pageResponse = this.orderRepository.findAll(request);

        final long totalRecords = pageResponse.getTotalElements();
        final int totalPages = pageResponse.getTotalPages();
        final Set<Order> content = new HashSet<>(pageResponse.getContent());

        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("total_records", totalRecords);
        resultMap.put("pages", totalPages);
        resultMap.put("data", content);
        return resultMap;
    }

    public Order findById(long orderId) {
        return this.orderRepository
                    .findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid order id"));
    }

    public void deleteOrderById(long orderId) {
        this.orderRepository.deleteById(orderId);
    }
}