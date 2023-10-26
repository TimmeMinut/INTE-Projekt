package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {
    public static final Customer NON_MEMBER_CUSTOMER = new Customer("Bob", "19991231-1234", 15000_00, false);
    private static final double STANDARD_VAT_MULTIPLIER = 1.25;
    private static final double FOOD_VAT_MULTIPLIER = 1.12;
    private static final double BOOK_VAT_MULTIPLIER = 1.06;

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

    @Test
    void Equals_same_product() {
        // given
        Product product1 = new Product("Coke", 12, Product.ProductCategory.FOOD, true);
        Product product2 = product1; // product2 pekar på samma objekt som product1

        // when && then
        assertEquals(product1, product2);
    }

    @Test
    void Equals_identical_products() {
        // given
        Product product1 = new Product("Coke", 12, Product.ProductCategory.FOOD, true);
        Product product2 = new Product("Coke", 12, Product.ProductCategory.FOOD, true);

        // when && then
        assertEquals(product1, product2);
    }

    @Test
    void Equals_different_products() {
        // given
        Product product1 = new Product("Coke", 12, Product.ProductCategory.FOOD, true);
        Product product2 = new Product("Pepsi", 12, Product.ProductCategory.FOOD, true);

        // when && then
        Assertions.assertNotEquals(product1, product2);
    }

    @Test
    void Equals_null_product() {
        // given
        Product product1 = new Product("Coke", 12, Product.ProductCategory.FOOD, true);

        // when && then
        Assertions.assertNotEquals(product1, null);
    }

    @Test
    void Equals_different_types() {
        // given
        Product product1 = new Product("Coke", 12, Product.ProductCategory.FOOD, true);
        String someString = "Not a Product";

        // when && then
        Assertions.assertNotEquals(product1, someString);
    }

    @Test
    void Apply_positive_discount(){
        // given
        Product product = new Product("Soda", 10, Product.ProductCategory.FOOD, true);
        double validDiscount = 2;

        //when
        product.setDiscountAmount(validDiscount);

        //then
        assertEquals(2, product.getDiscountAmount());
    }
    @Test
    void Apply_negative_discount(){
        // given
        Product product = new Product("Soda", 10, Product.ProductCategory.FOOD, true);
        double invalidDiscount = -3;

        //when && then
        assertThrows(IllegalArgumentException.class, () -> product.setDiscountAmount(invalidDiscount));
    }

    @Test
    void Apply_maximum_discount(){
        // given
        Product product = new Product("Soda", 10, Product.ProductCategory.FOOD, true);
        double validDiscount = 10;

        //when && then
        assertEquals(0, product.getDiscountAmount());
    }

    @Test
    void Apply_discount_higher_than_price(){
        // given
        Product product = new Product("Soda", 10, Product.ProductCategory.FOOD, true);
        double invalidDiscount = 45;

        //when && then
        assertThrows(IllegalArgumentException.class, () -> product.setDiscountAmount(invalidDiscount));
    }

    @Test
    void Get_price_after_setting_discount_amount() {
        // given
        Product product = new Product("Coke", 12, Product.ProductCategory.FOOD, true);
        double discount = 3;
        double expectedPriceAfterDiscount = (12 - discount) * (FOOD_VAT_MULTIPLIER) + 2;

        // when
        product.setDiscountAmount(discount);

        // then
        assertEquals(expectedPriceAfterDiscount, product.getPriceAfterDiscount(), 0.000001);
    }
}








