package org.example;

import java.util.Objects;

public class Product {
    private String name;
    private ProductCategory productCategory;
    private double VATExclusive;
    private double VATValue;
    private double price;
    private boolean deposit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(getName(), product.getName());
    }

    private double discountAmount = 0;

    public Product(String name, double VATExclusive, ProductCategory productCategory, boolean deposit) {
        this.name = name;
        this.VATExclusive = VATExclusive;
        this.deposit = deposit;
        this.productCategory = productCategory;

        setPrice();
    }

    private void setPrice() {
        calculateVATValue();
        price = VATExclusive + VATValue;

        if(deposit){
            price += 2;
        }
    }

    private void calculateVATValue() {
        VATValue =  VATExclusive * productCategory.getVATRate();
    }

    public String getName() {
        return name;
    }

    public double getVATExclusive(){
        return VATExclusive;
    }

    public double getVATValue() {
        return VATValue;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }


    public double getPrice() {
        return price;
    }

    public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }

    public double getPriceAfterDiscounts() { return VATExclusive - discountAmount; }



    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", VATExclusive=" + VATExclusive +
                ", VATValue=" + VATValue +
                ", price=" + price +
                ", discountAmount=" + discountAmount +
                '}';
    }

    public enum ProductCategory {
        BOOK(0.06),
        FOOD(0.12),
        STANDARD(0.25);

        private final double VATRate;

        ProductCategory(double VATRate) {
            this.VATRate = VATRate;
        }

        public double getVATRate() {
            return VATRate;
        }
    }
}