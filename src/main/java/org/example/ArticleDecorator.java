package org.example;

public abstract class ArticleDecorator implements Article {
    private final Article article;

    public ArticleDecorator(Article article) {
        this.article = article;
    }

    @Override
    public double getPrice() {
        return article.getPrice();
    }

    @Override
    public double getDiscountAmount() {
        return article.getPrice();
    }
}
