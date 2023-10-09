package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PercentageDiscountDecoratorTest {

    @Test
    @DisplayName("")
    void Percentage_discount_is_appled_to_single_product() {
        // given
        final double DISCOUNT_PERCENTAGE = 0.5;
        Product product = new ConcreteProduct("productName", 29.99);

        // when
        Product discountDecorator = new PercentageDiscountDecorator(product, DISCOUNT_PERCENTAGE);

        // then
        Assertions.assertEquals(14.995, discountDecorator.getPrice());
    }

    @Test
    void Percentage_discount_is_applied_to_product_group() {
        // given
        Product product1 = new ConcreteProduct("productName", 10);
        Product product2 = new ConcreteProduct("productName", 20);

        ProductGroup productGroup = new ProductGroup();
        productGroup.addProduct(product1);
        productGroup.addProduct(product2);

        // when
        Product discountDecorator = new PercentageDiscountDecorator(productGroup, 0.5);

        // then
        Assertions.assertEquals(15, discountDecorator.getPrice());
    }
}