package org.example;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

class CheckoutSystemTest {
    private static final Customer NON_MEMBER_CUSTOMER = new Customer("Bob", "19991231-1234", 15000_00, false);
    private static final Product VALID_PRODUCT = new Product("productName", 20, Product.ProductCategory.STANDARD, false);
    static Customer getBronzeCustomer() {
        return new Customer("name", "19990101-0101", 0, true);
    }

    static Customer getSilverCustomer() {
        Customer customer = new Customer("name", "19990101-0101", 0, true);
        customer.getMembership().increasePoints(1500);
        return customer;
    }

    static Customer getGoldCustomer() {
        Customer customer = new Customer("name", "19990101-0101", 0, true);
        customer.getMembership().increasePoints(2500);
        return customer;
    }
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
    @Description("Test case: 1")
    void getTotal_non_member_standard_vat_product() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        Product product = new Product("SHIRT", 299, Product.ProductCategory.STANDARD, false);

        // when
        checkoutSystem.registerProduct(product);

        // then
        assertEquals(299*1.25, checkoutSystem.getTotal());
    }

    @Test
    @Description("Test case: 2")
    void getTotal_bronze_member_standard_vat_product() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(getBronzeCustomer());
        Product product = new Product("SHIRT", 299, Product.ProductCategory.STANDARD, false);

        // when
        checkoutSystem.registerProduct(product);

        // then
        assertEquals( (299 * ( 1 - 0.01) * 1.25), checkoutSystem.getTotal());
    }

    @Test
    @Description("Test case: 3")
    void getTotal_silver_member_standard_vat_product() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(getSilverCustomer());
        Product product = new Product("SHIRT", 299, Product.ProductCategory.STANDARD, false);

        // when
        checkoutSystem.registerProduct(product);

        // then
        assertEquals( (299 * ( 1 - 0.05) * 1.25), checkoutSystem.getTotal());
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
        Product product1 = new Product("APPLE", 50, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("APPLE", 100, Product.ProductCategory.STANDARD, false);
        Product product3 = new Product("APPLE", 50, Product.ProductCategory.STANDARD, false);
        Product product4 = new Product("APPLE", 100, Product.ProductCategory.STANDARD, false);
        Product product5 = new Product("BANANA", 100, Product.ProductCategory.STANDARD, false);
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

        assertEquals(350 * 1.25, total);
    }

    @Test
    void johantestar() {
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        Product product1 = new Product("APPLE", 50, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("APPLE", 100, Product.ProductCategory.STANDARD, false);
        Product product3 = new Product("APPLE", 75, Product.ProductCategory.STANDARD, false);
        Product product4 = new Product("APPLE", 100, Product.ProductCategory.STANDARD, false);
        Product product5 = new Product("BANANA", 100, Product.ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(product1);
        checkoutSystem.registerProduct(product2);
        checkoutSystem.registerProduct(product3);
        checkoutSystem.registerProduct(product4);
        checkoutSystem.registerProduct(product5);

        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 3, 2);

        ArrayList<Product> listBeforeSort = checkoutSystem.getBasket();
        System.out.println(listBeforeSort);

        ArrayList<Product> sorted = checkoutSystem.getListSorted(listBeforeSort);
        System.out.println(sorted);

    }


    @Test
    void Bronze_Membership_Discount_is_applied() {
        //given
        Customer customer = new Customer("Memphis", "20001231-2345", 15000_00, true);
        CheckoutSystem checkoutSystem = new CheckoutSystem(customer);
        Product product = new Product("productName", 100, Product.ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(product);

        // then
        assertEquals((100 * (1 - 0.01)) * 1.25, checkoutSystem.getTotal());
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
        assertEquals((100 * (1 - 0.02)) * 1.25, checkoutSystem.getTotal());
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
        assertEquals((100 * (1 - 0.05)) * 1.25, checkoutSystem.getTotal());
    }


}