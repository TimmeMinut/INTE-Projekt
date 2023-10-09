package org.example;

public class ConcreteProduct implements Product {

    private String name;
    private double price;
    //private Currency baseCurrency;

    public ConcreteProduct(String name, double price) {
        // TODO price check? Negative, max roof price etc
        this.name = name;
        this.price = price;
        //this.baseCurrency = baseCurrency;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
