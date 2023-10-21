package org.example;

public class Product {
    private String name;
    private ProductCategory productCategory;
    private double VATExclusive; //price excluding VAT
    private double VATValue;
    private double price;
    private boolean deposit;
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

    /*public Pair<Integer,Integer> getQuantityDiscount() { // Ev. refactoring senare
        return quantityDiscount;
    }*/

    /*public void putUpForSale(int take, int pay) {
        quantityDiscount = Pair.of(take, pay);
    }*/

    public double getPrice() {
        return price;
    }

    public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }

    public double getPriceAfterDiscounts() { return VATExclusive - discountAmount; }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Product object) {
            return this.getName().equals(object.getName());
        }

        return false;
    }

    @Override
    public String toString() {
        return name;
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