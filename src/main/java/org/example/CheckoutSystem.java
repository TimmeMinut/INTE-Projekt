package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CheckoutSystem {
    private final Customer customer;
    private final Map<Product, Integer> basket = new HashMap<>();
    private final ArrayList<ConcreteArticle> articles = new ArrayList<>();
    private ArrayList<DiscountCampaign> discountCampaigns = new ArrayList<>();
    private final ArrayList<String> discountCodes = new ArrayList<>();

    public CheckoutSystem(Customer currentCustomer) {
        this.customer = currentCustomer;
        discountCampaigns.add( new DiscountCampaign(ProductCategory.BOOK, 3, 2));
    }

    public void registerProduct(Product product) {
        if (!basket.containsKey(product)) {
            basket.put(product, 1);
        } else {
            int quantity = basket.get(product);
            basket.put(product, ++quantity); // Skriver över tidigare quantity med +1
        }
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
        // Which campaign is worth more?
        double totalDiscountFromCampaigns = getDiscountFromCampaigns(); // Apply discountCompaigns, get discountAmount
        double totalDiscountFromMembership = getBasketValue() * ( 1 - getMembershipDiscount());

        // Biggest discount is applied
        if (totalDiscountFromCampaigns > totalDiscountFromMembership) {
            applyDiscountFromCampaigns();
        } else {
            applyMembershipCampaign();
        }

        applyVAT();

        return total;
    }

    public double getBasketValue() {
        double totalValue = 0;
        for(ConcreteArticle article: articles) {
            totalValue += article.getPrice();
        }
        return getBasketValue();
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

    private double getMembershipDiscount() { // ev. decorator senare?
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
        double appliedDiscountAmount = 0;
        // för varje kampanj, applicera rabatter
        for ( DiscountCampaign campaign: discountCampaigns) {
            appliedDiscountAmount += getDiscountFromCampaign(campaign);
        }
        return appliedDiscountAmount;
    }

    private double getDiscountFromCampaign(DiscountCampaign discountCampaign) {
        double appliedDiscountAmount = 0;

        ProductCategory productCategory = discountCampaign.getProductCategory();
        int buy = discountCampaign.getBuy();
        int payFor = discountCampaign.getBuy();

        ArrayList<ConcreteArticle> discountedItems = new ArrayList<>();
        for (ConcreteArticle article: articles) {
            if (article.getProductCategory().equals(productCategory)) {
                discountedItems.add(article);
            }
        }

        if(discountedItems.isEmpty()) {
            // TODO Felhantering?
        };

        ArrayList<ConcreteArticle> sortedItems = getArticlesSorted(discountedItems);

        int totalQuantity = articles.size(); // TODO Throw away since articleGroup.getArticles()?
        int notDiscountedQuantity = totalQuantity / buy * payFor;
        int restQuantity = totalQuantity % buy;

        // sätta rabatt på notdiscountedQuantity: de sista i listan
        for ( int i = sortedItems.size()-1; i >= notDiscountedQuantity; i--) {
            ConcreteArticle article = sortedItems.get(i);
            appliedDiscountAmount += article.getPrice();
            article.setDiscountAmount(article.getPrice());
        }

        //double originalPrice = super.getPrice() / totalQuantity;
        //return originalPrice * ( notDiscountedQuantity + restQuantity );
        return appliedDiscountAmount;
    }

    private void ApplyDiscountFromCampaigns() {
        // för varje kampanj, applicera rabatter
        for ( DiscountCampaign campaign: discountCampaigns) {
            appliedDiscountAmount += ApplyDiscountFromCampaigns(campaign);
        }
        return appliedDiscountAmount;
    }



    public void applyDiscountFromCampaigns(DiscountCampaign discountCampaign) {
        double appliedDiscountAmount = 0;

        ProductCategory productCategory = discountCampaign.getProductCategory();
        int buy = discountCampaign.getBuy();
        int payFor = discountCampaign.getBuy();

        ArrayList<ConcreteArticle> discountedItems = new ArrayList<>();
        for (ConcreteArticle article: articles) {
            if (article.getProductCategory().equals(productCategory)) {
                discountedItems.add(article);
            }
        }

        if(discountedItems.isEmpty()) {
            // TODO Felhantering?
        };

        ArrayList<ConcreteArticle> sortedItems = getArticlesSorted(discountedItems);

        int totalQuantity = articles.size(); // TODO Throw away since articleGroup.getArticles()?
        int notDiscountedQuantity = totalQuantity / buy * payFor;
        int restQuantity = totalQuantity % buy;

        // sätta rabatt på notdiscountedQuantity: de sista i listan
        for ( int i = sortedItems.size()-1; i >= notDiscountedQuantity; i--) {
            ConcreteArticle article = sortedItems.get(i);
            appliedDiscountAmount += article.getPrice();
            article.setDiscountAmount(article.getPrice());
        }

        //double originalPrice = super.getPrice() / totalQuantity;
        //return originalPrice * ( notDiscountedQuantity + restQuantity );
        return appliedDiscountAmount;
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
