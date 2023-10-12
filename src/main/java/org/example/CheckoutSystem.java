package org.example;

import java.util.ArrayList;

public class CheckoutSystem {
    // TODO Different data structure, HashMap?
    private Customer customer;
    private final ArrayList<Product> basket = new ArrayList<>();
    private ArrayList<String> discountCodes = new ArrayList<>();

    public CheckoutSystem(Customer currentCustomer) {
        this.customer = currentCustomer;
    }

    public void registerProduct(Product product) { basket.add(product); }

    public Product getProduct(String productName) {
        Product p = null;
        for (Product product: basket) {
            if (productName.equals(product.getName())) {
                p = product;
            }
        }
        return p;
    }

    public void removeProduct(String productName) {
        Product product = getProduct(productName);

        if (!basket.contains(product)) { throw new IllegalArgumentException(); }

        basket.remove(product);
    }

    public boolean contains(Product product) {
        return basket.contains(product);
    }

    public double getTotal() {
        // TODO Here will all logic be
        // Customer membership => rabatter?
        // Produktkategorirabatter
        // Produktrabatter
        // Åldergräns, kundens ålder vs produkt
        // Rabatt giltighetstid?

        // kolla kundens berättigade rabatt
        // kolla membership och rabatter

        double totalSum = 0;
        for (Product product: basket) {
            totalSum += product.getPrice();
        }
        double discount = getMembershipDiscount();

        return totalSum * ( 1 - discount);
    }

    private double getMembershipDiscount() {
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
