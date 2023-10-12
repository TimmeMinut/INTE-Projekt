package org.example;

public class Product {
    private String name;
    private int VATExclusive; //price exclusing VAT
    private ProductCategory productCategory;
    private boolean deposit;
    private double VATValue;
    private double price;



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
        VATValue = VATExclusive * (productCategory.VATRate + 1);
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

    public void putUpForSale(int i, int i1) {
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