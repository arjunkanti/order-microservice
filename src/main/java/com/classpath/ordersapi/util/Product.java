package com.classpath.ordersapi.util;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class Product {
    private String name;
    private double price;
    private LocalDate launchDate;
    private Company company;
}

enum Company {
    APPLE,
    SAMSUNG,
    LG,
    OPPO
}