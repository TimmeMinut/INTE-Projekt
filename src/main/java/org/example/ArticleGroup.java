package org.example;

import java.util.ArrayList;

public class ArticleGroup implements Article {
    private final ArrayList<Article> articles = new ArrayList<>();

    public void addArticle(Article article) {
        articles.add(article);
    }

    @Override
    public double getPrice() {
        double totalPrice = 0;
        for (Article article: articles) {
            totalPrice += article.getPrice();
        }
        return totalPrice;
    }

    @Override
    public double getDiscountAmount() {
        double totalDiscountAmount = 0;
        for (Article article: articles) {
            totalDiscountAmount += article.getDiscountAmount();
        }
        return totalDiscountAmount;
    }
}
