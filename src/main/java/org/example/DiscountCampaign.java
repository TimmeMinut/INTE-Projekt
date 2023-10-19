package org.example;

public class DiscountCampaign {
    private Product.ProductCategory productCategory;
    private int buy;
    private int payFor;

    public DiscountCampaign(Product.ProductCategory productCategory, int buy, int payFor) {
        this.productCategory = productCategory;
        this.buy = buy;
        this.payFor = payFor;
    }

    public Product.ProductCategory getProductCategory() {
        return productCategory;
    }

    public int getBuy() {
        return buy;
    }

    public int getPayFor() {
        return payFor;
    }
}
