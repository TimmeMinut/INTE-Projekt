package org.example;

public class Money implements Comparable<Money> {

    private static final int MAJOR_UNIT = 100;

    private Currency currency;
    private long ammount;

    public Money(Currency currency, long ammount) {
        if (currency == null)
            throw new IllegalArgumentException("Currency can't be null");
        if (ammount < 0)
            throw new IllegalArgumentException(
                    "Ammount can't be less than zero");
        this.currency = currency;
        this.ammount = ammount;
    }

    public Money(Currency currency, long ammountOfMajorUnit,
                 long ammountOfMinorUnit) {
        this(currency, ammountOfMajorUnit * MAJOR_UNIT + ammountOfMinorUnit);
    }

    public long getAmmountOfMajorUnit() {
        return ammount / MAJOR_UNIT;
    }

    public long getAmmountOfMinorUnit() {
        return ammount % MAJOR_UNIT;
    }

    public long getTotalAmmountInMinorUnit() {
        return ammount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Money add(long ammountOfMinorUnit) {
        return new Money(currency, ammount + ammountOfMinorUnit);
    }

    public Money add(Money addend) {
        if (currency != addend.currency)
            throw new IllegalArgumentException(
                    "The currency needs to be the same in order to add");
        return add(addend.ammount);
    }

    public Money subtract(long ammountOfMinorUnit) {
        return new Money(currency, ammount - ammountOfMinorUnit);
    }

    public Money subtract(Money subtrahend) {
        if (currency != subtrahend.currency)
            throw new IllegalArgumentException(
                    "The currency needs to be the same in order to subtract");
        if (ammount < subtrahend.ammount)
            throw new IllegalArgumentException(
                    "You can't subtract more money than you have");
        return subtract(subtrahend.ammount);
    }

    public int compareTo(Money other) {
        if (currency != other.currency)
            return currency.name.compareTo(other.currency.name);
        return (int) (ammount - other.ammount);
    }

    public boolean equals(Object other) {
        if (other instanceof Money) {
            Money m = (Money) other;
            return currency.equals(m.currency) && ammount == m.ammount;
        } else {
            return false;
        }
    }

    public String toString() {
        return String.format("%d:%d %s", getAmmountOfMajorUnit(),
                getAmmountOfMinorUnit(), getCurrency().symbol);
    }

    public Money getNothing() {
        return new Money(currency, 0L);
    }
}
