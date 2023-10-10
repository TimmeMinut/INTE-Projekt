package org.example;
import java.time.LocalDateTime;
public class Membership {

    // Start-date
    // För att testa krävs subklasser/interface ?
    // Se Henriks video om mock-objekt / test-doubles
    private LocalDateTime startingDate;
    private Customer customer;

    public Membership(Customer customer) {
        this.customer = customer;
        startingDate = LocalDateTime.now();
    }

    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    public Customer getCustomer() {
        return customer;
    }
}
