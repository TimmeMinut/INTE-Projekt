package org.example;

import java.util.HashMap;
import java.util.Map;

public class Product {
    private String name;
    private long VATExclusive; //price excluding VAT
    private ProductCategory productCategory;
    private boolean deposit;
    private long VATValue;
    private long price;
    private Map<Integer,Integer> quantityDiscount = new HashMap<Integer,Integer>();






    public Product(String name, long VATExclusive, ProductCategory productCategory, boolean deposit) {
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
            price += 2_00;
        }
    }

    private void calculateVATValue() {
        VATValue = (long) (VATExclusive * productCategory.VATRate);
    }

    public String getName() {
        return name;
    }

    public long getVATExclusive(){
        return VATExclusive;
    }

    public long getVATValue() {
        return VATValue;
    }

    public Map<Integer,Integer> getQuantityDiscount() { // Ev. refaktorering senare
        return quantityDiscount;
    }

    public void putUpForSale(int take, int pay) {
        this.quantityDiscount.put(take,pay);
    }

    public long getPrice() {
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