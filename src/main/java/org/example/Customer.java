package org.example;

import java.util.List;

class Customer {
    private final String name;
    private final String ssn;
    private Membership membership;
    private long bankAccountBalance;
    private final List<Money> wallet;

    Customer(String name, String ssn, long bankAccountBalance, boolean member) {
        this.name = name;
        this.ssn = ssn;
        this.bankAccountBalance = bankAccountBalance;
        this.wallet = List.of(new Money(1000_00), new Money(500_00), new Money(200_00), new Money(50_00));

        if (member) {
            this.membership = new Membership(this);
        }
    }

    String getName() {
        return name;
    }

    String getSsn() {
        return ssn;
    }

    List<Money> getWallet() {
        return List.copyOf(wallet);
    }

    Membership getMembership() {
        return membership;
    }

    long getBankAccountBalance() {
        return bankAccountBalance;
    }

    void addMoney(long amount) {
        bankAccountBalance += amount;
    }


    void payByCard(double total) {
        if (bankAccountBalance < (long) (total * 100))
            throw new IllegalStateException("Payment declined: Insufficient funds.");

        bankAccountBalance -= (long) (total * 100);
    }


}
