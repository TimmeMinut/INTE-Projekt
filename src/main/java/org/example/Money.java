package org.example;

public class Money {

    private long amount;
    public static final int MAJOR_UNIT = 100;

    public Money(long amount) {
        if (amount < 0)
            throw new IllegalArgumentException("amount can't be less than zero");
        this.amount = amount;
    }

    public Money(long amountOfMajorUnit, long amountOfMinorUnit) {
        this(amountOfMajorUnit * MAJOR_UNIT + amountOfMinorUnit);
    }

    public long getAmountOfMajorUnit() {
        return amount / MAJOR_UNIT;
    }

    public long getAmountOfMinorUnit() {
        return amount % MAJOR_UNIT;
    }

    public long getTotalAmountInMinorUnit() {
        return amount;
    }

    public Money add(long amountOfMinorUnit) {
        return new Money(amount + amountOfMinorUnit);
    }

    public Money subtract(long amountOfMinorUnit) {
        return new Money(amount - amountOfMinorUnit);
    }

    @Override
    public String toString() {
        return String.format("%d.%d", getAmountOfMajorUnit(), getAmountOfMinorUnit());
    }

}
