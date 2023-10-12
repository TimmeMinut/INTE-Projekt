package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// TODO Make compatible with HashMap
public class CheckoutSystem {
    private Customer customer;
    private final HashMap<Product, Integer> basket = new HashMap<Product,Integer>();
    private ArrayList<String> discountCodes = new ArrayList<>();

    public CheckoutSystem(Customer currentCustomer) {
        this.customer = currentCustomer;
    }

    public void registerProduct(Product product, int quantity) {
        basket.put(product, quantity);
    }

//    public Product getProduct(String productName) {
//        Product p = null;
//        for (Product product : basket) {
//            if (productName.equals(product.getName())) {
//                p = product;
//            }
//        }
//        return p;
//    }

//    public void removeProduct(String productName) {
//        Product product = getProduct(productName);
//
//        if (!basket.contains(product)) {
//            throw new IllegalArgumentException();
//        }
//
//        basket.remove(product);
//    }

//    public boolean contains(Product product) {
//        return basket.contains(product);
//    }

    public double getTotal() {
        // TODO Here will all logic be
        // Produktkategorirabatter
        // Produktrabatter
        // Åldergräns, kundens ålder vs produkt
        // Rabatt giltighetstid?

        // kolla kundens berättigade rabatt
        // kolla membership och rabatter

        double totalSum = 0;
        for (Product product : basket.keySet()) {
            int quantity = basket.get(product);
            double quantityDiscount = getQuantityDiscount(product);

            totalSum += (product.getPrice() * quantity) - quantityDiscount;
        }

        double membershipDiscount = 0;
        if (customer.getMembership() != null) {
            membershipDiscount = getMembershipDiscount();
        }

        return totalSum * (1 - membershipDiscount);
    }

    private double getQuantityDiscount(Product product) {
            int take = basket.get(product); // Quantity
            int pay = product.getDiscount().get(take);

            return (take - pay) * product.getPrice();
    }

    private double getMembershipDiscount() { // ev. decorator senare?
        switch (customer.getMembership().getLevel()) {
            case "Bronze":
                return 0.01;
            case "Silver":
                return 0.03;
            case "Gold":
                return 0.05;
            default:
                return 0;
        }
    }

//    private double getProductSpecificDiscount(Product product) {
//        long reduceBy = 0;
//        if (!product.getDiscount().containsKey(null)) {
//            Map<Integer, Integer> discount = product.getDiscount();
//
//            for (Map.Entry<Integer, Integer> entry : discount.entrySet()) {
//                reduceBy = entry.getKey() - entry.getValue();
//            }
//            reduceBy = (long) product.getPrice() * reduceBy;
//        }
//        return reduceBy;
//    }

    public void pay(Card card) {
        double total = getTotal();
        double balance = card.getBalance();

        if (total > balance) {
            // TODO How to handle this?
        }

        card.debit(total);
    }

    public void registerDiscountCode(String code) {
        discountCodes.add(code);
    }

    /*private double percentageDiscount() {
        ArticleGroup articleGroup = new ArticleGroup();
        for (Article article: basket) {
            articleGroup.addArticle(article);
        }

        Article decorator =  new DiscountPercentageDecorator(articleGroup, 0.25);
        return decorator.getPrice();
    }*/

    public int getTotalPrice() {
        return 0;
    }
}
