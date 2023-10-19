package org.example;

import java.util.ArrayList;
import java.util.List;

public class ArticleGroup implements Article {
    private List<Article> articles = new ArrayList<>();

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
    public String getName() {
        return null;
    }
}
