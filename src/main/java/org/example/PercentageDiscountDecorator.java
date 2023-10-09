package org.example;

public class PercentageDiscountDecorator extends ProductDecorator {
    private double discount;

    public PercentageDiscountDecorator(Product decoratedProduct, double discount) {
        // TODO Agree on discount interval
        super(decoratedProduct);

        if (discount < 5 || discount > 90) {
            throw new IllegalArgumentException();
        }

        this.discount = discount;
    }

    @Override
    public double getPrice() {
        double originalPrice = super.getPrice();
        return originalPrice * (1 - discount);
    }
}
