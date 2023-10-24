package org.example;

import org.junit.jupiter.api.Test;

public class InventoryReportTest {
    @Test
    void generateInventoryReport(){
        //Given
        Inventory inventory = new Inventory();
        Product product1 = new Product("Pen", 10, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("Notebook", 5, Product.ProductCategory.STANDARD, false);

        inventory.addProduct(product1, 10);
        inventory.addProduct(product2, 5);

        InventoryReport inventoryReport = new InventoryReport(inventory);
        String expectedOutput = ""; //Blablablabla skriv här


        //assertEquals



    }
}
