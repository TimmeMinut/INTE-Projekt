package org.example;

import java.util.ArrayList;

public class CheckoutSystem {
    // TODO Different data structure, HashMap?
    private ArrayList<Article> basket = new ArrayList<>();
    private ArrayList<String> discountCodes = new ArrayList<>();

    public void registerProduct(Article article) { basket.add(article); }

    public Article getProduct(String productName) {
        Article p = null;
        for (Article article: basket) {
            if (productName.equals(article.getName())) {
                p = article;
            }
        }
        return p;
    }

    public void removeProduct(String productName) {
        Article product = getProduct(productName);

        if (!basket.contains(product)) { throw new IllegalArgumentException(); }

        basket.remove(product);
    }

    public boolean contains(Product product) {
        if (basket.contains(product)) {
            return true;
        }
        return false;
    }

    public double getTotal() {
        double totalSum = 0;
        for (Article product: basket) {
            totalSum += product.getPrice();
        }
        return totalSum;

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

    private double percentageDiscount() {

        ArticleGroup articleGroup = new ArticleGroup();
        for (Article article: basket) {
            articleGroup.addArticle(article);
        }
        Article decorator =  new DiscountPercentageDecorator(articleGroup, 0.25);
        return decorator.getPrice();
    }
}
