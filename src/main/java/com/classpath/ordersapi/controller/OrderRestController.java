package com.classpath.ordersapi.controller;

import com.classpath.ordersapi.model.Order;
import com.classpath.ordersapi.service.OrderService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(name = "developer", email = "support@classpath.com", url = "https://www.classpath.io/support"),
                title = "Orders-API",
                license = @License(name = "Apache MIT")
        )
)
public class OrderRestController {

    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "fetch all the orders")
    @ApiResponses(
            {
                    @ApiResponse ( responseCode = "200", description = "Fetching all the orders"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized access. Invalid credentials"),
                    @ApiResponse(responseCode = "403", description = "Forbidden to access the API")
            }

    )
    public Map<String, Object> fetchAllOrders(@RequestParam(value = "offset", defaultValue = "1", required = false ) int offset,
                                              @RequestParam(value = "records", defaultValue = "10", required = false) int records,
                                              @RequestParam(value = "sort" , defaultValue = "orderDate", required = false) String sortField){

        return this.orderService.findAllOrders(offset, records, sortField);
    }

    @GetMapping("/{id}")
    @Operation(summary = "fetch the order based on the orderid passed")
    @ApiResponses(
            {
                    @ApiResponse ( responseCode = "200", description = "Fetching the order based on the order id"),
                    @ApiResponse ( responseCode = "404", description = "Invalid order id passed"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized access. Invalid credentials"),
                    @ApiResponse(responseCode = "403", description = "Forbidden to access the API")
            }

    )
    public Order fetchOrderById(@Parameter(required = true, example = "1", description = "order id to be passed") @PathVariable long id){
        return this.orderService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete order based on the orderid passed")
    @ApiResponses(
            {
                    @ApiResponse ( responseCode = "204", description = "Successful deletion of order based on the matching order id"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized access. Invalid credentials"),
                    @ApiResponse(responseCode = "403", description = "Forbidden to access the API")
            }

    )
    public void deleteOrderById(@Parameter(required = true, example = "1", description = "order id to be passed") @PathVariable("id") long orderId){
        this.orderService.deleteOrderById(orderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Delete order based on the orderid passed")
    @ApiResponses(
            {
                    @ApiResponse ( responseCode = "201", description = "Successful creation of order"),
                    @ApiResponse ( responseCode = "400", description = "Invalid order data passed as input"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized access. Invalid credentials"),
                    @ApiResponse(responseCode = "403", description = "Forbidden to access the API")
            }
    )
    public Order saveOrder(
            @Parameter (required = true, name = "order", schema = @Schema(implementation = Order.class),description = "Order entity to be saved")
            @RequestBody
            @Valid
            Order order){
        return this.orderService.saveOrder(order);
    }
}