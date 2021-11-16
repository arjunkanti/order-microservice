package com.classpath.ordersapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;
    @NotEmpty(message = "customer name cannot be empty")
    private String name;
    @PastOrPresent(message = "Order date cannot be in the future")
    private LocalDate orderDate;
    @Email(message = "email address should be a valid email")
    private String emailAddress;
    @Min(value = 10000, message = "minimum order price should be 10k")
    @Max(value = 50000, message = "max order price can be 50k")
    private double orderPrice;
}