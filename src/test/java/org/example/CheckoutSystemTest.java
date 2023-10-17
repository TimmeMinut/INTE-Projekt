package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.StandardWatchEventKinds;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutSystemTest {
    public static final Customer VALID_CUSTOMER = new Customer("customerName", "20001231-1234", 15000_00, 500_00);

    public static final Product VALID_PRODUCT = new Product("productName", 20_00, Product.ProductCategory.STANDARD, false);

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
        Product product = new Product("productName", 29_00, Product.ProductCategory.STANDARD, false);

        // when
        checkoutSystem.registerProduct(product);

        // then
        assertEquals(29_00 * 1.25, checkoutSystem.getTotal());
    }

    @Test
    void Display_checkout_sum_with_amount_reaching_product_discount() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(VALID_CUSTOMER);
        Product product = new Product("productName", 29_00, Product.ProductCategory.STANDARD, false);
        product.putUpForSale(3,2);
        // when
        checkoutSystem.registerProduct(product);
        checkoutSystem.registerProduct(product);
        checkoutSystem.registerProduct(product);

        // then
        assertEquals((29_00 * 1.25) * 2, checkoutSystem.getTotal());
    }

    @Test
    void Display_checkout_sum_with_amount_not_reaching_product_discount() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(VALID_CUSTOMER);
        Product product = new Product("productName", 29_00, Product.ProductCategory.STANDARD, false);
        product.putUpForSale(5,4);
        // when
        checkoutSystem.registerProduct(product);
        checkoutSystem.registerProduct(product);
        checkoutSystem.registerProduct(product);
        checkoutSystem.registerProduct(product);

        // then
        assertEquals((29_00 * 1.25) * 4, checkoutSystem.getTotal());
    }

    @Test
    void Empty_basket_show_zero_in_total() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(VALID_CUSTOMER);

        // then
        assertEquals(0, checkoutSystem.getTotal());
    }

    @Test
    void Purchase_deducts_total_from_customer() {
        // Given
        Customer customer = new Customer("Albert", "19991231-1234", 100_00, 50_00);
        CheckoutSystem checkoutSystem = new CheckoutSystem(customer);
        Product product = new Product("Chair", 10, Product.ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(product);

        // When
        checkoutSystem.checkout();

        // Then
        assertEquals(100_00 - 12_50, customer.getBankAccountBalance());
    }

    @Test
    void Purchase_with_insufficient_funds_throws_exception() {
        // Given
        Customer customer = new Customer("Broke","20001231-3456", 0, 0);
        CheckoutSystem checkoutSystem = new CheckoutSystem(customer);
        checkoutSystem.registerProduct(VALID_PRODUCT);

        // Then
        assertThrows(IllegalStateException.class, checkoutSystem::checkout);
    }


    @Test
    void Percentage_discount() { // Testfall ska möjligen ändras eller raderas?
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(VALID_CUSTOMER);
        Article article1 = new ConcreteArticle(100_00);
        Article article2 = new ConcreteArticle(50_00);
        // checkoutSystem.registerProduct(article1);
        // checkoutSystem.registerProduct(article2);

        // when
        checkoutSystem.registerDiscountCode("25");

        // then
        assertEquals(112_5, checkoutSystem.getTotal());
    }

    @Test
    void Decorator() {
        // given
        Article article1 = new ConcreteArticle(100_00);
        Article article2 = new ConcreteArticle(50_00);
        ArticleGroup articleGroup = new ArticleGroup();
        articleGroup.addArticle(article1);
        articleGroup.addArticle(article2);

        // when
        Article discountArticles = new DiscountPercentageDecorator(articleGroup, 0.25);

        // then
        assertEquals(112_5, discountArticles.getPrice());
    }

    @Test
    void Static_Membership_Discount_is_applied() {
        //given
        final Customer MEMBER_CUSTOMER = new Customer("Miriam", "19990115-2345", 15000_00, 500_00);
        MEMBER_CUSTOMER.becomeMember();

        CheckoutSystem checkoutSystem = new CheckoutSystem(MEMBER_CUSTOMER);
        Product product = new Product("productName", 100_00, Product.ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(product);

        // when

        // then
        assertEquals(99_00, checkoutSystem.getTotal());
    }
}