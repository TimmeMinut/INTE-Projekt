package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckoutSystem {
    private final Customer customer;
    private final Map<Product, Integer> basket = new HashMap<>();
    private final ArrayList<String> discountCodes = new ArrayList<>();

    public CheckoutSystem(Customer currentCustomer) {
        this.customer = currentCustomer;
    }

    public void registerProduct(Product product) {
        if (!basket.containsKey(product)) {
            basket.put(product, 1);
        } else {
            int quantity = basket.get(product);
            basket.put(product, ++quantity); // Skriver över tidigare quantity med +1
        }
    }


    public Product getProduct(String productName) {
        Product p = null;
        for (Product product : basket.keySet()) {
            if (productName.equals(product.getName())) {
                p = product;
            }
        }
        return p;
    }

    public void removeProduct(String productName) {
        Product product = getProduct(productName);

        if (!basket.containsKey(product)) {
            throw new IllegalArgumentException();
        }

        if (basket.get(product) == 1) { // Om det bara finns 1 av produkten tas entry bort
            basket.remove(product);
        } else {
            int quantity = basket.get(product);
            basket.put(product, --quantity); // Skriver över tidigare quantity med -1
        }
    }

    public boolean contains(Product product) {
        return basket.containsKey(product);
    }

    public long getTotal() {
        // TODO Here will all logic be
        // Produktkategorirabatter
        // Produktrabatter
        // Åldersgräns, kundens ålder vs produkt
        // Rabatt giltighetstid?

        // kolla kundens berättigade rabatt
        // kolla membership och rabatter
        long totalSum = 0;
        for (Product product : basket.keySet()) {
            int quantity = basket.get(product);
            long quantityDiscount = getQuantityDiscount(product);

            totalSum += (product.getPrice() * quantity) - quantityDiscount;
        }

        double membershipDiscount = 0;
        if (customer.getMembership() != null) {
            membershipDiscount = getMembershipDiscount();
        }

        return (long) (totalSum * (1 - membershipDiscount));
    }

    private double getQuantityDiscount(Product product) {
            if(product.getQuantityDiscount() != null){
                int toBeBought = basket.get(product);

                int amountToReachDiscount = product.getQuantityDiscount().getKey();

                //int amountToReachDiscount = (int) product.getQuantityDiscount().getLeft();

                if(toBeBought >= amountToReachDiscount){
                    int totalPayFor = 0;
                    int payFor = (int) product.getQuantityDiscount().getValue();
                    int temp = toBeBought;

                    while(temp >= amountToReachDiscount){
                        totalPayFor += temp -payFor;
                        temp =-amountToReachDiscount;
                    }

                    return totalPayFor * product.getPrice();
                }

            }
        return 0;
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

    public void pay(Card card) {
        long total = getTotal();
        long balance = card.getBalance();

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


}
