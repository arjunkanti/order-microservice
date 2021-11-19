package com.classpath.ordersapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name="line_items")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "order")
@ToString(exclude = "order")
@Setter
@Getter
public class LineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int itemId;

    private int qty;
    private double price;

    private String name;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;
}