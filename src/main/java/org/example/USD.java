package org.example;

public class USD extends Currency {

    public static final USD instance = new USD();

    private USD() {
        super("US dollar", "$", 10000, 5000, 2000, 1000, 500, 100, 50, 25, 10,
                5, 1);
    }

}
