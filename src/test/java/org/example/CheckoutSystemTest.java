package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutSystemTest {
    @Test
    void Product_is_added_to_basket() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem();
        Product product = new Product("productName");

        // when
        checkoutSystem.registerProduct(product);

        // then
        assertEquals(product, checkoutSystem.getProduct("productName"));
    }

    @Test
    void Existing_product_is_removed_from_basket() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem();
        Product product = new Product("productName");
        checkoutSystem.registerProduct(product);

        // when
        checkoutSystem.removeProduct("productName");

        // then
        assertFalse(checkoutSystem.contains(product));
    }

    @Test
    void Removing_non_existing_product_from_basket_throws_exception() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem();
        Product product = new Product("productName");

        // then
        assertThrows(IllegalArgumentException.class, () -> checkoutSystem.removeProduct("productname"));
    }

    @Test
    void Display_checkout_sum_with_product_registered() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem();
        Product product = new Product("productName", 29.99);

        // when
        checkoutSystem.registerProduct(product);

        // then
        assertEquals(29.99, checkoutSystem.getTotal());
    }

    @Test
    void Empty_basket_show_zero_in_total() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem();

        // then
        assertEquals(0, checkoutSystem.getTotal());
    }

    @Test
    void Card_payment_with_enough_money() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem();
        Product product = new Product("productName", 29.99);
        checkoutSystem.registerProduct(product);
        Card card = new Card(100);

        // then
        checkoutSystem.pay(card);

        assertEquals(70.01, card.getBalance());
    }
}