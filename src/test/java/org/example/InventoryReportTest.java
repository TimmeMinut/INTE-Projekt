package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryReportTest {
    @Test
    void generateInventoryValuationReport() {
        //Given 41250 + 4375 + 3018,75
        Inventory inventory = new Inventory();

        Product product1 = new Product("Pen", 10, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("Notebook", 5, Product.ProductCategory.STANDARD, false);
        Product product3 = new Product("Hat", 69, Product.ProductCategory.STANDARD, false);

        inventory.addProduct(product1, 20000);
        inventory.addProduct(product2, 990);
        inventory.addProduct(product3, 89);

        double totalPriceProduct1 = 10 * 1.25 * 20000;
        double totalPriceProduct2 = 5 * 1.25 * 990;
        double totalPriceProduct3 = 69 * 1.25 * 89;

        InventoryReport inventoryReport = new InventoryReport(inventory);



        // Jämför varje rad i den faktiska rapporten med den förväntade
        assertEquals(totalPriceProduct1 + totalPriceProduct2 + totalPriceProduct3, inventoryReport.generateInventoryValuationReport());
    }


}









