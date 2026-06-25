package com.coreJava.commerce.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private OrderStatus status ;
    private List<OrderItem> orderLines = new ArrayList<>();

    public Order(Long id, List<OrderItem> items) {
        this.id = id;
        this.orderLines = new ArrayList<>(items);
        this.status = OrderStatus.CREATED;
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
}
