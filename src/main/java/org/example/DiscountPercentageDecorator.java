package org.example;



public class DiscountPercentageDecorator extends ArticleDecorator {
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
}
