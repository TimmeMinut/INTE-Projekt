package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    public static final Customer VALID_CUSTOMER = new Customer("Bob", "20001231-1234", 15000_00, 500_00);

    @Test
    void Membership_starts_at_Bronze_level() {
        // Given
        Customer customer = VALID_CUSTOMER;

        // When
        customer.becomeMember();

        // Then
        assertEquals("Bronze", customer.getMembership().getLevel());
    }

}
