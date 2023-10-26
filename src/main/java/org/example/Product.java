package org.example;

import java.util.List;
import java.util.Objects;

class Product {
    private final String name;
    private final ProductCategory productCategory;
    private final double VATExclusive;
    private final boolean deposit;
    private double VATValue;
    private double price;
    private double discountAmount = 0;

    Product(String name, double VATExclusive, ProductCategory productCategory, boolean deposit) {
        this.name = name;
        this.VATExclusive = VATExclusive;
        this.deposit = deposit;
        this.productCategory = productCategory;

        setPrice();
    }

    private void setPrice() {
        calculateVATValue();
        price = VATExclusive + VATValue;

        if (deposit) {
            price += 2;
        }
    }

    double getVATExclusive(){
        return VATExclusive;
    }

    String getName() {
        return name;
    }

    double getVATValue() {
        return VATValue;
    }

    ProductCategory getProductCategory() {
        return productCategory;
    }

    double getPrice() {
        return price;
    }

    void setDiscountAmount(double discountAmount) {
        if(discountAmount >= 0 && discountAmount <= VATExclusive){
            this.discountAmount = discountAmount;
        }else{
            throw new IllegalArgumentException("Discount must be greater than '0' and less than VATExclusive ");
        }
    }

    double getPriceAfterDiscount() {
        double VATExclusiveAfterDiscount = VATExclusive-discountAmount;
        double VATValueAfterDiscount = VATExclusiveAfterDiscount * productCategory.getVATRate();
        double priceAfterDiscount = VATExclusiveAfterDiscount + VATValueAfterDiscount;

        if(deposit){
            priceAfterDiscount += 2;
        }

        return priceAfterDiscount;
    }

    double getVATExclusiveAfterDiscounts() { return VATExclusive - discountAmount; }

    double getDiscountAmount() { return discountAmount;
    }

    private void calculateVATValue() {
        VATValue =  VATExclusive * productCategory.getVATRate();
    }

    @Override
     public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(getName(), product.getName());
    }
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

    enum ProductCategory {
        BOOK(0.06),
        FOOD(0.12),
        STANDARD(0.25);

        private final double VATRate;

        ProductCategory(double VATRate) {
            this.VATRate = VATRate;
        }

        double getVATRate() {
            return VATRate;
        }
    }
}