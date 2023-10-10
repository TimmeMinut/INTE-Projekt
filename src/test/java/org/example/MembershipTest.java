package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class MembershipTest {
    public static final Customer VALID_CUSTOMER = new Customer("Bob", "20001201-1234", 15000_00, 500_00);
    @Test
    void Membership_is_created_for_correct_customer() {
        // given
        Customer customer = VALID_CUSTOMER; // ..kanske komma på bättre namn

        // when
        customer.becomeMember();

        // then
        assertEquals("20001201-1234", customer.getMembership().getCustomer().getSSN()); // ... hmm
    }


    @Test
    void Membership_starting_date_is_recorded(){

    }
}
