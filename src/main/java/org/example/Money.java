package org.example;

import java.util.ArrayList;
import java.util.List;

// Genererad med Ai, med vissa korrigeringar
public class Money {
    public final int[] denominations = {1000_00, 500_00, 200_00, 100_00, 50_00, 20_00, 10_00, 5_00, 2_00, 1_00};
    private static final int MAJOR_UNIT = 100;
    private long amount;

    public Money(long amount) {
        this.amount = amount;
    }

    public void add(Money other) {
        this.amount += other.amount;
    }

    public void add(long amount) {
        this.amount += amount;
    }

    public void subtract(Money other) {
        this.amount -= other.amount;
    }

    public void subtract(long amount) {
        this.amount -= amount;
    }

    public long getTotalMinorUnit() {
        return this.amount;
    }

    public long getTotalMajorUnit() {
        return this.amount / MAJOR_UNIT;
    }

    public List<Money> getAmountInDenominations() {
        List<Money> denominationList = new ArrayList<>();
        long remainingAmount = this.amount;
        for (int i = 0; i < denominations.length; i++) {
            int count = (int) (remainingAmount / denominations[i]);
            if (count != 0) {
                for (int j = 0; j < count; j++) {
                    denominationList.add(new Money(denominations[i]));
                }
                remainingAmount -= (long) count * denominations[i];
            }
        }
        return denominationList;
    }


}