package com.classpath.ordersapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

@SpringBootApplication
@EnableBinding(Source.class)
public class OrdersApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrdersApiApplication.class, args);
	}
}
