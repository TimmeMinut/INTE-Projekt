package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    @Test
    void VAT_is_calculated_on_books() {
        // given
        Product product = new Product("journal", 25_00, ProductCategory.BOOK, true);

        // when && then
        assertEquals(25_00 * 0.06, product.getVATValue()); //
    }

    @Test
    void VAT_is_calculated_on_food() {
        // given
        Product product = new Product("Twix", 12_00, ProductCategory.FOOD, true);

        // when && then
        assertEquals(12_00 * 0.12, product.getVATValue());
    }

    @Test
    void VAT_is_calculated_on_standard() {
        // given
        Product product = new Product("pen", 17_00, ProductCategory.STANDARD, true);

        // when && then
        assertEquals(17_00 * 0.25, product.getVATValue());
    }

    @Test
    void calculatePriceIncludingDeposit() {
        Product product = new Product("Coke", 12_00, ProductCategory.FOOD, true);

        assertEquals(12_00 * 1.12 + 2_00, product.getPrice(), 0.000001);
    }

    @Test
    void calculatePriceExcludingDeposit() {
        Product product = new Product("Twix", 12_00, ProductCategory.FOOD, false);

        assertEquals(12_00 * 1.12, product.getPrice(), 0.000001);
    }

    @Test
    void Product_is_put_up_for_sale() {
        //given
        Product product = new Product("pen", 17_00, ProductCategory.STANDARD, true);
        Customer customer = new Customer("Miriam", "19990115-2345", 15000_00, 500_00);
        CheckoutSystem checkoutSystem = new CheckoutSystem(customer);

        //when
        product.putUpForSale(3, 2); // Pick 3 pay for 2
        for (int i = 0; i < 3; i++) {
            checkoutSystem.registerProduct(product); // Add 3 pens to basket
        }


        //then
        assertEquals(23_25 * 2, checkoutSystem.getTotal());
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








