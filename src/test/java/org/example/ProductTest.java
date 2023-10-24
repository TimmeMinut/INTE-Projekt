package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {
    public static final Customer NON_MEMBER_CUSTOMER = new Customer("Bob", "19991231-1234", 15000_00, false);

    @Test
    void VAT_is_calculated_on_books() {
        // given
        Product product = new Product("journal", 25, Product.ProductCategory.BOOK, true);

        // when && then
        assertEquals(25 * 0.06, product.getVATValue()); //
    }

    @Test
    void VAT_is_calculated_on_food() {
        // given
        Product product = new Product("Twix", 12, Product.ProductCategory.FOOD, true);

        // when && then
        assertEquals(12 * 0.12, product.getVATValue());
    }

    @Test
    void VAT_is_calculated_on_standard() {
        // given
        Product product = new Product("pen", 17, Product.ProductCategory.STANDARD, true);

        // when && then
        assertEquals(17 * 0.25, product.getVATValue());
    }

    @Test
    void Calculate_price_including_deposit() {
        Product product = new Product("Coke", 12, Product.ProductCategory.FOOD, true);

        assertEquals(12 * 1.12 + 2, product.getPrice(), 0.000001);
    }

    @Test
    void CalculatePriceExcludingDeposit() {
        Product product = new Product("Twix", 12, Product.ProductCategory.FOOD, false);

        assertEquals(12 * 1.12, product.getPrice(), 0.000001);
    }
}








