package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CheckoutSystem {
    private final Customer customer;
    private final Map<Product, Integer> basket = new HashMap<>();
    private final ArrayList<ConcreteArticle> articles = new ArrayList<>();
    private final ArrayList<DiscountCampaign> discountCampaigns = new ArrayList<>();
    private final ArrayList<String> discountCodes = new ArrayList<>();

    public CheckoutSystem(Customer currentCustomer) {
        this.customer = currentCustomer;
        //discountCampaigns.add( new DiscountCampaign(ProductCategory.BOOK, 3, 2));
    }

    public void addDiscountCampaign(DiscountCampaign newDiscountCampaign) {
        // TODO: Is this messy?
        if (!discountCampaigns.isEmpty()) {
            for (DiscountCampaign campaign: discountCampaigns) {
                if (campaign.getProductCategory() == newDiscountCampaign.getProductCategory()) {
                    throw new IllegalArgumentException();
                }
            }
        }
        discountCampaigns.add(newDiscountCampaign);
    }

    public void registerProduct(Product product) {
        if (!basket.containsKey(product)) {
            basket.put(product, 1);
        } else {
            int quantity = basket.get(product);
            basket.put(product, ++quantity); // Skriver över tidigare quantity med +1
        }
    }

    public void registerProduct(ConcreteArticle article) {
        articles.add(article);
    }


    public Product getProduct(String productName) {
        Product product = null;
        for (Product p : basket.keySet()) {
            if (productName.equals(p.getName())) {
                product = p;
            }
        }
        return product;
    }

    public void removeProduct(String productName) {
        Product product = getProduct(productName);

        if (!basket.containsKey(product)) {
            throw new IllegalArgumentException();
        }

        if (basket.get(product) == 1) { // Om det bara finns 1 av produkten tas entry bort
            basket.remove(product);
        } else {
            int quantity = basket.get(product);
            basket.put(product, --quantity); // Skriver över tidigare quantity med -1
        }
    }

    public boolean contains(Product product) {
        return basket.containsKey(product);
    }

    public double getTotal() {
        if (articles.isEmpty()) {
            return 0;
        }

        double total = getBasketValue();

        double totalDiscountFromCampaigns = getDiscountFromCampaigns();
        double totalDiscountFromMembership = getBasketValue() * getMembershipDiscount();
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
        for (ConcreteArticle article: articles) {
            totalVAT += article.getPriceAfterDiscounts() * article.getProductCategory().getVATRate();
        }
        return totalVAT;
    }

    public double getBasketValue() {
        double totalValue = 0;
        for(ConcreteArticle article: articles) {
            totalValue += article.getPrice();
        }
        return totalValue;
    }

    private double getQuantityDiscount(Product product) {
            if(product.getQuantityDiscount() != null){
                int toBeBought = basket.get(product);

                int amountToReachDiscount = product.getQuantityDiscount().getKey();

                //int amountToReachDiscount = (int) product.getQuantityDiscount().getLeft();

                if(toBeBought >= amountToReachDiscount){
                    int totalPayFor = 0;
                    int payFor = (int) product.getQuantityDiscount().getValue();
                    int temp = toBeBought;

                    while(temp >= amountToReachDiscount){
                        totalPayFor += temp -payFor;
                        temp =-amountToReachDiscount;
                    }

                    return totalPayFor * product.getPrice();
                }

            }
        return 0;
    }

    private double getMembershipDiscount() {
        switch (customer.getMembership().getLevel()) {
            case "Bronze":
                return 0.01;
            case "Silver":
                return 0.03;
            case "Gold":
                return 0.05;
            default:
                return 0;
        }
    }

    public void pay(Card card) {
        double total = getTotal();
        double balance = card.getBalance();

        if (total > balance) {
            // TODO How to handle this?
        }

        card.debit(total);
    }

    private double getDiscountFromCampaigns() {
        double discountAmount = 0;

        for ( DiscountCampaign campaign: discountCampaigns) {
            discountAmount += getDiscountFromCampaign(campaign);
        }
        return discountAmount;
    }

    private ArrayList<Product> getBasketAsList() {
        if (basket.isEmpty()) {
            return new ArrayList<Product>();
        }

        ArrayList<Product> basketAsList = new ArrayList<>();
        for (Product product: basket.keySet()) {
            for (int i = 1; i == basket.get(product); i++) {
                basketAsList.add(product);
            }
        }

        return basketAsList;
    }

    private double getDiscountFromCampaign(DiscountCampaign discountCampaign) {
        double appliedDiscountAmount = 0;

        ProductCategory productCategory = discountCampaign.getProductCategory();
        int buy = discountCampaign.getBuy();
        int payFor = discountCampaign.getPayFor();

        ArrayList<ConcreteArticle> discountedItems = new ArrayList<>();

        // ArrayList<Product> basket = getBasketAsList()
        // ArrayList<Product> getProductsFromBasketByCategory(productCategory)
        for (ConcreteArticle article: articles) {
            if (article.getProductCategory().equals(productCategory)) {
                discountedItems.add(article);
            }
        }

        if(discountedItems.isEmpty()) {
            // TODO Felhantering?
        };

        ArrayList<ConcreteArticle> sortedItems = getArticlesSorted(discountedItems);

        int totalQuantity = sortedItems.size(); // TODO Throw away since articleGroup.getArticles()?
        int discountedQuantity = totalQuantity / buy * ( buy - payFor);
        int notDiscountedQuantity = totalQuantity - discountedQuantity;

        // sätta rabatt på notdiscountedQuantity: de sista i listan
        for ( int i = sortedItems.size()-1; i >= notDiscountedQuantity; i--) {
            ConcreteArticle article = sortedItems.get(i);
            appliedDiscountAmount += article.getPrice();
            //article.setDiscountAmount(article.getPrice());
        }

        return appliedDiscountAmount;
    }

    private void applyDiscountFromCampaigns() {
        for ( DiscountCampaign campaign: discountCampaigns) {
            applyDiscountFromCampaigns(campaign);
        }
    }

    public void applyDiscountFromCampaigns(DiscountCampaign discountCampaign) {
        ProductCategory productCategory = discountCampaign.getProductCategory();
        int buy = discountCampaign.getBuy();
        int payFor = discountCampaign.getPayFor();

        ArrayList<ConcreteArticle> discountedItems = new ArrayList<>();
        for (ConcreteArticle article: articles) {
            if (article.getProductCategory().equals(productCategory)) {
                discountedItems.add(article);
            }
        }

        if(discountedItems.isEmpty()) {
            // TODO Felhantering?
        };
        // discountedItems = getArticlesSorted(discountedItems);
        ArrayList<ConcreteArticle> sortedItems = getArticlesSorted(discountedItems);

        int totalQuantity = articles.size(); // TODO Throw away since articleGroup.getArticles()?
        int discountedQuantity = totalQuantity / buy * ( buy - payFor);
        int notDiscountedQuantity = totalQuantity - discountedQuantity;

        for ( int i = sortedItems.size()-1; i >= notDiscountedQuantity; i--) {
            ConcreteArticle article = sortedItems.get(i);
            article.setDiscountAmount(article.getPrice());
        }
    }

    public void applyMembershipCampaign() {
        double membershipDiscount = getMembershipDiscount();

        for (ConcreteArticle article: articles) {
            article.setDiscountAmount(article.getPrice() * membershipDiscount);
        }
    }

    private ArrayList<ConcreteArticle> getArticlesSorted(ArrayList<ConcreteArticle> list) {

        Collections.sort(list, (article1, article2) -> {
            double price1 = article1.getPrice();
            double price2 = article2.getPrice();

            return Double.compare(price1, price2);
        });
        return articles;
    }

    //public void registerDiscountCode(String code) {
    //    discountCodes.add(code);
    //}
}
