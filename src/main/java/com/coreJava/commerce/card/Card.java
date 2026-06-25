package com.coreJava.commerce.card;

import com.coreJava.commerce.catalog.Product;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Card {
    Long id; //wrapper kullanıyoruz cunku null olabilir
    List<CardItem> items;

    public Card(Long id) {
        this.id = id;
    }

    public void addProduct(Product product, int quantity) {
        if (product == null) {
            return;
        }

        // item mevcutsa quantityi arttir
        for (CardItem item : items) {
            if (Objects.equals(item.getItem().getId(), product.getId())) {
                item.increaseQuantity(quantity);

            }
        }
        // mevcut değilse siparis satırı olustur
        CardItem cardItem = new CardItem(product, quantity);
        items.add(cardItem);
    }


    public boolean removeProduct(Product product) {

         return items.removeIf(cardItem -> cardItem.getItem().getName().equals(product.getName()));
    }

    public boolean changeProductQuantity(Product product,int quantity){
        return items.contains(product);
    }


}
