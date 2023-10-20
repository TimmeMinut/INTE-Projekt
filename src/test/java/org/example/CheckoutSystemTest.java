package org.example;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

class CheckoutSystemTest {
    private static final Customer NON_MEMBER_CUSTOMER = new Customer("Bob", "19991231-1234", 15000_00, false);
    private static final double BRONZE_DISCOUNT_MULTIPLIER = 1.01;
    private static final double SILVER_DISCOUNT_MULTIPLIER = 1.02;
    private static final double GOLD_DISCOUNT_MULTIPLIER = 1.05;

    private static final double BOOK_VAT_MULTIPLIER = 1.06;
    private static final double FOOD_VAT_MULTIPLIER = 1.12;
    private static final double STANDARD_VAT_MULTIPLIER = 1.25;




    private static final Product VALID_PRODUCT = new Product("productName", 20, Product.ProductCategory.STANDARD, false);

    @Test
    void Product_is_added_to_basket() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        Product product = VALID_PRODUCT;

        // when
        checkoutSystem.registerProduct(product);

        // then
        assertEquals(product, checkoutSystem.getProduct("productName"));
    }


    @Test
    void Existing_product_is_removed_from_basket() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        Product product = VALID_PRODUCT;
        checkoutSystem.registerProduct(product);

        // when
        checkoutSystem.removeProduct(VALID_PRODUCT);

        // then
        assertFalse(checkoutSystem.basketContains(product));
    }

    @Test
    void Removing_non_existing_product_from_basket_throws_exception() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);

        // then
        assertThrows(IllegalArgumentException.class, () -> checkoutSystem.removeProduct(VALID_PRODUCT));
    }

    @Test
    void Display_checkout_sum_with_product_registered() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        Product product = new Product("productName", 29, Product.ProductCategory.STANDARD, false);

        // when
        checkoutSystem.registerProduct(product);

        // then
        assertEquals(29 * 1.25, checkoutSystem.getTotal());
    }

    /*@Test
    void Display_checkout_sum_with_amount_reaching_product_discount() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        Product product = new Product("productName", 29_00, Product.ProductCategory.STANDARD, false);
        product.putUpForSale(3, 2);
        // when
        checkoutSystem.registerProduct(product);
        checkoutSystem.registerProduct(product);
        checkoutSystem.registerProduct(product);

        // then
        assertEquals((29 * 1.25) * 2, checkoutSystem.getTotal());
    }*/

    /*@Test
    void Display_checkout_sum_with_amount_not_reaching_product_discount() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        Product product = new Product("productName", 29_00, Product.ProductCategory.STANDARD, false);
        product.putUpForSale(5, 4);
        // when
        checkoutSystem.registerProduct(product);
        checkoutSystem.registerProduct(product);
        checkoutSystem.registerProduct(product);
        checkoutSystem.registerProduct(product);

        // then
        assertEquals((29_00 * 1.25) * 4, checkoutSystem.getTotal());
    }*/

    @Test
    void Empty_basket_show_zero_in_total() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);

        // then
        assertEquals(0, checkoutSystem.getTotal());
    }

    @Test
    void Purchase_deducts_total_from_customer() {
        // Given
        Customer customer = new Customer("Albert", "19991231-1234", 100_00, false);
        Product product = new Product("Chair", 10, Product.ProductCategory.STANDARD, false);
        CheckoutSystem checkoutSystem = new CheckoutSystem(customer);
        checkoutSystem.registerProduct(product);

        // When
        checkoutSystem.checkout();

        // Then
        assertEquals(100_00 - 12_50, customer.getBankAccountBalance());
    }

    @Test
    void Payment_with_insufficient_funds_is_declined() {
        // Given
        Customer customer = new Customer("Albert", "19991231-1234", 0, false);
        CheckoutSystem checkoutSystem = new CheckoutSystem(customer);
        checkoutSystem.registerProduct(VALID_PRODUCT);


        // Then
        assertThrows(IllegalStateException.class, () -> customer.pay(checkoutSystem.getTotal()));
    }

    @Test
    void Customer_can_try_again_after_failed_payment() {
        // Given
        Customer customer = new Customer("Albert", "19991231-1234", 0, false);
        CheckoutSystem checkoutSystem = new CheckoutSystem(customer);
        checkoutSystem.registerProduct(VALID_PRODUCT);
        checkoutSystem.checkout();

        // When
        customer.addMoney(100_00);
        checkoutSystem.checkout();

        // Then
        assertEquals(100_00 - 25_00, customer.getBankAccountBalance());
    }

    @Test
    void Successful_purchase_empties_basket() {
        // Given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        checkoutSystem.registerProduct(VALID_PRODUCT);

        // When
        checkoutSystem.checkout();

        // Then
        assertEquals(0, checkoutSystem.getBasketSize());
    }

    @Test
    void Discount_campaign_with_even_number_of_same_products() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        Product product1 = new Product("APPLE", 100, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("APPLE", 100, Product.ProductCategory.STANDARD, false);
        Product product3 = new Product("APPLE", 100, Product.ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(product1);
        checkoutSystem.registerProduct(product2);
        checkoutSystem.registerProduct(product3);
        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 3, 2);

        double total = checkoutSystem.getTotal();
        System.out.println(product1.getPriceAfterDiscounts());
        System.out.println(product2.getPriceAfterDiscounts());
        System.out.println(product3.getPriceAfterDiscounts());
        // then
        assertEquals(200 * 1.25, total);
    }

    @Test
    void Discount_campaign_with_odd_number_of_products() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        Product product1 = new Product("APPLE", 100, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("APPLE", 100, Product.ProductCategory.STANDARD, false);
        Product product3 = new Product("APPLE", 100, Product.ProductCategory.STANDARD, false);
        Product product4 = new Product("APPLE", 100, Product.ProductCategory.STANDARD, false);
        Product product5 = new Product("APPLE", 100, Product.ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(product1);
        checkoutSystem.registerProduct(product2);
        checkoutSystem.registerProduct(product3);
        checkoutSystem.registerProduct(product4);
        checkoutSystem.registerProduct(product5);

        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 3, 2);

        double total = checkoutSystem.getTotal();
        System.out.println(product1.getPriceAfterDiscounts());
        System.out.println(product2.getPriceAfterDiscounts());
        System.out.println(product3.getPriceAfterDiscounts());
        System.out.println(product4.getPriceAfterDiscounts());
        System.out.println(product5.getPriceAfterDiscounts());

        assertEquals(400 * 1.25, total);
    }

    @Test
    void Discount_campaign_with_odd_number_of_products_and_different_prices() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        // olika pris
        Product product1 = new Product("APPLE", 100, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("APPLE", 100, Product.ProductCategory.STANDARD, false);
        Product product3 = new Product("APPLE", 100, Product.ProductCategory.STANDARD, false);
        Product product4 = new Product("APPLE", 100, Product.ProductCategory.STANDARD, false);
        Product product5 = new Product("BANANA", 50, Product.ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(product1);
        checkoutSystem.registerProduct(product2);
        checkoutSystem.registerProduct(product3);
        checkoutSystem.registerProduct(product4);
        checkoutSystem.registerProduct(product5);

        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 3, 2);

        double total = checkoutSystem.getTotal();
        System.out.println(product1.getPriceAfterDiscounts());
        System.out.println(product2.getPriceAfterDiscounts());
        System.out.println(product3.getPriceAfterDiscounts());
        System.out.println(product4.getPriceAfterDiscounts());
        System.out.println(product5.getPriceAfterDiscounts());

        assertEquals(400 * 1.25, total);
    }

    @Test
    void Discount_campaign_take_X_pay_X() {
        // Vad händer om Ta 1 Betala för 1

        // Given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);

        // Then
        assertThrows(IllegalArgumentException.class, () -> checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 1, 1));
    }

    @Test
    void Discount_campaign_take_smaller_than_pay() {
        // Given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);

        // Then
        assertThrows(IllegalArgumentException.class, () -> checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 1, 2));
    }

    @Test
    void Bronze_Membership_Discount_is_applied() {
        //given
        Customer customer = new Customer("Memphis", "20001231-2345", 15000_00, true);
        CheckoutSystem checkoutSystem = new CheckoutSystem(customer);
        Product product = new Product("productName", 100, Product.ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(product);

        // then
        assertEquals((100 * (BRONZE_DISCOUNT_MULTIPLIER)) * 1.25, checkoutSystem.getTotal());
    }

    @Test
    void Silver_Membership_Discount_is_applied() {
        //given
        Customer customer = new Customer("Memphis", "20001231-2345", 15000_00, true);
        Membership membership = customer.getMembership();
        membership.increasePoints(1000); // To silver

        Product product = new Product("productName", 100, Product.ProductCategory.STANDARD, false);

        CheckoutSystem checkoutSystem = new CheckoutSystem(customer);
        checkoutSystem.registerProduct(product);


        // then
        assertEquals((100 * (SILVER_DISCOUNT_MULTIPLIER)) * 1.25, checkoutSystem.getTotal());
    }


    @Test
    void Gold_Membership_Discount_is_applied() {
        //given
        Customer customer = new Customer("Memphis", "20001231-2345", 15000_00, true);
        Membership membership = customer.getMembership();
        membership.increasePoints(2000); // To gold

        Product product = new Product("productName", 100, Product.ProductCategory.STANDARD, false);

        CheckoutSystem checkoutSystem = new CheckoutSystem(customer);
        checkoutSystem.registerProduct(product);


        // then
        assertEquals((100 * (GOLD_DISCOUNT_MULTIPLIER)) * STANDARD_VAT_MULTIPLIER, checkoutSystem.getTotal());
    }


    @Test
    @Description("Test Case: 14")
    void Lowest_price_product_is_deducted() {
        // nm 3f2 + 4f3 st + f rätt produkt rabatteras k6
        // (K6) Olika priser inom samma kategori - varan med lägst pris rabatteras
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 3, 2);
        checkoutSystem.addDiscountCampaign(Product.ProductCategory.FOOD, 4, 3);

        Product[] products = new Product[7];

        products[0] = new Product("Ruler", 15, Product.ProductCategory.STANDARD, false);
        products[1] = new Product("Pen", 10, Product.ProductCategory.STANDARD, false);
        products[2] = new Product("Eraser", 5, Product.ProductCategory.STANDARD, false);

        products[3] = new Product("Juice", 8, Product.ProductCategory.FOOD, false);
        products[4] = new Product("Apple", 6, Product.ProductCategory.FOOD, false);
        products[5] = new Product("Milk", 4, Product.ProductCategory.FOOD, false);
        products[6] = new Product("Gum", 2, Product.ProductCategory.FOOD, false);

        for (int i = 0; i < 7; i++) {
            checkoutSystem.registerProduct(products[i]);
        }

        double total = checkoutSystem.getTotal();
        // Expected 51.41
        assertEquals(((15 + 10) * STANDARD_VAT_MULTIPLIER) + ((8 + 6 + 4) * FOOD_VAT_MULTIPLIER), total, 0.000001);
    }

    @Test
    void ekv_15() {
        // nm 3f2 + 4f3 + 5f3 st + b + f  rätt produkt rabatteras K8
    }

    @Test
    void ekv_16() {
        // nm - st total 0 P0
    }

    @Test
    void ekv_17() {
        // g 3f2 s g > 3f2 VS1
    }

    @Test
    void ekv_18() {
        // g 3f2 s g <3f2 VS2
    }

    @Test
    void If_both_discounts_are_equal_only_one_is_applied_19() {
        // Med VATExclusive 50 och 20 st produkter ger både ta 20 betala 19 och 5% membership 50 i discount

        // Given
        Customer customer = new Customer("Memphis", "20001231-2345", 15000_00, true);
        customer.getMembership().increasePoints(2000); // To gold
        CheckoutSystem checkoutSystem = new CheckoutSystem(customer);
        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 20, 19);

        Product product1 = new Product("APPLE", 50, Product.ProductCategory.STANDARD, false);
        for (int i = 0; i < 20; i++) {
            checkoutSystem.registerProduct(product1);
        }

        // Then
        assertEquals(950 * 1.25, checkoutSystem.getTotal());
    }

}