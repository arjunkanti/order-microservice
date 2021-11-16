package com.classpath.ordersapi.controller;

import com.classpath.ordersapi.model.Order;
import com.classpath.ordersapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    @GetMapping
    public Map<String, Object> fetchAllOrders(@RequestParam(value = "offset", defaultValue = "1", required = false ) int offset,
                                              @RequestParam(value = "records", defaultValue = "10", required = false) int records,
                                              @RequestParam(value = "sort" , defaultValue = "orderDate", required = false) String sortField){

        return this.orderService.findAllOrders(offset, records, sortField);
    }

    @GetMapping("/{id}")
    public Order fetchOrderById(@PathVariable long id){
        return this.orderService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderById(@PathVariable("id") long orderId){
        this.orderService.deleteOrderById(orderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order saveOrder(@RequestBody Order order){
        return this.orderService.saveOrder(order);
    }
}