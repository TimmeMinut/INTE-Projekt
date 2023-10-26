package org.example;

import java.util.ArrayList;
import java.util.List;

// Genererad med Ai, med vissa korrigeringar
class Money {
    private final int[] denominations = {1000_00, 500_00, 200_00, 100_00, 50_00, 20_00, 10_00, 5_00, 2_00, 1_00};
    private static final int MAJOR_UNIT = 100;
    private long amount;

    Money(long amount) {
        this.amount = amount;
    }

    void add(Money other) {
        this.amount += other.amount;
    }

    void add(long amount) {
        this.amount += amount;
    }

    void subtract(Money other) {
        this.amount -= other.amount;
    }

    void subtract(long amount) {
        this.amount -= amount;
    }

    long getTotalMinorUnit() {
        return this.amount;
    }

    long getTotalMajorUnit() {
        return this.amount / MAJOR_UNIT;
    }

    List<Money> getAmountInDenominations() {
        List<Money> denominationList = new ArrayList<>();
        long remainingAmount = this.amount;
        for (int denomination : denominations) {
            int count = (int) (remainingAmount / denomination);
            if (count != 0) {
                for (int j = 0; j < count; j++) {
                    denominationList.add(new Money(denomination));
                }
                remainingAmount -= (long) count * denomination;
            }
        }
        return denominationList;
    }


}