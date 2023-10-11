package org.example;

public class Product {
    private String name;
    private int price;
    private ProductCategory productCategory;
    private double VATValue;



    public Product(String name, int price, ProductCategory productCategory) {
        this.name = name;
        this.price = price;
        this.productCategory = productCategory;

        calculateVATValue();
    }

    private void calculateVATValue() {
        VATValue = price * (productCategory.VATRate + 1);
    }

    public String getName() {
        return name;
    }

    public int getPrice(){
        return price;
    }

    public double getVATValue() {
        return VATValue;
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
