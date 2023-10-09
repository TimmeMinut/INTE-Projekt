package org.example;

public class SEK extends Currency {

    public static final SEK instance = new SEK();

    private SEK() {
        super("Svenska kronor", "kr", 100000, 50000, 10000, 5000, 2000, 1000,
                500, 100, 1);
    }

}
