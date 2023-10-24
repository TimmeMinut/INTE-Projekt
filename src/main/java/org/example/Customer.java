package org.example;

import java.util.List;

public class Customer {

    private final String name;
    private final String snn;
    private Membership membership;
    private long bankAccountBalance;
    private List<Money> wallet;

    public Customer(String name, String snn, long bankAccountBalance, Boolean member) {
        this.name = name;
        this.snn = snn;
        this.bankAccountBalance = bankAccountBalance;
        this.wallet = List.of(new Money(1000_00), new Money(500_00), new Money(200_00), new Money(50_00));

        if (member) {
            this.membership = new Membership(this);
        }
    }

    public String getName() {
        return name;
    }

    public String getSsn() {
        return snn;
    }

    public long getBankAccountBalance() {
        return bankAccountBalance;
    }

    public Membership getMembership() {
        return membership;
    }

    public void addMoney(long amount) {
        bankAccountBalance += amount;
    }

    public List<Money> getWallet() {
        return wallet;
    }

    public void payByCard(double total) throws IllegalStateException {
        if (bankAccountBalance < (long) (total * 100))
            throw new IllegalStateException("Payment declined: Insufficient funds.");

        bankAccountBalance -= (long) (total * 100);
    }

    public void payByCash(double total) throws IllegalStateException {

    }

}
