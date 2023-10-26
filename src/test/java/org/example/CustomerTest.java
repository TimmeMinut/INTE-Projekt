package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void getName_returns_correct_name() {
        // given
        Customer customer = new Customer("Memphis", "20001231-2345",15000_00, true);

        // then
        assertEquals("Memphis", customer.getName());
    }

    @Test
    void getWallet_returns_wallet_with_correct_contents() {
        // given
        Customer customer = new Customer("Memphis", "20001231-2345",15000_00, true);
        List<Money> wallet = customer.getWallet();
        Money money = wallet.get(0);

        // then
        assertEquals(1000_00, money.getTotalMinorUnit());
    }


}
