package org.example;

import java.util.List;

class Customer {
    private final String name;
    private final String snn;
    private Membership membership;
    private long bankAccountBalance;
    private List<Money> wallet;

    Customer(String name, String snn, long bankAccountBalance, Boolean member) {
        this.name = name;
        this.snn = snn;
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
        return snn;
    }

    long getBankAccountBalance() {
        return bankAccountBalance;
    }

    Membership getMembership() {
        return membership;
    }

    void addMoney(long amount) {
        bankAccountBalance += amount;
    }

    List<Money> getWallet() {
        return wallet;
    }

    void payByCard(double total) throws IllegalStateException {
        if (bankAccountBalance < (long) (total * 100))
            throw new IllegalStateException("Payment declined: Insufficient funds.");

        bankAccountBalance -= (long) (total * 100);
    }

    void payByCash(double total) throws IllegalStateException {

    }

}
