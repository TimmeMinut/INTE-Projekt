package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

// TODO Make compatible with latest updates
class CheckoutSystemTest {
    public static final Customer VALID_CUSTOMER = new Customer("customerName", "20001231-1234", 15000_00, 500_00);

    public static final Product VALID_PRODUCT = new Product("productName", 20, Product.ProductCategory.STANDARD, false);
    @Test
    void Product_is_added_to_basket() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(VALID_CUSTOMER);
        Product product = VALID_PRODUCT;

        // when
        checkoutSystem.registerProduct(product);

        // then
        assertEquals(product, checkoutSystem.getProduct("productName"));
    }

    @Test
    void Existing_product_is_removed_from_basket() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(VALID_CUSTOMER);
        Product product = VALID_PRODUCT;
        checkoutSystem.registerProduct(product);

        // when
        checkoutSystem.removeProduct("productName");

        // then
        assertFalse(checkoutSystem.contains(product));
    }

    @Test
    void Removing_non_existing_product_from_basket_throws_exception() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(VALID_CUSTOMER);

        // then
        assertThrows(IllegalArgumentException.class, () -> checkoutSystem.removeProduct("productName"));
    }

    @Test
    void Display_checkout_sum_with_product_registered() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(VALID_CUSTOMER);
        Product product = new Product("productName", 29, Product.ProductCategory.STANDARD, false);

        // when
        checkoutSystem.registerProduct(product);

        // then
        assertEquals(29, checkoutSystem.getTotal());
    }

    @Test
    void Empty_basket_show_zero_in_total() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(VALID_CUSTOMER);

        // then
        assertEquals(0, checkoutSystem.getTotal());
    }

    @Test
    void Card_payment_with_enough_money() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(VALID_CUSTOMER);
        Product product = VALID_PRODUCT;
        checkoutSystem.registerProduct(VALID_PRODUCT);
        Card card = new Card(100);

        // when
        checkoutSystem.pay(card);

        // then
        assertEquals(70.01, card.getBalance());
    }

    @Test
    void Percentage_discount() { // Testfall ska möjligen ändras eller raderas?
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(VALID_CUSTOMER);
        Article article1 = new ConcreteArticle(100);
        Article article2 = new ConcreteArticle(50);
        // checkoutSystem.registerProduct(article1);
        // checkoutSystem.registerProduct(article2);

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

    @Test
    void Static_Membership_Discount_is_applied() {
        //given
        final Customer MEMBER_CUSTOMER = new Customer("Miriam", "19990115-2345", 15000_00, 500_00);
        MEMBER_CUSTOMER.becomeMember();

        CheckoutSystem checkoutSystem = new CheckoutSystem(MEMBER_CUSTOMER);
        Product product = new Product("productName", 100, Product.ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(product);

        // when

        // then
        assertEquals(99, checkoutSystem.getTotal());
    }
}