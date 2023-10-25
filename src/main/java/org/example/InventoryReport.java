package org.example;

import java.util.Map;

class InventoryReport {
    private Inventory inventory;

    InventoryReport(Inventory inventory) {
        this.inventory = inventory;
    }



    //Rapporteringmetod1
    void generateInventoryReport() {
        Map<Product, Integer> inventoryData = inventory.getInventory();
        System.out.println("Lagersaldorapport:");
        for (Map.Entry<Product, Integer> entry : inventoryData.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(product.getName() + ": " + quantity);
        }
    }

    //Rapporteringsmetod2
    // generateReport for X products -Timor

    //etc
}
