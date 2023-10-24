package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Genererad av Ai, med vissa korrigeringar
public class MoneyTest {

    @Test
    public void testGetAmountInDenominations_201() {
        Money money = new Money(201_00);
        List<Money> denominations = money.getAmountInDenominations();
        assertEquals(2, denominations.size());
        assertEquals(200_00, denominations.get(0).getTotalMinorUnit());
        assertEquals(1_00, denominations.get(1).getTotalMinorUnit());
    }

    @Test
    public void testGetAmountInDenominations_1001() {
        Money money = new Money(1001_00);
        List<Money> denominations = money.getAmountInDenominations();
        assertEquals(2, denominations.size());
        assertEquals(1000_00, denominations.get(0).getTotalMinorUnit());
        assertEquals(1_00, denominations.get(1).getTotalMinorUnit());
    }

    @Test
    public void testGetAmountInDenominations_0() {
        Money money = new Money(0);
        List<Money> denominations = money.getAmountInDenominations();
        assertEquals(0, denominations.size());
    }

    @Test
    public void testGetAmountInDenominations_5000_21() {
        Money money = new Money(5000_21);
        List<Money> denominations = money.getAmountInDenominations();
        assertEquals(1000_00, denominations.get(0).getTotalMinorUnit());
        assertEquals(1000_00, denominations.get(1).getTotalMinorUnit());
        assertEquals(1000_00, denominations.get(2).getTotalMinorUnit());
        assertEquals(1000_00, denominations.get(3).getTotalMinorUnit());
        assertEquals(1000_00, denominations.get(4).getTotalMinorUnit());
    }

    @Test
    public void testGetAmountInDenominations() {
        // Test case 1: amount is 1234 kr
        Money money1 = new Money(1234_00);
        List<Money> result1 = money1.getAmountInDenominations();
        assertEquals(6, result1.size());
        assertEquals(1000_00, result1.get(0).getTotalMinorUnit());
        assertEquals(200_00, result1.get(1).getTotalMinorUnit());
        assertEquals(20_00, result1.get(2).getTotalMinorUnit());
        assertEquals(10_00, result1.get(3).getTotalMinorUnit());
        assertEquals(2_00, result1.get(4).getTotalMinorUnit());
        assertEquals(2_00, result1.get(5).getTotalMinorUnit());

        // Test case 2: amount is 5000 kr
        Money money2 = new Money(5000_00);
        List<Money> result2 = money2.getAmountInDenominations();
        assertEquals(5, result2.size());
        for (Money m : result2) {
            assertEquals(1000_00, m.getTotalMinorUnit());
        }

        // Test case 3: amount is 789 kr
        Money money3 = new Money(789_00);
        List<Money> result3 = money3.getAmountInDenominations();
        assertEquals(8, result3.size());
        assertEquals(500_00, result3.get(0).getTotalMinorUnit());
        assertEquals(200_00, result3.get(1).getTotalMinorUnit());
        assertEquals(50_00, result3.get(2).getTotalMinorUnit());
        assertEquals(20_00, result3.get(3).getTotalMinorUnit());
        assertEquals(10_00, result3.get(4).getTotalMinorUnit());
        assertEquals(5_00, result3.get(5).getTotalMinorUnit());
        assertEquals(2_00, result3.get(6).getTotalMinorUnit());
        assertEquals(2_00, result3.get(7).getTotalMinorUnit());
    }




}
