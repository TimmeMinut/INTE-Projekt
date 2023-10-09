package org.example;

public class Currency implements Comparable<Currency> {

    public final String name;
    public final String symbol;
    public final int[] denominations;

    public Currency(String name, String symbol, int... denominations) {
        this.name = name;
        this.symbol = symbol;
        this.denominations = denominations;
    }

    public Money getNothing() {
        return new Money(this, 0L);
    }

    @Override
    public int compareTo(Currency other) {
        return name.compareTo(other.name);
    }

}