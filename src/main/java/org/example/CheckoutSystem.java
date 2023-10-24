package org.example;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;

public class CheckoutSystem {
    private final Customer customer;
    private final List<Product> basket = new ArrayList<>();
    private Map<Product.ProductCategory, Pair<Integer, Integer>> discountCampaigns;

    private Money cashRegister;

    CheckoutSystem(Customer currentCustomer) {
        this.customer = currentCustomer;
        this.discountCampaigns = new HashMap<>();
        this.cashRegister = new Money(25000_00);
    }

    void addDiscountCampaign(Product.ProductCategory category, int take, int pay) {
        if (take <= pay) throw new IllegalArgumentException("Take needs to be larger than pay!");

        discountCampaigns.put(category, Pair.of(take, pay));
    }

    public int getBasketSize() {
        return basket.size();
    }

    public void registerProduct(Product product) {
        basket.add(product);
    }

    public Product getProduct(Product product) {
        Product productFound = null;
        for (Product p : basket) {
            if (product.equals(p)) {
                productFound = p;
            }
        }
        return productFound;
    }

    public void removeProduct(Product product) {
        if (!basket.contains(product)) {
            throw new IllegalArgumentException("Product is not in basket");
        }
        basket.remove(product);
    }

    public boolean basketContains(Product product) {
        return basket.contains(product);
    }

    double getTotal() {
        if (basket.isEmpty()) {
            return 0;
        }

        resetDiscounts();

        double total = getBasketValue();
        double totalDiscountFromCampaigns = getDiscountFromCampaigns();
        double totalDiscountFromMembership = total * getMembershipDiscount();

        if (totalDiscountFromCampaigns > totalDiscountFromMembership) {
            total -= totalDiscountFromCampaigns;
        } else {
            resetDiscounts();
            applyMembershipCampaign();
            total -= totalDiscountFromMembership;
        }

        total += getTotalVAT();

        return total;
    }

    private void resetDiscounts() {
        for (Product product : basket) {
            product.setDiscountAmount(0);
        }
    }

    double getTotalVAT() {
        double totalVAT = 0;
        for (Product product : basket) {
            totalVAT += product.getPriceAfterDiscounts() * product.getProductCategory().getVATRate();
        }
        return totalVAT;
    }

    double getBasketValue() {
        double totalValue = 0;
        for (Product product : basket) {
            totalValue += product.getVATExclusive();
        }
        return totalValue;
    }

    private double getMembershipDiscount() {
        if (customer.getMembership() == null) {
            return 0;
        }

        return switch (customer.getMembership().getLevel()) {
            case "Bronze" -> 0.01;
            case "Silver" -> 0.02;
            case "Gold" -> 0.05;
            default -> 0;
        };
    }

    private double getDiscountFromCampaigns() {
        double discountAmount = 0;

        if (discountCampaigns == null) {
            return discountAmount;
        }

        for (Map.Entry<Product.ProductCategory, Pair<Integer, Integer>> entry : discountCampaigns.entrySet()) {
            discountAmount += getDiscountFromCampaign(entry);
        }
        return discountAmount;
    }

    private double getDiscountFromCampaign(Map.Entry<Product.ProductCategory, Pair<Integer, Integer>> discountCampaign) {
        double appliedDiscountAmount = 0;

        int take = discountCampaign.getValue().getLeft();
        int pay = discountCampaign.getValue().getRight();

        ArrayList<Product> campaignProducts = getProductsFromBasket(discountCampaign.getKey());

        if (campaignProducts.isEmpty()) {
            return appliedDiscountAmount;
        }
        ;

        Collections.sort(campaignProducts, Comparator.comparingDouble(Product::getVATExclusive));


        int discountedQuantity = campaignProducts.size() / take * (take - pay);

        for (int i = 0; i < discountedQuantity; i++) {
            Product product = campaignProducts.get(i);
            appliedDiscountAmount += product.getVATExclusive();
            product.setDiscountAmount(product.getVATExclusive());
        }

        return appliedDiscountAmount;
    }

    private ArrayList<Product> getProductsFromBasket(Product.ProductCategory productCategory) {
        ArrayList<Product> products = new ArrayList<>();

        for (Product product : basket) {
            if (product.getProductCategory().equals(productCategory)) {
                products.add(product);
            }
        }

        return products;
    }

    void applyMembershipCampaign() {
        double membershipDiscount = getMembershipDiscount();

        for (Product product : basket) {
            product.setDiscountAmount(product.getVATExclusive() * membershipDiscount);
        }
    }


    Map<Product.ProductCategory, Pair<Integer, Integer>> getDiscountCampaigns() {
        if (discountCampaigns == null) throw new IllegalArgumentException("There are no discount Campaigns!");

        return Map.copyOf(discountCampaigns);
    }


    void checkoutByCard() {
        double total = getTotal();
        try {
            customer.payByCard(total);
            basket.clear();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

}
