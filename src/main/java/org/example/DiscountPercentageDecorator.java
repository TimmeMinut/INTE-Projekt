package org.example;

import java.util.ArrayList;
import java.util.List;

public class DiscountPercentageDecorator extends ArticleDecorator implements Article {
    private double discount;
    public DiscountPercentageDecorator(Article article, Double discount) {
        super(article);
        this.discount = discount;
    }

    @Override
    public double getPrice() {
        // double originalPrice = super.getPrice();
        return super.getPrice() * ( 1 - discount);
    }

    @Override
    public String getName() {
        return super.getName();
    }
}
