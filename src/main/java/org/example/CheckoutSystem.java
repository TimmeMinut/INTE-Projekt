package org.example;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;

public class CheckoutSystem {
    private final Customer customer;
    private final List<Product> basket = new ArrayList<>();
    private Map<Product.ProductCategory, Pair> discountCampaigns;

    public CheckoutSystem(Customer currentCustomer) {
        this.customer = currentCustomer;
        this.discountCampaigns = new HashMap<>();
    }

    public void addDiscountCampaign(Product.ProductCategory category, int take, int pay) {
        // TODO: Is this messy?
        // Tänker att man bör kunna ändra discountCampaign för en kategori
        // Då behövs inte kontrollen, utan put(category, Pair.of(take,pay) skriver över gammal campaign
        if (take <= pay) throw new IllegalArgumentException("Take need to be larger than pay!");

//        if (!discountCampaigns.isEmpty()) {
//            for (Map.Entry<Product.ProductCategory, Pair> entry : discountCampaigns.entrySet()) {
//                if (entry.getKey() == category) {
//                    throw new IllegalArgumentException();
//                }
//            }
//        }
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

        double total = getBasketValue();

        double totalDiscountFromCampaigns = getDiscountFromCampaigns();
        double totalDiscountFromMembership = total * getMembershipDiscount();
        if (totalDiscountFromCampaigns > totalDiscountFromMembership) {
            applyDiscountFromCampaigns();
            total -= totalDiscountFromCampaigns;
        } else {
            applyMembershipCampaign();
            total -= totalDiscountFromMembership;
        }

        total += getTotalVAT();

        return total;
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

        ArrayList<Product> sortedItems = getListSorted(discountedItems);

        int totalQuantity = sortedItems.size();
        int discountedQuantity = totalQuantity / take * (take - pay);
        int notDiscountedQuantity = totalQuantity - discountedQuantity;

        // sätta rabatt på notdiscountedQuantity: de sista i listan
        for (int i = sortedItems.size() - 1; i >= notDiscountedQuantity; i--) {
            Product product = sortedItems.get(i);
            appliedDiscountAmount += product.getVATExclusive();
        }

        return appliedDiscountAmount;
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

        ArrayList<Product> sortedItems = getListSorted(discountedItems);

        int totalQuantity = sortedItems.size();
        int discountedQuantity = totalQuantity / take * (take - pay);
        int notDiscountedQuantity = totalQuantity - discountedQuantity;

        for (int i = sortedItems.size() - 1; i >= notDiscountedQuantity; i--) {
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
        ArrayList<Product> sortedList = new ArrayList<>(list);
        Collections.sort(list, (product1, product2) -> {
            double price1 = product1.getVATExclusive();
            double price2 = product2.getVATExclusive();

            return Double.compare(price1, price2);
        });
        return sortedList;
    }


    public void checkout() {
        double total = getTotal();
        try {
            customer.pay(total);
            basket.clear();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }
}
