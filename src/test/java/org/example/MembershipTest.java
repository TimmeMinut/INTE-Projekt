package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MembershipTest {

    @Test
    void Membership_is_created_for_correct_customer() {
        // given
        Customer customer = new Customer("Memphis", "20001231-2345",15000_00, true);
        Membership membership = customer.getMembership();
        // then
        assertEquals("20001231-2345", membership.getCustomer().getSsn());
    }

    @Test
    void Membership_starts_at_Bronze_level() {
        // Given
        Customer customer = new Customer("Memphis", "20001231-2345", 15000_00, true);
        Membership membership = customer.getMembership();

        // Then
        assertEquals("Bronze", membership.getLevel());
    }


    @Test
    void Bonus_points_increase_after_purchase() {
        // Given
        Customer customer = new Customer("Memphis", "20001231-2345",15000_00, true);
        Membership membership = customer.getMembership();
        CheckoutSystem checkoutSystem = new CheckoutSystem(customer);
        Product coffee = new Product("Coffee", 100, Product.ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(coffee);

        // When
        membership.increasePoints(checkoutSystem.getTotal());

        // Then
        assertEquals((125 * 100) * (1 - 0.01), membership.getPoints());
    }

    @Test
    void Silver_reached_at_1000_points() {
        // Given
        Customer customer = new Customer("Memphis", "20001231-2345",15000_00, true);
        Membership membership = customer.getMembership();
        CheckoutSystem checkoutSystem = new CheckoutSystem(customer);
        Product expensiveProduct = new Product("Saffron", 1000, Product.ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(expensiveProduct);

        // When
        membership.increasePoints(checkoutSystem.getTotal());

        // Then
        assertEquals("Silver", customer.getMembership().getLevel());
    }

    @Test
    void Gold_reached_at_2000_points() {
        // Given
        Customer customer = new Customer("Memphis", "20001231-2345",15000_00, true);
        Membership membership = customer.getMembership();
        CheckoutSystem checkoutSystem = new CheckoutSystem(customer);
        Product expensiveProduct = new Product("Saffron", 2000, Product.ProductCategory.STANDARD, false);
        checkoutSystem.registerProduct(expensiveProduct);

        // When
        membership.increasePoints(checkoutSystem.getTotal());

        // Then
        assertEquals("Gold", customer.getMembership().getLevel());
    }
}
