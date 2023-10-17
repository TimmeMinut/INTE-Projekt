package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

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
    void calculatePriceExcludingDeposit() {
        Product product = new Product("Twix", 12, Product.ProductCategory.FOOD, false);

        assertEquals(12 * 1.12, product.getPrice(), 0.000001);
    }

    @Test
    void Product_is_put_up_for_sale() {
        //given
        Product product = new Product("pen", 17, Product.ProductCategory.STANDARD, false);
        Customer customer = new Customer("Miriam", "19990115-2345", 15000_00, 500_00);
        CheckoutSystem checkoutSystem = new CheckoutSystem(customer);

        //when
        product.putUpForSale(3, 2); // Pick 3 pay for 2
        for (int i = 0; i < 3; i++) {
            checkoutSystem.registerProduct(product); // Add 3 pens to basket
        }


        //then
        assertEquals((17 * 1.25) * 2, checkoutSystem.getTotal());
    }


    @Test
    void ProductCategory_is_put_for_sale() {

    }

    @Test
    void Product_sale_is_taken_down() {

    }

    @Test
    void ProductCategory_sale_is_taken_down() {

    }
}








