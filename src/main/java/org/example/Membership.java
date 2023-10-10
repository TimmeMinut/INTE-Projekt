package org.example;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Membership {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Se Henriks video om mock-objekt / test-doubles
    private LocalDateTime startingDateTime;
    private Customer customer;

    public Membership(Customer customer) {
        this.customer = customer;
        startingDateTime = LocalDateTime.now();
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
}
