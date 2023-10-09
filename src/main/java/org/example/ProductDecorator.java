package org.example;

public abstract class ProductDecorator implements Product{
    private Product decoratedProduct;

    public ProductDecorator(Product decoratedProduct) {
        this.decoratedProduct = decoratedProduct;
    }

    @Override
    public double getPrice() {
        return decoratedProduct.getPrice();
    }
}
