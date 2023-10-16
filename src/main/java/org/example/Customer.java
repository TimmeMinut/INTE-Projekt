package org.example;

public class Customer {

    private final String name;
    private final String SSN;
    private Membership membership; // Finns det nått lämpligt design-pattern?
    private final long bankAccountBalance;
    private final long walletBalance;

    public Customer(String name, String SSN, long bankAccountBalance, long walletBalance) {
        this.name = name;
        this.SSN = SSN;
        this.bankAccountBalance = bankAccountBalance;
        this.walletBalance = walletBalance;
    }

    public String getName() {
        return name;
    }

    public String getSSN() {
        return SSN;
    }

    public long getBankAccountBalance() {
        return bankAccountBalance;
    }

    public long getWalletBalance() {
        return walletBalance;
    }

    public Membership getMembership() {
        return membership;
    }

    public void becomeMember() {
        if (this.membership != null) return;
        membership = new Membership(this);
    }
}
