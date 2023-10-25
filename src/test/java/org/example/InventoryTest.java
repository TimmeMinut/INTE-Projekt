package org.example;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryTest {


    @Test
    void Add_product(){
        //Given
        Inventory inventory = new Inventory();
        Product product = new Product("Pen", 10, Product.ProductCategory.STANDARD, false );

        //When
        inventory.addProduct(product, 1);

        //Then
        assertEquals(Pair.of(product, 1), inventory.getProduct(product));

    }

    @Test
    void Remove_product(){

        //Given
        Inventory inventory = new Inventory();
        Product product = new Product("Pen", 10, Product.ProductCategory.STANDARD, false );

        //When
        inventory.addProduct(product, 1);
        inventory.removeProduct(product, 1);

        assertEquals(Pair.of(product, 0), inventory.getProduct(product));

    }

    @Test
    void Get_inventory(){
        //Given
        Inventory inventory = new Inventory();
        Product product1 = new Product("Pen", 10, Product.ProductCategory.STANDARD, false);
        Product product2 = new Product("Notebook", 5, Product.ProductCategory.STANDARD, false);

        inventory.addProduct(product1, 10);
        inventory.addProduct(product2, 5);

        // When
        Map<Product, Integer> inventoryMap = inventory.getInventory();

        // Then
        assertEquals(2, inventoryMap.size()); // Kontrollera att det finns två produkter i inventory
        assertEquals(10, inventoryMap.get(product1)); // Kontrollera att rätt mängd av produkt 1 finns i inventory
        assertEquals(5, inventoryMap.get(product2)); // Kontrollera att rätt mängd av produkt 2 finns i inventory
    }
}









