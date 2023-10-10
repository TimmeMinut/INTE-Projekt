package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class MembershipTest {
    public static final Customer VALID_CUSTOMER = new Customer("Bob", "20001231-1234", 15000_00, 500_00);
    public static final LocalDateTime VALID_DATE_TIME = LocalDateTime.of(2023, 1, 31, 15, 30);

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
}
