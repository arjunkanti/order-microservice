package com.classpath.ordersapi.service;

import com.classpath.ordersapi.model.Order;
import com.classpath.ordersapi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order saveOrder(Order order) {
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