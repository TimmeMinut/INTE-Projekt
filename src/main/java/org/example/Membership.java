package org.example;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Membership {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Se Henriks video om mock-objekt / test-doubles för att testa LocalDateTime
    private LocalDateTime startingDateTime;
    private Customer customer;
    private long points;
    private String level; // Bronze, Silver, Gold

    public Membership(Customer customer) {
        this.customer = customer;
        this.startingDateTime = LocalDateTime.now();
        this.points = 0;
        this.level = "Bronze";
    }

    public LocalDateTime getStartingDateTime() {
        return startingDateTime;
    }

    public String getStartingDateTimeFormatted() {
        return formatter.format(startingDateTime);
    }

    public Customer getCustomer() {
        return customer;
    }

    public long getPoints() {
        return points;
    }

    public String getLevel() {
        return level;
    }

    public void increasePoints(long payment) {
        double bonusFactor;
        switch (level) {
            case "Bronze" -> bonusFactor = 0.25;
            case "Silver" -> bonusFactor = 0.50;
            case "Gold" -> bonusFactor = 0.75;
            default -> bonusFactor = 0;
        }

        long pointsToAdd = (long) Math.round(payment * bonusFactor);
        points += pointsToAdd;

        updateLevel(pointsToAdd);
    }

    private void updateLevel(long pointsToAdd) {
        // Handlar man för >= 1000_00 går man från Bronze till Silver
        // Handlar man för >= 2000_00 går man Från Silver till Gold
        // Efter 1 mån resettas man till Bronze?
        if (points > 1000_00 && points - pointsToAdd < 1000_00) { // Om detta köp tar kunden över 1000_00 points
            level = "Silver";
        } else if (points > 2000_00 && points - pointsToAdd < 2000_00) { // Om detta köp tar kunden över 2000_00 points
            level = "Gold";
        }
    }
}
