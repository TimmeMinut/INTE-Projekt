package org.example;

public class Card {
    private long balance;

    public Card(long balance) {
        this.balance = balance;
    }

    public long getBalance() { return balance; }

    public void debit(long sum) {
        balance -= sum;
    }
}
