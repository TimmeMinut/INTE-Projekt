package org.example;

import java.util.Map;

public class InventoryReport {
    private Inventory inventory;

    InventoryReport(Inventory inventory) {
        this.inventory = inventory;
    }



    Double generateInventoryValuationReport() {  Map<Product, Integer> productInventory = inventory.getInventory();

        double totalValuation = 0.0;

        StringBuilder report = new StringBuilder("Lagervärderingsrapport:\n");
        for (Map.Entry<Product, Integer> entry : productInventory.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double productValue = product.getPrice() * quantity;
            totalValuation += productValue;
            report.append(product.getName()).append(": ").append(quantity).append(" enheter, värde: ").append(productValue).append(" kr\n");
        }

        report.append("Totalt värde av lagret: ").append(totalValuation).append(" kr");

        System.out.println(report);
        return totalValuation;
    }
}


