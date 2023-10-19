package org.example;

public class ConcreteArticle implements Article {
    private double price;
    private String name;

    public ConcreteArticle(double price, String name) {
        this.price = price;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
