package org.example;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Membership {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Se Henriks video om mock-objekt / test-doubles
    private LocalDateTime startingDateTime;
    private Customer customer;
    private int points;
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

    public int getPoints() {
        return points;
    }

    public String getLevel() {
        return level;
    }

    public void increasePoints(long payment) {
        // Ska öka baserat på hur mkt kund betalar vid ett köp, och deras nivå.
        // Konvertering från long till int, vad händer vid enormt köp?
        double bonusFactor;
        switch (this.getLevel()) {
            case "Bronze" -> bonusFactor = 0.25;
            case "Silver" -> bonusFactor = 0.50;
            case "Gold" -> bonusFactor = 0.75;
            default -> bonusFactor = 0;
        }

        double rawPoints = (double) (payment * bonusFactor) * 0.1 * 0.1;
        this.points += (int) Math.round(rawPoints);
    }

    public void changeLevel() {

    }
}
