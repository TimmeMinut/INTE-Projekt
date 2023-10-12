package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Product {
    private String name;
    private int VATExclusive; //price exclusing VAT
    private ProductCategory productCategory;
    private boolean deposit;
    private double VATValue;
    private double price;
    private Map<Integer,Integer> discount = new HashMap<Integer,Integer>();






    public Product(String name, int VATExclusive, ProductCategory productCategory, boolean deposit) {
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
        VATValue = VATExclusive * productCategory.VATRate;
    }

    public String getName() {
        return name;
    }

    public int getVATExclusive(){
        return VATExclusive;
    }

    public double getVATValue() {
        return VATValue;
    }

    public Map<Integer,Integer> getDiscount() { // Ev. refaktorering senare
        return discount;
    }

    public void putUpForSale(int take, int pay) {
        this.discount.put(take,pay);
    }

    public double getPrice() {
        return price;
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