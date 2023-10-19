package org.example;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Membership {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Se Henriks video om mock-objekt / test-doubles för att testa LocalDateTime
    private LocalDateTime startingDateTime;
    private Customer customer;
    private double points;
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

    public double getPoints() {
        return points;
    }

    public String getLevel() {
        return level;
    }

    public void increasePoints(double payment) {


        points += payment;
        updateLevel(payment);
    }

    private void updateLevel(double pointsToAdd) {
        // Handlar man för > 4000_00 går man från Bronze till Silver
        // Om man därefter handlar för > 2000_00 går man Från Silver till Gold
        // Efter 1 mån resettas man till Bronze?
        if (points > 1000_00 && points < 2000_00 && points - pointsToAdd < 1000_00) { // Om detta köp tar kunden över 1000_00 points
            level = "Silver";
        } else if (points > 2000_00 && points - pointsToAdd < 2000_00) { // Om detta köp tar kunden över 2000_00 points
            level = "Gold";
        }
    }
}
