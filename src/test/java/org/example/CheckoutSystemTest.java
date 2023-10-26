package org.example;

import jdk.jfr.Description;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class CheckoutSystemTest {

    static final double TOLERANCE = 0.00001;
    private static final Customer NON_MEMBER_CUSTOMER = new Customer("Bob", "19991231-1234", 15000_00, false);
    private static final Product VALID_PRODUCT = new Product("productName", 20, Product.ProductCategory.STANDARD, false);
    private static final double BRONZE_DISCOUNT_MULTIPLIER = 0.99;
    private static final double SILVER_DISCOUNT_MULTIPLIER = 0.98;
    private static final double GOLD_DISCOUNT_MULTIPLIER = 0.95;
    private static final double STANDARD_VAT_MULTIPLIER = 1.25;
    private static final double FOOD_VAT_MULTIPLIER = 1.12;
    private static final double BOOK_VAT_MULTIPLIER = 1.06;

    static Customer getBronzeCustomer() {
        return new Customer("name", "19990101-0101", 15000_00, true);
    }

    static Customer getSilverCustomer() {
        Customer customer = new Customer("name", "19990101-0101", 15000_00, true);
        customer.getMembership().increasePoints(1500);
        return customer;
    }

    static Customer getGoldCustomer() {
        Customer customer = new Customer("name", "19990101-0101", 15000_00, true);
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
        assertEquals(product, checkoutSystem.getProduct(product));
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
        checkoutSystem.checkoutByCard();

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
        assertThrows(IllegalStateException.class, () -> customer.payByCard(checkoutSystem.getTotal()));
    }

    @Test
    void Customer_can_try_again_after_failed_payment() {
        // Given
        Customer customer = new Customer("Albert", "19991231-1234", 0, false);
        CheckoutSystem checkoutSystem = new CheckoutSystem(customer);
        checkoutSystem.registerProduct(VALID_PRODUCT);
        checkoutSystem.checkoutByCard();

        // When
        customer.addMoney(100_00);
        checkoutSystem.checkoutByCard();

        // Then
        assertEquals(100_00 - 25_00, customer.getBankAccountBalance());
    }

    @Test
    void Successful_purchase_empties_basket() {
        // Given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        checkoutSystem.registerProduct(VALID_PRODUCT);

        // When
        checkoutSystem.checkoutByCard();

        // Then
        assertEquals(0, checkoutSystem.getBasketSize());
    }

    @Test
    void addDiscountCampaign() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        boolean campaignAdded = false;

        //when
        checkoutSystem.addDiscountCampaign(Product.ProductCategory.BOOK, 3,2);

        for (Map.Entry<Product.ProductCategory, Pair<Integer, Integer>> entry : checkoutSystem.getDiscountCampaigns().entrySet()) {
            if (entry.getKey().equals(Product.ProductCategory.BOOK) && entry.getValue().equals(Pair.of(3, 2))) {
                campaignAdded = true;
                break;
            }
        }
        //then
        assertTrue(campaignAdded);
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
        assertEquals(299 * STANDARD_VAT_MULTIPLIER, checkoutSystem.getTotal());
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
        assertEquals(299 * BRONZE_DISCOUNT_MULTIPLIER * STANDARD_VAT_MULTIPLIER, checkoutSystem.getTotal());
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
        assertEquals(299 * SILVER_DISCOUNT_MULTIPLIER * STANDARD_VAT_MULTIPLIER, checkoutSystem.getTotal());
    }

    @Test
    @Description("Test case: 4")
    void getTotal_gold_member_standard_vat_product() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(getGoldCustomer());
        Product product = new Product("SHIRT", 299, Product.ProductCategory.STANDARD, false);

        // when
        checkoutSystem.registerProduct(product);

        // then
        assertEquals(299 * GOLD_DISCOUNT_MULTIPLIER * STANDARD_VAT_MULTIPLIER, checkoutSystem.getTotal());
    }

    @Test
    @Description("Test case: 5")
    void get_Total_non_member_book_vat_product() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        Product product = new Product("BOOK", 98, Product.ProductCategory.BOOK, false);

        // when
        checkoutSystem.registerProduct(product);

        // then
        assertEquals(98 * BOOK_VAT_MULTIPLIER, checkoutSystem.getTotal(), TOLERANCE);
    }

    @Test
    @Description("Test case: 6")
    void get_Total_non_member_food_vat_product() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        Product product = new Product("APPLE", 1.99, Product.ProductCategory.FOOD, false);

        // when
        checkoutSystem.registerProduct(product);

        // then
        assertEquals(1.99 * FOOD_VAT_MULTIPLIER, checkoutSystem.getTotal());
    }

    @Test
    @Description("Test case: 7")
    void get_Total_buy3_pay_for2_exact_number_of_products() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        Product product1 = new Product("SHIRT", 299, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("SHIRT", 299, Product.ProductCategory.STANDARD, false);
        Product product3 = new Product("SHIRT", 299, Product.ProductCategory.STANDARD, false);

        // when
        checkoutSystem.registerProduct(product1);
        checkoutSystem.registerProduct(product2);
        checkoutSystem.registerProduct(product3);

        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 3, 2);

        // then
        assertEquals(2 * 299 * STANDARD_VAT_MULTIPLIER, checkoutSystem.getTotal());
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
        System.out.println(product1.getVATExclusiveAfterDiscounts());
        System.out.println(product2.getVATExclusiveAfterDiscounts());
        System.out.println(product3.getVATExclusiveAfterDiscounts());
        // then
        assertEquals(200 * 1.25, total);
    }

    @Test
    @Description("ID 8")
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

        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 5, 3);

        double total = checkoutSystem.getTotal();
        System.out.println(product1.getVATExclusiveAfterDiscounts());
        System.out.println(product2.getVATExclusiveAfterDiscounts());
        System.out.println(product3.getVATExclusiveAfterDiscounts());
        System.out.println(product4.getVATExclusiveAfterDiscounts());
        System.out.println(product5.getVATExclusiveAfterDiscounts());

        assertEquals(300 * 1.25, total);
    }

    @Test
    @Description("ID 13")
    void Discount_campaign_with_different_prices() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        // olika pris
        Product product1 = new Product("APPLE", 100, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("BANANA", 50, Product.ProductCategory.STANDARD, false);
        Product product3 = new Product("APPLE", 100, Product.ProductCategory.STANDARD, false);

        checkoutSystem.registerProduct(product1);
        checkoutSystem.registerProduct(product2);
        checkoutSystem.registerProduct(product3);


        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 3, 2);

        double total = checkoutSystem.getTotal();
        System.out.println(product1.getVATExclusiveAfterDiscounts());
        System.out.println(product2.getVATExclusiveAfterDiscounts());
        System.out.println(product3.getVATExclusiveAfterDiscounts());


        assertEquals(200 * 1.25, total);
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
    @Description("ID 9")
    void Discount_campaign_with_not_enough_products_to_be_applied() {
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        // olika pris
        Product product1 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);

        checkoutSystem.registerProduct(product1);
        checkoutSystem.registerProduct(product2);

        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 3, 2);

        assertEquals(20 * 1.25, checkoutSystem.getTotal());
    }

    @Test
    @Description("ID 10")
    void Discount_campaign_odd_number() {
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        // olika pris
        Product product1 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);
        Product product3 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);
        Product product4 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);


        checkoutSystem.registerProduct(product1);
        checkoutSystem.registerProduct(product2);
        checkoutSystem.registerProduct(product3);
        checkoutSystem.registerProduct(product4);


        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 3, 2);
        assertEquals(30 * 1.25, checkoutSystem.getTotal());

    }

    @Test
    @Description("ID 11")
    void Discount_campaign_reached_two_times() {
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        // olika pris
        Product product1 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);
        Product product3 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);
        Product product4 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);
        Product product5 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);
        Product product6 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);

        checkoutSystem.registerProduct(product1);
        checkoutSystem.registerProduct(product2);
        checkoutSystem.registerProduct(product3);
        checkoutSystem.registerProduct(product4);
        checkoutSystem.registerProduct(product5);
        checkoutSystem.registerProduct(product6);

        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 3, 2);
        assertEquals(40 * 1.25, checkoutSystem.getTotal());
    }

    @Test
    @Description("ID 12")
    void Discount_campaign_reached_two_times_with_odd_number() {
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        // olika pris
        Product product1 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);
        Product product3 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);
        Product product4 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);
        Product product5 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);
        Product product6 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);
        Product product7 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);
        Product product8 = new Product("PEN", 10, Product.ProductCategory.STANDARD, false);

        checkoutSystem.registerProduct(product1);
        checkoutSystem.registerProduct(product2);
        checkoutSystem.registerProduct(product3);
        checkoutSystem.registerProduct(product4);
        checkoutSystem.registerProduct(product5);
        checkoutSystem.registerProduct(product6);
        checkoutSystem.registerProduct(product7);
        checkoutSystem.registerProduct(product8);


        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 3, 2);
        assertEquals(60 * 1.25, checkoutSystem.getTotal());
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
    @Description("Test case 15")
    void Lowest_price_products_are_deducted_multiple_campaigns() {
        // (K8) >1 kampanj med kombinationer av K1-K6

        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 3, 2);
        checkoutSystem.addDiscountCampaign(Product.ProductCategory.BOOK, 4, 3);
        checkoutSystem.addDiscountCampaign(Product.ProductCategory.FOOD, 5, 3);

        Product[] products = new Product[15];

        // 7 Products. Take 3 Pay 2
        // 2 Cheapest should be discounted
        products[0] = new Product("Cup", 80, Product.ProductCategory.STANDARD, false);
        products[1] = new Product("Cup", 80, Product.ProductCategory.STANDARD, false);
        products[2] = new Product("Cup", 80, Product.ProductCategory.STANDARD, false);
        products[3] = new Product("Bottle", 100, Product.ProductCategory.STANDARD, false);
        products[4] = new Product("Bottle", 100, Product.ProductCategory.STANDARD, false);
        products[5] = new Product("Chair", 50, Product.ProductCategory.STANDARD, false);
        products[6] = new Product("Watch", 120, Product.ProductCategory.STANDARD, false);

        // 5 Products. Take 4 pay 3
        // Cheapest should be discounted
        products[7] = new Product("Pamphlet", 20, Product.ProductCategory.BOOK, false);
        products[8] = new Product("CookBook", 50, Product.ProductCategory.BOOK, false);
        products[9] = new Product("CookBook", 50, Product.ProductCategory.BOOK, false);
        products[10] = new Product("FantasyBook", 60, Product.ProductCategory.BOOK, false);
        products[11] = new Product("Magazine", 20, Product.ProductCategory.BOOK, false);

        // 3 Products. Take 5 pay 3
        // None should be discounted
        products[12] = new Product("Apple", 10, Product.ProductCategory.FOOD, false);
        products[13] = new Product("Apple", 10, Product.ProductCategory.FOOD, false);
        products[14] = new Product("Pear", 5, Product.ProductCategory.FOOD, false);

        for (int i = 0; i < 15; i++) {
            checkoutSystem.registerProduct(products[i]);
        }

        double total = checkoutSystem.getTotal();

        assertEquals((80 + 80 + 100 + 100 + 120) * STANDARD_VAT_MULTIPLIER +
                        (20 + 50 + 50 + 60) * BOOK_VAT_MULTIPLIER +
                        (10 + 10 + 5) * FOOD_VAT_MULTIPLIER
                , total);
    }

    @Test
    @Description("Test case 16")
    void Empty_basket_returns_0_total() {
        // Given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        double total = checkoutSystem.getTotal();

        // Then
        assertEquals(0, total);
    }

    @Test
    @Description("Test case 17")
    void Membership_discount_is_applied_if_largest() {
        CheckoutSystem checkoutSystem = new CheckoutSystem(getGoldCustomer());
        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 3, 2);

        Product product1 = new Product("Bottle", 100, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("Bottle", 100, Product.ProductCategory.STANDARD, false);
        Product product3 = new Product("Eraser", 5, Product.ProductCategory.STANDARD, false);

        checkoutSystem.registerProduct(product1);
        checkoutSystem.registerProduct(product2);
        checkoutSystem.registerProduct(product3);

        double total = checkoutSystem.getTotal();

        assertEquals(((205 * GOLD_DISCOUNT_MULTIPLIER) * STANDARD_VAT_MULTIPLIER), total);
    }

    @Test
    @Description("Test case 18")
    void Campaign_discount_is_applied_if_largest() {
        // g 3f2 s g <3f2 VS2

        CheckoutSystem checkoutSystem = new CheckoutSystem(getGoldCustomer());
        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 3, 2);

        Product[] products = new Product[3];

        products[0] = new Product("Bottle", 100, Product.ProductCategory.STANDARD, false);
        products[1] = new Product("Bottle", 100, Product.ProductCategory.STANDARD, false);
        products[2] = new Product("Bottle", 100, Product.ProductCategory.STANDARD, false);

        for (int i = 0; i < 3; i++) {
            checkoutSystem.registerProduct(products[i]);
        }

        double total = checkoutSystem.getTotal();

        assertEquals(200 * STANDARD_VAT_MULTIPLIER, total);
    }

    @Test
    @Description("Test case 19")
    void If_both_discounts_are_equal_only_one_is_applied() {
        // Med VATExclusive 50 och 20 st produkter ger både ta 20 betala 19 och 5% membership 50 i discount

        // Given
        Customer customer = getGoldCustomer();
        CheckoutSystem checkoutSystem = new CheckoutSystem(customer);
        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 20, 19);

        Product product1 = new Product("APPLE", 50, Product.ProductCategory.STANDARD, false);
        for (int i = 0; i < 20; i++) {
            checkoutSystem.registerProduct(product1);
        }

        // Then
        assertEquals(950 * 1.25, checkoutSystem.getTotal());
    }


    @Test
    @Description("Test case: 20")
    void getTotal_removing_product_after_campaigns_are_applied() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        Product product1 = new Product("SHIRT1", 299, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("SHIRT2", 299, Product.ProductCategory.STANDARD, false);
        Product product3 = new Product("SHIRT3", 299, Product.ProductCategory.STANDARD, false);
        Product product4 = new Product("SHIRT4", 299, Product.ProductCategory.STANDARD, false);
        Product product5 = new Product("SHIRT5", 299, Product.ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(product1);
        checkoutSystem.registerProduct(product2);
        checkoutSystem.registerProduct(product3);
        checkoutSystem.registerProduct(product4);
        checkoutSystem.registerProduct(product5);

        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 5, 3);

        // when
        checkoutSystem.getTotal();
        checkoutSystem.removeProduct(product3);

        // then
        assertEquals(4 * 299 * STANDARD_VAT_MULTIPLIER, checkoutSystem.getTotal());
    }

    @Test
    @Description("Test case: 21")
    void getTotal_removing_and_registering_product_after_campaigns_are_applied() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        Product product1 = new Product("SHIRT1", 299, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("SHIRT2", 299, Product.ProductCategory.STANDARD, false);
        Product product3 = new Product("SHIRT3", 299, Product.ProductCategory.STANDARD, false);
        Product product4 = new Product("SHIRT4", 299, Product.ProductCategory.STANDARD, false);
        Product product5 = new Product("SHIRT5", 299, Product.ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(product1);
        checkoutSystem.registerProduct(product2);
        checkoutSystem.registerProduct(product3);
        checkoutSystem.registerProduct(product4);
        checkoutSystem.registerProduct(product5);

        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 5, 3);

        // when
        checkoutSystem.getTotal();
        checkoutSystem.removeProduct(product3);
        checkoutSystem.getTotal();
        checkoutSystem.registerProduct(product3);

        // then
        assertEquals(3 * 299 * STANDARD_VAT_MULTIPLIER, checkoutSystem.getTotal());
    }


    @Test
    void no_discountCampaigns() {
        // Given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        Product product1 = new Product("productName", 100, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("productName", 100, Product.ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(product1);
        checkoutSystem.registerProduct(product2);

        double total = checkoutSystem.getTotal();

        assertEquals(200 * STANDARD_VAT_MULTIPLIER, total);
    }

    @Test
    void discountCampaigns_but_no_discounted_products() {
        // Given
        CheckoutSystem checkoutSystem = new CheckoutSystem(NON_MEMBER_CUSTOMER);
        checkoutSystem.addDiscountCampaign(Product.ProductCategory.BOOK, 2, 1);

        Product product1 = new Product("productName", 100, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("productName", 100, Product.ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(product1);
        checkoutSystem.registerProduct(product2);

        double total = checkoutSystem.getTotal();

        assertEquals(200 * STANDARD_VAT_MULTIPLIER, total);
    }

}