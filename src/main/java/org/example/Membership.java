package org.example;
import java.time.LocalDateTime;

public class Membership {



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

    public Customer getCustomer() {
        return customer;
    }
}
