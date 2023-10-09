package org.example;

public class ConcreteProduct implements Product {

    private String name;
    private double price;
    //private Currency baseCurrency;

    public ConcreteProduct(String name, double price) {
        this.name = name;
        this.price = price;
        //this.baseCurrency = baseCurrency;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
