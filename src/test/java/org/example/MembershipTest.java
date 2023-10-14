package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class MembershipTest {
    public static final Customer VALID_CUSTOMER = new Customer("Bob", "20001231-1234", 15000_00, 500_00);
    public static final LocalDateTime VALID_DATE_TIME = LocalDateTime.of(2023, 1, 31, 15, 30);
    public static final CheckoutSystem VALID_CHECKOUT_SYSTEM = new CheckoutSystem(VALID_CUSTOMER);
    public static final Product VALID_PRODUCT = new Product("productName", 20_00, Product.ProductCategory.STANDARD, false);


    @Test
    void Membership_is_created_for_correct_customer() {
        // given
        Customer customer = VALID_CUSTOMER;

        // when
        customer.becomeMember();

        // then
        assertEquals("20001231-1234", customer.getMembership().getCustomer().getSSN()); // ... hmm
    }


    @Test
    void Membership_starting_date_is_recorded() {
        // Given
        Customer customer = VALID_CUSTOMER;

        // When
        customer.becomeMember();

        // Then
        assertNotEquals(null, customer.getMembership().getStartingDateTime());
    }

    @Test
    void Membership_starting_date_is_formatted_correctly() {
        // Given
        Customer customer = VALID_CUSTOMER;
        customer.becomeMember();
        Membership membership = customer.getMembership();
        String correctDateTimeFormat = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";


        // When
        String formattedDateTime = membership.getStartingDateTimeFormatted();

        // Then
        assertTrue(formattedDateTime.matches(correctDateTimeFormat));
    }

    @Test
    void Bonus_points_increase_after_purchase() {
        // Given
        Customer customer = VALID_CUSTOMER;
        customer.becomeMember();
        Membership membership = customer.getMembership();
        CheckoutSystem checkoutSystem = VALID_CHECKOUT_SYSTEM;
        Product coffee = new Product("Coffee", 100_00, Product.ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(coffee);
        long payment = checkoutSystem.getTotal();

        // When
        membership.increasePoints(payment);

        // Then
        assertEquals(125_00, membership.getPoints());
    }

    @Test
    void Membership_level_increases_at_1000_points() {
        // Given
        Customer customer = VALID_CUSTOMER;
        customer.becomeMember();
        Membership membership = customer.getMembership();
        CheckoutSystem checkoutSystem = VALID_CHECKOUT_SYSTEM;
        Product expensiveProduct = new Product("Gold", 1000_00, Product.ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(expensiveProduct);

        // When
        membership.increasePoints(checkoutSystem.getTotal());

        // Then
        assertEquals("Silver", membership.getLevel());
    }
}
