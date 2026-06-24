package com.coreJava.commerce.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    // null kontrolunu falan nasıl yapcaz
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
