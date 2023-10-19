package org.example;

public class Customer {

    private final String name;
    private final String snn;
    private Membership membership; // Finns det nått lämpligt design-pattern?
    private final long bankAccountBalance;

    public Customer(String name, String snn, long bankAccountBalance, Boolean member) {
        this.name = name;
        this.snn = snn;
        this.bankAccountBalance = bankAccountBalance;

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

    public void pay(double total) throws IllegalStateException {
        if (bankAccountBalance < (long) (total * 100))
            throw new IllegalStateException("Payment declined: Insufficient funds.");

        bankAccountBalance -= (long) (total * 100);
    }

}
