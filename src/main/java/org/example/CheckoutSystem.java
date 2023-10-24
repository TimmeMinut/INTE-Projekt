package org.example;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;

public class CheckoutSystem {
    private final Customer customer;
    private final ArrayList<Product> basket = new ArrayList<>();
    private Map<Product.ProductCategory, Pair> discountCampaigns;

    private Money cashRegister;

    public CheckoutSystem(Customer currentCustomer) {
        this.customer = currentCustomer;
        this.discountCampaigns = new HashMap<>();
        this.cashRegister = new Money(25000_00);
    }

    public void addDiscountCampaign(Product.ProductCategory category, int take, int pay) {
        if (take <= pay) throw new IllegalArgumentException("Take need to be larger than pay!");

        discountCampaigns.put(category, Pair.of(take, pay));
    }

    public int getBasketSize() {
        return basket.size();
    }


    public void registerProduct(Product product) {
        basket.add(product);
    }

    public Product getProduct(String productName) {
        Product product = null;
        for (Product p : basket) {
            if (productName.equals(p.getName())) {
                product = p;
            }
        }
        return product;
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

    public double getTotal() {
        if (basket.isEmpty()) {
            return 0;
        }

        resetDiscounts(); // TODO Where to reset?

        double total = getBasketValue();
        double totalDiscountFromCampaigns = getDiscountFromCampaigns();
        double totalDiscountFromMembership = total * getMembershipDiscount();

        if (totalDiscountFromCampaigns > totalDiscountFromMembership) {
            //getDiscountFromCampaigns();
            total -= totalDiscountFromCampaigns;
        } else {
            resetDiscounts(); // TODO Where to reset?
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

    public double getTotalVAT() {
        double totalVAT = 0;
        for (Product product : basket) {
            totalVAT += product.getPriceAfterDiscounts() * product.getProductCategory().getVATRate();
        }
        return totalVAT;
    }

    public double getBasketValue() {
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

        for (Map.Entry<Product.ProductCategory, Pair> entry : discountCampaigns.entrySet()) {
            discountAmount += getDiscountFromCampaign(entry);
        }
        return discountAmount;
    }

    private double getDiscountFromCampaign(Map.Entry<Product.ProductCategory, Pair> discountCampaign) {
        double appliedDiscountAmount = 0;

        int take = (int) discountCampaign.getValue().getLeft();
        int pay = (int) discountCampaign.getValue().getRight();

        ArrayList<Product> campaignProducts = getProductsFromBasket(discountCampaign.getKey());

        //if(campaignProducts.isEmpty()) {
        // TODO Felhantering?
        //};

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

    private void applyDiscountFromCampaigns() {
        for (Map.Entry<Product.ProductCategory, Pair> entry : discountCampaigns.entrySet()) {
            applyDiscountFromCampaigns(entry);
        }
    }

    public void applyDiscountFromCampaigns(Map.Entry<Product.ProductCategory, Pair> discountCampaign) {
        Product.ProductCategory productCategory = discountCampaign.getKey();
        int take = (int) discountCampaign.getValue().getLeft();
        int pay = (int) discountCampaign.getValue().getRight();

        ArrayList<Product> discountedItems = new ArrayList<>();
        for (Product product : basket) {
            if (product.getProductCategory().equals(productCategory)) {
                discountedItems.add(product);
            }
        }

        if (discountedItems.isEmpty()) {
            // TODO Felhantering?
        }
        ;

        ArrayList<Product> sortedItems = getListSorted(discountedItems);

        int totalQuantity = sortedItems.size();
        int discountedQuantity = totalQuantity / take * (take - pay);
        int notDiscountedQuantity = totalQuantity - discountedQuantity;

        for (int i = 0; i < discountedQuantity; i++) {
            Product product = sortedItems.get(i);
            product.setDiscountAmount(product.getVATExclusive());
        }

    }

    public void applyMembershipCampaign() {
        double membershipDiscount = getMembershipDiscount();

        for (Product product : basket) {
            product.setDiscountAmount(product.getVATExclusive() * membershipDiscount);
        }
    }

    private ArrayList<Product> getListSorted(ArrayList<Product> list) {
        Collections.sort(list, Comparator.comparingDouble(Product::getVATExclusive));
        return list;
    }

    public void checkoutByCard() {
        double total = getTotal();
        try {
            customer.payByCard(total);
            basket.clear();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

}
