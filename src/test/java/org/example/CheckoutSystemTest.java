package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// TODO Make compatible with latest updates
class CheckoutSystemTest {
    public static final Customer VALID_CUSTOMER = new Customer("customerName", "20001231-1234", 15000_00, 500_00);

    public static final Product VALID_PRODUCT = new Product("productName", 20_00, ProductCategory.STANDARD, false);
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
        Product product = new Product("productName", 29_00, ProductCategory.STANDARD, false);

        // when
        checkoutSystem.registerProduct(product);

        // then
        assertEquals(29_00 * 1.25, checkoutSystem.getTotal());
    }

    @Test
    void Display_checkout_sum_with_amount_reaching_product_discount() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(VALID_CUSTOMER);
        Product product = new Product("productName", 29_00, ProductCategory.STANDARD, false);
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
        Product product = new Product("productName", 29_00, ProductCategory.STANDARD, false);
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
    void Card_payment_with_enough_money() {
        // given
        CheckoutSystem checkoutSystem = new CheckoutSystem(VALID_CUSTOMER);
        Product product = VALID_PRODUCT;
        checkoutSystem.registerProduct(VALID_PRODUCT);
        Card card = new Card(100_00);

        // when
        checkoutSystem.pay(card);

        // then
        assertEquals(70_01, card.getBalance());
    }


    @Test
    void Static_Membership_Discount_is_applied() {
        //given
        final Customer MEMBER_CUSTOMER = new Customer("Miriam", "19990115-2345", 15000_00, 500_00);
        MEMBER_CUSTOMER.becomeMember();

        CheckoutSystem checkoutSystem = new CheckoutSystem(MEMBER_CUSTOMER);
        Product product = new Product("productName", 100_00, ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(product);

        // when

        // then
        assertEquals(99_00, checkoutSystem.getTotal());
    }

    @Test
    void Discount_campaign_with_even_number_of_products() {
        // given
        final Customer MEMBER_CUSTOMER = new Customer("Miriam", "19990115-2345", 15000_00, 500_00);
        CheckoutSystem checkoutSystem = new CheckoutSystem(MEMBER_CUSTOMER);
        MEMBER_CUSTOMER.becomeMember();
        ConcreteArticle article1 = new ConcreteArticle(100, ProductCategory.STANDARD);
        ConcreteArticle article2 = new ConcreteArticle(100, ProductCategory.STANDARD);
        ConcreteArticle article3 = new ConcreteArticle(100, ProductCategory.STANDARD);
        checkoutSystem.registerProduct(article1);
        checkoutSystem.registerProduct(article2);
        checkoutSystem.registerProduct(article3);
        checkoutSystem.addDiscountCampaign(new DiscountCampaign(ProductCategory.STANDARD, 3, 2));

        double total = checkoutSystem.getTotal();
        System.out.println(article1.getPriceAfterDiscounts());
        System.out.println(article2.getPriceAfterDiscounts());
        System.out.println(article3.getPriceAfterDiscounts());
        // then
        assertEquals(200 * 1.25, total);
    }

    @Test
    void Discount_campaign_with_odd_number_of_products() {
        // given
        final Customer MEMBER_CUSTOMER = new Customer("Miriam", "19990115-2345", 15000_00, 500_00);
        CheckoutSystem checkoutSystem = new CheckoutSystem(MEMBER_CUSTOMER);
        MEMBER_CUSTOMER.becomeMember();
        ConcreteArticle article1 = new ConcreteArticle(100, ProductCategory.STANDARD);
        ConcreteArticle article2 = new ConcreteArticle(100, ProductCategory.STANDARD);
        ConcreteArticle article3 = new ConcreteArticle(100, ProductCategory.STANDARD);
        ConcreteArticle article4 = new ConcreteArticle(100, ProductCategory.STANDARD);
        ConcreteArticle article5 = new ConcreteArticle(100, ProductCategory.STANDARD);
        checkoutSystem.registerProduct(article1);
        checkoutSystem.registerProduct(article2);
        checkoutSystem.registerProduct(article3);
        checkoutSystem.registerProduct(article4);
        checkoutSystem.registerProduct(article5);

        checkoutSystem.addDiscountCampaign(new DiscountCampaign(ProductCategory.STANDARD, 3, 2));

        double total = checkoutSystem.getTotal();
        System.out.println(article1.getPriceAfterDiscounts());
        System.out.println(article2.getPriceAfterDiscounts());
        System.out.println(article3.getPriceAfterDiscounts());
        System.out.println(article4.getPriceAfterDiscounts());
        System.out.println(article5.getPriceAfterDiscounts());

        assertEquals(400 * 1.25, total);
    }

    @Test
    void Discount_campaign_with_odd_number_of_products_and_different_prices() {
        // given
        final Customer MEMBER_CUSTOMER = new Customer("Miriam", "19990115-2345", 15000_00, 500_00);
        CheckoutSystem checkoutSystem = new CheckoutSystem(MEMBER_CUSTOMER);
        MEMBER_CUSTOMER.becomeMember();
        ConcreteArticle article1 = new ConcreteArticle(100, ProductCategory.STANDARD);
        ConcreteArticle article2 = new ConcreteArticle(100, ProductCategory.STANDARD);
        ConcreteArticle article3 = new ConcreteArticle(150, ProductCategory.STANDARD);
        ConcreteArticle article4 = new ConcreteArticle(100, ProductCategory.STANDARD);
        ConcreteArticle article5 = new ConcreteArticle(50, ProductCategory.STANDARD);
        checkoutSystem.registerProduct(article1);
        checkoutSystem.registerProduct(article2);
        checkoutSystem.registerProduct(article3);
        checkoutSystem.registerProduct(article4);
        checkoutSystem.registerProduct(article5);

        checkoutSystem.addDiscountCampaign(new DiscountCampaign(ProductCategory.STANDARD, 3, 2));

        double total = checkoutSystem.getTotal();
        System.out.println(article1.getPriceAfterDiscounts());
        System.out.println(article2.getPriceAfterDiscounts());
        System.out.println(article3.getPriceAfterDiscounts());
        System.out.println(article4.getPriceAfterDiscounts());
        System.out.println(article5.getPriceAfterDiscounts());

        assertEquals(450 * 1.25, total);
    }
}