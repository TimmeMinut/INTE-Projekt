package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class MembershipTest {
    public static final Customer NON_MEMBER_CUSTOMER = new Customer("Bob", "20001231-1234", 15000_00, 500_00);
    public static final Customer MEMBER_CUSTOMER = new Customer("Miriam", "19990115-2345", 15000_00, 500_00);
    public static final LocalDateTime VALID_DATE_TIME = LocalDateTime.of(2023, 1, 31, 15, 30);

    @BeforeAll
    public static void initMemberCustomer() {
        MEMBER_CUSTOMER.becomeMember();
    }

    @Test
    void Membership_is_created_for_correct_customer() {
        // given
        Customer customer = NON_MEMBER_CUSTOMER;

        // when
        customer.becomeMember();

        // then
        assertEquals("20001231-1234", customer.getMembership().getCustomer().getSSN()); // ... hmm
    }


    @Test
    void Membership_starting_date_is_recorded() {
        // Given
        Customer customer = NON_MEMBER_CUSTOMER;

        // When
        customer.becomeMember();

        // Then
        assertNotEquals(null, customer.getMembership().getStartingDateTime());
    }

    @Test
    void Membership_starting_date_is_formatted_correctly() {
        // Given
        Customer customer = NON_MEMBER_CUSTOMER;
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
        Customer customer = MEMBER_CUSTOMER;
        Membership membership = customer.getMembership();

        // When
//        customer.checksOut();
        membership.increasePoints(1000_00);

        // Then
        assertEquals(250, membership.getPoints());
    }
}
