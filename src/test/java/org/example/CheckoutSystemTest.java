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

        // when
        checkoutSystem.pay(card);

        // then
        assertEquals(70.01, card.getBalance());
    }

    @Test
    void Percentage_discount() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem();
        Article article1 = new ConcreteArticle(100);
        Article article2 = new ConcreteArticle(50);
        checkoutSystem.registerProduct(article1);
        checkoutSystem.registerProduct(article2);

        // when
        checkoutSystem.registerDiscountCode("25");

        // then
        assertEquals(112.5, checkoutSystem.getTotal());
    }

    @Test
    void Decorator() {
        // given
        Article article1 = new ConcreteArticle(100);
        Article article2 = new ConcreteArticle(50);
        ArticleGroup articleGroup = new ArticleGroup();
        articleGroup.addArticle(article1);
        articleGroup.addArticle(article2);

        // when
        Article discountArticles = new DiscountPercentageDecorator(articleGroup, 0.25);

        // then
        assertEquals(112.5, discountArticles.getPrice());
    }
}