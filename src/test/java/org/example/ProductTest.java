package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    @Test
    void VAT_is_calculated_on_books() {


        // given
        Product product = new Product("journal", 25, Product.ProductCategory.BOOK);

        // when && then
        assertEquals(product.getPrice() * 1.06, product.getVATValue());
    }

    @Test
    void VAT_is_calculated_on_food() {


        // given
        Product product = new Product("Twix", 12, Product.ProductCategory.FOOD);

        // when && then
        assertEquals(product.getPrice() * 1.12, product.getVATValue());
    }

    @Test
    void VAT_is_calculated_on_standard() {


        // given
        Product product = new Product("pen", 17, Product.ProductCategory.STANDARD);

        // when && then
        assertEquals(product.getPrice() * 1.25, product.getVATValue());
    }
}
