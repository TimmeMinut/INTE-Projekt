package org.example;

public class Main {

    public static void main(String[] args) {

        Customer customer = new Customer("Bob","19991231-1234",15000_00, false);
        CheckoutSystem checkoutSystem = new CheckoutSystem(customer);
        checkoutSystem.addDiscountCampaign(Product.ProductCategory.STANDARD, 3, 2);
        checkoutSystem.addDiscountCampaign(Product.ProductCategory.BOOK, 4, 3);
        checkoutSystem.addDiscountCampaign(Product.ProductCategory.FOOD, 5, 3);




        int i = 5000;
        while (i > 0) {
            checkoutSystem.registerProduct(new Product("StandardProd " + i, i, Product.ProductCategory.STANDARD, false));
            i--;
        }

        int j = 5000;
        while (j> 0) {
            checkoutSystem.registerProduct(new Product("BookProd " + j, j, Product.ProductCategory.BOOK, false));
            j--;
        }

        int k = 5000;
        while (k> 0) {
            checkoutSystem.registerProduct(new Product("FoodProd " + k, k, Product.ProductCategory.FOOD, false));
            k--;
        }


        double total = checkoutSystem.getTotal();



    }
}
