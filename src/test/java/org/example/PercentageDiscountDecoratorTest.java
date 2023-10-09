package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PercentageDiscountDecoratorTest {

    @Test
    void percentageDiscountDecorator_ShouldWorkOnSingleProduct() {
        Product product = new ConcreteProduct("p1", 29.99);

        Product discountDecorator = new PercentageDiscountDecorator(product, 0.5);

        System.out.println("Product 1 price: " + product.getPrice());
        System.out.println("Discounted price: " + discountDecorator.getPrice());
        Assertions.assertEquals(14.995, discountDecorator.getPrice());
    }

    @Test
    void percentageDiscountDecorator_ShouldWorkOnProductGroup() {
        Product p1 = new ConcreteProduct("p1", 10);
        Product p2 = new ConcreteProduct("p1", 20);

        ProductGroup productGroup = new ProductGroup();
        productGroup.addProduct(p1);
        productGroup.addProduct(p2);

        Product discountDecorator = new PercentageDiscountDecorator(productGroup, 0.5);

        System.out.println("Product 1 price: " + p1.getPrice());
        System.out.println("Product 2 price: " + p2.getPrice());
        System.out.println("Group price: " + productGroup.getPrice());
        System.out.println("Discounted group price: " + discountDecorator.getPrice());
        Assertions.assertEquals(15, discountDecorator.getPrice());
    }
}