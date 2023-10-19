package org.example;

public abstract class ArticleDecorator implements Article {
    private Article article;

    protected ArticleDecorator(Article article) {
        this.article = article;
    }

    @Override
    public double getPrice() {
        return article.getPrice();
    }

    @Override
    public String getName() {
        return article.getName();
    }
}
