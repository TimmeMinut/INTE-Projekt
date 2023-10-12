package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutSystemTest {

    @Test
    void Product_is_added_to_basket() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem();
        Product product = new Product("productName", 1, Product.ProductCategory.STANDARD, true);

        // when
        checkoutSystem.addProduct(product);

        // then
        assertEquals(product, checkoutSystem.getProduct("productName"));
    }

    @Test
    void Existing_product_is_removed_from_basket() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem();
        Product product = new Product("productName", 1, Product.ProductCategory.STANDARD, true);
        checkoutSystem.addProduct(product);

        // when
        checkoutSystem.removeProduct("productName");

        // then
        assertFalse(checkoutSystem.contains(product));
    }

    @Test
    void Removing_non_existing_product_from_basket_throws_exception() {

    }
}