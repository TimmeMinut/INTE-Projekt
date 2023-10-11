package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    @Test
    void VAT_is_calculated_on_books() {
        // given
        Product product = new Product("journal", 25, Product.ProductCategory.BOOK, true);

        // when && then
        assertEquals(25 * 1.06, product.getVATValue());
    }

    @Test
    void VAT_is_calculated_on_food() {
        // given
        Product product = new Product("Twix", 12, Product.ProductCategory.FOOD, true);

        // when && then
        assertEquals(12 * 1.12, product.getVATValue());
    }

    @Test
    void VAT_is_calculated_on_standard() {
        // given
        Product product = new Product("pen", 17, Product.ProductCategory.STANDARD, true);

        // when && then
        assertEquals(17 * 1.25, product.getVATValue());
    }

    @Test
    void calculatePriceIncludingDeposit(){
        Product product = new Product("Coke", 12, Product.ProductCategory.FOOD, true);

        assertEquals(12 + (12 * 1.12) + 2, product.getPrice());
    }

    @Test
    void calculatePriceExcludingDeposit(){
        Product product = new Product("Twix", 12, Product.ProductCategory.FOOD, false);

        assertEquals(12 + (12 * 1.12), product.getPrice());
    }
    
    @Test
    void Product_is_put_up_for_sale(){
        //given
        Product product = new Product("pen", 17, Product.ProductCategory.STANDARD, true);
        CheckoutSystem checkoutSystem = new CheckoutSystem();

        //when
        product.putUpForSale(3, 2); //Pick 3 pay for 2
        for (int i = 0; i <3; i++){
            checkoutSystem.addProduct(product); //Add 3 pens to basket
        }

        //then
        assertEquals(17 * 2, checkoutSystem.getTotalPrice());
    }


    @Test
    void ProductCategory_is_put_for_sale(){

    }

    @Test
    void Product_sale_is_taken_down(){

    }

    @Test
    void ProductCategory_sale_is_taken_down(){

    }
}








