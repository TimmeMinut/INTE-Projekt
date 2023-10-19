package org.example;

public class Membership {

    private Customer customer;
    private long points;
    private String level; // Bronze, Silver, Gold

    public Membership(Customer customer) {
        this.customer = customer;
        this.points = 0;
        this.level = "Bronze";
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getPoints() {
        return points;
    }

    public String getLevel() {
        return level;
    }

    public void increasePoints(double total) {
        long pointsToAdd = (long) (total * 100);
        points += pointsToAdd;
        updateLevel(pointsToAdd);
    }

    private void updateLevel(long pointsToAdd) {
        if (points >= 1000_00 && points < 2000_00 && points - pointsToAdd < 1000_00) { // Om detta köp tar kunden över 1000_00 points
            level = "Silver";
        } else if (points >= 2000_00 && points - pointsToAdd < 2000_00) { // Om detta köp tar kunden över 2000_00 points
            level = "Gold";
        }
    }
}
