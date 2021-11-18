package com.classpath.ordersapi.service;

import com.classpath.ordersapi.model.Order;
import com.classpath.ordersapi.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    @Transactional
    @CircuitBreaker(name = "inventoryservice")
    public Order saveOrder(Order order) {
        //update the inventory
        //call the post endpoint on the inventory microservice using rest template/web client
        final ResponseEntity<Integer> responseEntity = this.restTemplate.postForEntity("http://inventory-microservice/api/v1/inventory", null, Integer.class);
        log.info("Response from inventory microservice : {} ", responseEntity.getBody());
        return this.orderRepository.save(order);
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