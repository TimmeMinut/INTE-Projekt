package org.example;
import java.time.LocalDateTime;
public class Membership {

    // Start-date
    // För att testa krävs subklasser/interface ?
    // Se Henriks video om mock-objekt / test-doubles
    private LocalDateTime startingDate;

    public Membership() {
        startingDate = LocalDateTime.now();
    }


}
