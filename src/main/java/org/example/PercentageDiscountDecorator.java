package org.example;

public class PercentageDiscountDecorator extends ProductDecorator {
    private double discount;

    public PercentageDiscountDecorator(Product decoratedProduct, double discount) {
        super(decoratedProduct);
        this.discount = discount;
    }

    @Override
    public double getPrice() {
        double originalPrice = super.getPrice();
        return originalPrice * (1 - discount);
    }
}
