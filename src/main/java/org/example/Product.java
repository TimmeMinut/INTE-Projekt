package org.example;
import org.apache.commons.lang3.tuple.Pair;


public class Product {
    private String name;
    private double VATExclusive; //price excluding VAT
    private ProductCategory productCategory;
    private boolean deposit;
    private double VATValue;
    private double price;
    private Pair<Integer,Integer> quantityDiscount;






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

    public Pair<Integer,Integer> getQuantityDiscount() { // Ev. refactoring senare
        return quantityDiscount;
    }

    public void putUpForSale(int take, int pay) {
        quantityDiscount = Pair.of(take, pay);
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