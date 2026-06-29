package com.coreJava.commerce.order;

import com.coreJava.commerce.catalog.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private Long id;
    private OrderStatus status;
    private List<OrderItem> orderLines = new ArrayList<>();
    private LocalDateTime createdAt;

    public Order(Long id, List<OrderItem> items) {
        this.id = id;
        this.orderLines = new ArrayList<>(items);
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.CREATED;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Order order)) {
            return false; // obj ile nesne'nin sinifi ayni değilse direk false
        }

        return Objects.equals(order.id, this.id);
    }

    @Override
    public String toString() {
        return this.status.toString() + " " + this.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }


    public List<OrderItem> getOrderLines() {
        return orderLines;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTotalAmount() {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItem item : orderLines) {
            totalAmount = totalAmount.add(item.getLineTotal());
        }
        return totalAmount;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String printOrderDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss");

        return formatter.format(this.getCreatedAt());
    }
}
