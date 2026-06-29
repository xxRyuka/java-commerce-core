package com.coreJava.commerce.order;

import com.coreJava.commerce.cart.Cart;
import com.coreJava.commerce.cart.CartItem;
import com.coreJava.commerce.catalog.Product;
import com.coreJava.commerce.common.InsufficientStockException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private List<Order> orders = new ArrayList<>();
    private Long idCounter = 1L;

    public Order createOrder(Cart cart) {

        if (cart.isEmpty()) {
            throw new IllegalStateException("Sepet boş olduğu için sipariş oluşturulamadı.");
        }
        // once her item içi stock kontrolu
        for (CartItem cartitem : cart.getItems()) {
            Product pr = cartitem.getItem();


            if (pr.getStockQuantity() < cartitem.getQuantity()) {
                throw new InsufficientStockException(pr.getName() + " Yetersiz stock");
            }
        }

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartitem : cart.getItems()) {
            Product pr = cartitem.getItem();

            OrderItem orderLine = new OrderItem(pr, cartitem.getQuantity());
            orderItems.add(orderLine);

            pr.decreaseStock(cartitem.getQuantity());

        }
        Order order = new Order(idCounter, orderItems);
        orders.add(order);
        idCounter++;
        cart.clearCart();
        return order;
    }


    public Optional<Order> findOrderById(Long id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst();
    }


    // string olarak veriyoruz date time kısmını oyüzden bunu nasıl kullancam bide datelerde kıyas nasıl yapılıyor bilemedim
    public Optional<Order> findLastOrder() {
        return orders.reversed().stream().findFirst();
    }

    public List<Order> findOrdersByDate(LocalDate date) {
        return orders.stream().filter(order -> order.getCreatedAt().toLocalDate().equals(date)).toList();
    }


    // startDate --- between --- endDate
    public List<Order> findOrdersBetween(LocalDate startDate, LocalDate endDate) {

        return orders.stream().filter(order -> {
            LocalDate orderDate = order.getCreatedAt().toLocalDate();

            return !orderDate.isBefore(startDate) && !orderDate.isAfter(endDate);
        }).toList();


    }

    public long countOrders() {
        return orders.stream()
                .count();
    }

    public BigDecimal getTotalSalesAmount() {
        return orders.stream().map(Order::getTotalAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }
}
