package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    public static final Customer NON_MEMBER_CUSTOMER = new Customer("Bob", "19991231-1234", 15000_00, false);



    @Test
    void Membership_starts_at_Bronze_level() {
        // Given
        Customer customer = new Customer("Memphis", "20001231-2345", 15000_00, true);
        Membership membership = customer.getMembership();

        // Then
        assertEquals("Bronze", membership.getLevel());
    }

}
