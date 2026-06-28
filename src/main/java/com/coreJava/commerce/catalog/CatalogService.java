package com.coreJava.commerce.catalog;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class CatalogService {

    // TODO: Javada List direk interfaceyi kapsıyor sanırım ArrayList'mi yapmalıydım ? bunu arastırıp not almam lazım !
    // final ekleyerek listeyi const yaptım listeyi manipule edebilirim fakat sonran baska bir liste ile değiştiremem CONSTANT !
    private final List<Product> products; // burda productları tutacagım in memory liste
    // Map<Key,Value>
    // final ekleyerek listeyi const yaptım listeyi manipule edebilirim fakat sonran baska bir liste ile değiştiremem CONSTANT !
    private final Map<Long, Product> productMap; // id ile eslestircem burda direk

    public CatalogService() { // bunu nullable yapmak istiyom c#'da ? koyuyordum ama burda nasıl olmalı
        // listeyi ve mapi set etmem gerekiyor baslangıcta!

        this.products = new ArrayList<Product>();
        this.productMap = new HashMap<Long, Product>();
    }


    public void addProduct(Product product) {
        Long id = product.getId();

//        for (Product pr : products) {
//            if (pr.getId().equals(product.getId())) {
//                System.out.printf("Urun zaten Mevcut urun id %d", product.getId());
//                return; // return ile fonksiyondan cıkıs dogru mu ? go'da böyle yapıyordum
//            }
//        }

        if (existsById(product.getId())) {
            System.out.printf("Urun zaten Mevcut urun id %d " + "\n", product.getId());
            return; // return ile fonksiyondan cıkıs dogru mu ? go'da böyle yapıyordum
        }

        products.add(product); // Listeye ekledik ek olarak mape set edelim
        productMap.put(product.getId(), product); // map içindede set edildi


    }


    public boolean existsById(Long id) {
        return productMap.containsKey(id);
    }


    public List<Product> findProducts(Predicate<Product> condition) {

        List<Product> filteredList = new ArrayList<>();
        for (Product item : products) {
            if (condition.test(item)) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }


    public void forEachProduct(Consumer<Product> productConsumer) {
        for (Product item : products) {
            productConsumer.accept(item);
        }
    }

    public List<Product> getProductsSorted(Comparator<Product> productComparator) {
        List<Product> sortedList = new ArrayList<>(products);
        sortedList.sort(productComparator);
        return sortedList;
    }


    public List<Product> findProductsWithStream(Predicate<Product> condition) {
        return products.stream().filter(condition).toList();
    }

    public List<Product> findInStockProducts() {
        return products.stream().filter(product -> product.getStockQuantity() > 0).toList();
    }


    public List<Product> findProductsExpensiveThan(BigDecimal minPrice) {
        return products.stream().filter(product -> product.getPrice().compareTo(minPrice) >= 0).toList(); // neden altı cizildi ve mor oldu
    }

    public List<Product> getProductsSortedByPriceAsc() {
        return products.stream().sorted(Comparator.comparing(Product::getPrice))
                .toList();
    }


    public List<Product> getProductsSortedByNameAsc() {
        return products.stream().sorted(Comparator.comparing(Product::getName)).toList();
    }


    public long countProducts() {
        return products.stream()
                .count();
    }


    public boolean hasAnyOutOfStockProduct() {
        return products.stream().anyMatch(product -> product.getStockQuantity() <= 0);
    }

    public List<String> getProductNames() {
        return products.stream().map(Product::getName).toList();
    }

    public Product findById(Long id) {
        return productMap.get(id);
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Product> findProductsByCategoryId(Long id) {
        List<Product> filteredList = new ArrayList<Product>();

        for (Product pr : products) {
            if (pr.getCategory().getId().equals(id)) {
                filteredList.add(pr);
            }
        }
        return filteredList;
    }


    // price 50 ise
    // gelen urunlerin pricesi daha kucuk ola
    public List<Product> findProductsByMinPrice(BigDecimal price) {
        List<Product> filteredList = new ArrayList<Product>();

        for (Product pr : products) {
            if (price.compareTo(pr.getPrice()) < 0) {
                filteredList.add(pr);
            }
        }
        return filteredList;
    }

    public boolean removeProductById(Long id) {

        if (findById(id) == null) {
            System.out.println("girilen id'ye sahip ürün bulunamadı" + "\n");

            return false;
        }

        products.remove(findById(id));
        productMap.remove(id);
        return true;
    }

    public int getProductCount() {
        return products.size();
    }
}
