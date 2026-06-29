package com.coreJava.commerce;

import com.coreJava.commerce.cart.Cart;
import com.coreJava.commerce.cart.CartItem;
import com.coreJava.commerce.catalog.CatalogService;
import com.coreJava.commerce.catalog.Category;
import com.coreJava.commerce.catalog.Product;
import com.coreJava.commerce.common.InsufficientStockException;
import com.coreJava.commerce.order.Order;
import com.coreJava.commerce.order.OrderItem;
import com.coreJava.commerce.order.OrderService;
import com.coreJava.commerce.reporting.ReportService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final DateTimeFormatter ORDER_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public static void main(String[] args) {

        CatalogService catalogService = createCatalog();
        Cart cart = new Cart(1L);
        OrderService orderService = new OrderService();
        ReportService reportService = new ReportService(catalogService.getProducts());

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        System.out.println("========================================");
        System.out.println("   CORE JAVA COMMERCE CONSOLE APP");
        System.out.println("========================================");

        while (isRunning) {
            printMenu();

            int choice = readInt(scanner, "Seçiminiz: ");

            switch (choice) {
                case 1 -> printProducts(catalogService);

                case 2 -> addProductToCart(scanner, catalogService, cart);

                case 3 -> printCart(cart);

                case 4 -> changeCartProductQuantity(scanner, cart);

                case 5 -> removeProductFromCart(scanner, cart);

                case 6 -> checkout(cart, orderService);

                case 7 -> printOrders(orderService);

                case 8 -> sprint8OptionalDateTimeDemo(orderService);

                case 9 -> sprint7StreamDemo(catalogService, reportService);

                case 0 -> {
                    isRunning = false;
                    System.out.println("Uygulama kapatıldı.");
                }

                default -> System.out.println("Geçersiz seçim yaptınız.");
            }
        }

        scanner.close();
    }

    private static CatalogService createCatalog() {
        Category electronics = new Category(1L, "Elektronik");
        Category books = new Category(2L, "Kitap");

        Product keyboard = new Product(
                1L,
                "Wraith Keyboard",
                electronics,
                18,
                new BigDecimal("180.00")
        );

        Product laptop = new Product(
                2L,
                "HP Omen Laptop",
                electronics,
                21,
                new BigDecimal("1800.00")
        );

        Product book = new Product(
                3L,
                "Clean Code",
                books,
                120,
                new BigDecimal("5.00")
        );

        Product mouse = new Product(
                4L,
                "Gaming Mouse",
                electronics,
                50,
                new BigDecimal("50.00")
        );

        CatalogService catalogService = new CatalogService();

        catalogService.addProduct(keyboard);
        catalogService.addProduct(laptop);
        catalogService.addProduct(book);
        catalogService.addProduct(mouse);

        return catalogService;
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("--------------- MENU ---------------");
        System.out.println("1 - Ürünleri Listele");
        System.out.println("2 - Sepete Ürün Ekle");
        System.out.println("3 - Sepeti Görüntüle");
        System.out.println("4 - Sepetteki Ürün Miktarını Değiştir");
        System.out.println("5 - Sepetten Ürün Sil");
        System.out.println("6 - Siparişi Tamamla");
        System.out.println("7 - Sipariş Geçmişi");
        System.out.println("8 - Sprint 8 Optional Date-Time Demo");
        System.out.println("9 - Sprint 7 Stream Report Demo");
        System.out.println("0 - Çıkış");
        System.out.println("------------------------------------");
    }

    private static void printProducts(CatalogService catalogService) {
        System.out.println();
        System.out.println("========== KATALOG ==========");

        List<Product> products = catalogService.getProducts();

        if (products.isEmpty()) {
            System.out.println("Katalogda ürün bulunmuyor.");
            return;
        }

        for (Product product : products) {
            System.out.printf(
                    "ID: %d | Ürün: %s | Kategori: %s | Fiyat: %s | Stok: %d%n",
                    product.getId(),
                    product.getName(),
                    product.getCategory().getName(),
                    product.getPrice(),
                    product.getStockQuantity()
            );
        }
    }

    private static void addProductToCart(
            Scanner scanner,
            CatalogService catalogService,
            Cart cart
    ) {
        printProducts(catalogService);

        Long productId = readLong(scanner, "Sepete eklenecek ürün ID: ");

        Product product = catalogService.findById(productId);

        if (product == null) {
            System.out.println("Bu ID ile bir ürün bulunamadı.");
            return;
        }

        int quantity = readInt(scanner, "Miktar: ");

        if (quantity <= 0) {
            System.out.println("Miktar sıfırdan büyük olmalıdır.");
            return;
        }

        cart.addProduct(product, quantity);

        System.out.println(
                product.getName()
                        + " sepete eklendi. Eklenen miktar: "
                        + quantity
        );
    }

    private static void changeCartProductQuantity(
            Scanner scanner,
            Cart cart
    ) {
        if (cart.isEmpty()) {
            System.out.println("Sepet boş olduğu için miktar değiştirilemez.");
            return;
        }

        printCart(cart);

        Long productId = readLong(scanner, "Miktarı değişecek ürün ID: ");
        int newQuantity = readInt(scanner, "Yeni miktar: ");

        if (newQuantity <= 0) {
            System.out.println("Miktar sıfırdan büyük olmalıdır.");
            return;
        }

        boolean isChanged = cart.changeProductQuantity(productId, newQuantity);

        if (isChanged) {
            System.out.println("Ürün miktarı güncellendi.");
        } else {
            System.out.println("Bu ürün sepette bulunamadı.");
        }
    }

    private static void removeProductFromCart(
            Scanner scanner,
            Cart cart
    ) {
        if (cart.isEmpty()) {
            System.out.println("Sepet zaten boş.");
            return;
        }

        printCart(cart);

        Long productId = readLong(scanner, "Silinecek ürün ID: ");

        boolean isRemoved = cart.removeProduct(productId);

        if (isRemoved) {
            System.out.println("Ürün sepetten silindi.");
        } else {
            System.out.println("Bu ürün sepette bulunamadı.");
        }
    }

    private static void checkout(
            Cart cart,
            OrderService orderService
    ) {
        try {
            Order order = orderService.createOrder(cart);

            System.out.println();
            System.out.println("Sipariş başarıyla oluşturuldu.");
            printOrder(order);

        } catch (InsufficientStockException | IllegalStateException ex) {
            System.out.println();
            System.out.println("Sipariş oluşturulamadı.");
            System.out.println("Sebep: " + ex.getMessage());
        }
    }

    private static void printOrders(OrderService orderService) {
        List<Order> orders = orderService.getOrders();

        System.out.println();
        System.out.println("======= SİPARİŞ GEÇMİŞİ =======");

        if (orders.isEmpty()) {
            System.out.println("Henüz oluşturulmuş sipariş yok.");
            return;
        }

        for (Order order : orders) {
            printOrder(order);
        }
    }

    private static void printOrder(Order order) {
        System.out.println();
        System.out.println("========== ORDER ==========");
        System.out.println("Order ID: " + order.getId());
        System.out.println("Status: " + order.getStatus());
        System.out.println("Order Date: " + order.getCreatedAt().format(ORDER_DATE_FORMATTER));
        System.out.println("---------------------------");

        for (OrderItem item : order.getOrderLines()) {
            System.out.printf(
                    "Ürün ID: %d | Ürün: %s | Miktar: %d | Birim Fiyat: %s | Satır Toplamı: %s%n",
                    item.getProductId(),
                    item.getProductName(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getLineTotal()
            );
        }

        System.out.println("---------------------------");
        System.out.println("Sipariş Toplamı: " + order.getTotalAmount());
        System.out.println("===========================");
    }

    private static void printCart(Cart cart) {
        System.out.println();
        System.out.println("========== SEPET ==========");

        if (cart.isEmpty()) {
            System.out.println("Sepetiniz şu an boş.");
            return;
        }

        for (CartItem item : cart.getItems()) {
            System.out.printf(
                    "ID: %d | Ürün: %s | Miktar: %d | Birim Fiyat: %s | Satır Toplamı: %s%n",
                    item.getItem().getId(),
                    item.getItem().getName(),
                    item.getQuantity(),
                    item.getItem().getPrice(),
                    item.getLineTotal()
            );
        }

        System.out.println("------------------------------------");
        System.out.println("Toplam Ürün Adedi: " + cart.getTotalQuantity());
        System.out.println("Genel Toplam: " + cart.getTotalAmount());
    }

    private static void sprint8OptionalDateTimeDemo(OrderService orderService) {
        System.out.println();
        System.out.println("===== SPRINT 8 OPTIONAL & DATE-TIME DEMO =====");

        Optional<Order> foundOrder = orderService.findOrderById(1L);

        if (foundOrder.isPresent()) {
            System.out.println("1 ID'li sipariş bulundu:");
            printOrder(foundOrder.get());
        } else {
            System.out.println("1 ID'li sipariş bulunamadı.");
        }

        Optional<Order> notFoundOrder = orderService.findOrderById(999L);

        if (notFoundOrder.isEmpty()) {
            System.out.println("999 ID'li sipariş bulunamadı.");
        }

        orderService.findLastOrder()
                .ifPresent(order -> {
                    System.out.println();
                    System.out.println("Son sipariş bulundu:");
                    printOrder(order);
                });

        try {
            Order requiredOrder = orderService.findOrderById(1L)
                    .orElseThrow(() -> new IllegalArgumentException("Sipariş bulunamadı."));

            System.out.println("orElseThrow ile alınan sipariş ID: " + requiredOrder.getId());

        } catch (IllegalArgumentException ex) {
            System.out.println("orElseThrow sonucu hata: " + ex.getMessage());
        }

        LocalDate today = LocalDate.now();

        List<Order> todayOrders = orderService.findOrdersByDate(today);

        System.out.println();
        System.out.println("Bugünkü sipariş sayısı: " + todayOrders.size());

        List<Order> lastSevenDaysOrders = orderService.findOrdersBetween(
                today.minusDays(7),
                today
        );

        System.out.println("Son 7 gündeki sipariş sayısı: " + lastSevenDaysOrders.size());
        System.out.println("Toplam sipariş sayısı: " + orderService.countOrders());
        System.out.println("Toplam satış tutarı: " + orderService.getTotalSalesAmount());
        System.out.println("==============================================");
    }

    private static void sprint7StreamDemo(
            CatalogService catalogService,
            ReportService reportService
    ) {
        System.out.println();
        System.out.println("===== SPRINT 7 STREAM DEMO =====");

        System.out.println("--- Stokta olan ürünler ---");
        catalogService.findInStockProducts()
                .forEach(System.out::println);

        System.out.println("--- Pahalı ürünler ---");
        catalogService.findProductsExpensiveThan(new BigDecimal("1000"))
                .forEach(System.out::println);

        System.out.println("--- Ürün isimleri ---");
        catalogService.getProductNames()
                .forEach(System.out::println);

        System.out.println("--- Fiyata göre sıralı ürünler ---");
        catalogService.getProductsSortedByPriceAsc()
                .forEach(product ->
                        System.out.println(product.getName() + " | " + product.getPrice())
                );

        System.out.println("--- İsme göre sıralı ürünler ---");
        catalogService.getProductsSortedByNameAsc()
                .forEach(product ->
                        System.out.println(product.getName())
                );

        System.out.println("--- Rapor ---");
        System.out.println("Toplam stok adedi: " + reportService.getTotalStockQuantity());
        System.out.println("Toplam envanter değeri: " + reportService.getTotalInventoryValue());
        System.out.println("Ortalama ürün fiyatı: " + reportService.getAverageProductPrice());

        System.out.println("Stokta olmayan ürün var mı? " + catalogService.hasAnyOutOfStockProduct());
        System.out.println("Tüm ürünler stokta mı? " + catalogService.areAllProductsInStock());
        System.out.println("Ücretsiz ürün yok mu? " + catalogService.hasNoFreeProduct());
        System.out.println("================================");
    }

    private static int readInt(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.println("Lütfen geçerli bir sayı girin.");
            }
        }
    }

    private static Long readLong(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {
                return Long.parseLong(input);
            } catch (NumberFormatException ex) {
                System.out.println("Lütfen geçerli bir ID girin.");
            }
        }
    }
}