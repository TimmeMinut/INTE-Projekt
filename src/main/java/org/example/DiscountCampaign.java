package org.example;

public class DiscountCampaign {
    private ProductCategory productCategory;
    private int buy;
    private int payFor;

    public DiscountCampaign(ProductCategory productCategory, int buy, int payFor) {
        this.productCategory = productCategory;
        this.buy = buy;
        this.payFor = payFor;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public int getBuy() {
        return buy;
    }

    public int getPayFor() {
        return payFor;
    }
}
