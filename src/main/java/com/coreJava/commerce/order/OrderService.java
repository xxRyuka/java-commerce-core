package com.coreJava.commerce.order;

import com.coreJava.commerce.cart.Cart;
import com.coreJava.commerce.cart.CartItem;
import com.coreJava.commerce.catalog.Product;
import com.coreJava.commerce.common.InsufficientStockException;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private List<Order> orders = new ArrayList<>();
    private Long idCounter = 1L;

    public Order createOrder(Cart cart) {

        if (cart.isEmpty()){
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

    public List<Order> getOrders(){
        return orders;
    }
}
