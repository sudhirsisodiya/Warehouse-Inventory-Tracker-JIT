package service;

import model.Product;
import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    private final Map<String, Product> inventory = new HashMap<>();
    private final AlertService alertService;

    public Warehouse(AlertService alertService) {
        if (alertService == null) {
            throw new IllegalArgumentException("AlertService cannot be null.");
        }
        this.alertService = alertService;
    }

    public void addProduct(Product product) {
        if (product == null) {
            System.out.println("Error: Product cannot be null.");
            return;
        }

        String productId = product.getProductId();
        if (productId == null || productId.trim().isEmpty()) {
            System.out.println("Error: Product ID cannot be empty.");
            return;
        }

        if (inventory.containsKey(productId)) {
            System.out.println("Error: A product with ID '" + productId + "' already exists.");
            return;
        }

        inventory.put(productId, product);
        System.out.println("Product added successfully: " + product.getName());
    }

    public void receiveShipment(String productId, int quantity) {
        if (isInvalidProductId(productId) || isInvalidQuantity(quantity, "shipment")) {
            return;
        }

        Product product = inventory.get(productId);
        if (product == null) {
            System.out.println("Error: Product with ID '" + productId + "' not found in inventory.");
            return;
        }

        product.setQuantity(product.getQuantity() + quantity);
        System.out.println("Shipment received for " + product.getName() +
                " | Updated Quantity: " + product.getQuantity());
    }

    public void fulfillOrder(String productId, int quantity) {
        if (isInvalidProductId(productId) || isInvalidQuantity(quantity, "order")) {
            return;
        }

        Product product = inventory.get(productId);
        if (product == null) {
            System.out.println("Error: Product with ID '" + productId + "' not found.");
            return;
        }

        if (product.getQuantity() < quantity) {
            System.out.println("Error: Insufficient stock for " + product.getName() +
                    " | Available: " + product.getQuantity() + ", Required: " + quantity);
            return;
        }

        product.setQuantity(product.getQuantity() - quantity);
        System.out.println("Order fulfilled for " + product.getName() +
                " | Remaining Quantity: " + product.getQuantity());

        if (product.getQuantity() < product.getReorderThreshold()) {
            alertService.onLowStock(product);
        }
    }

    public void viewInventory() {
        System.out.println("\n--- Current Inventory ---");
        if (inventory.isEmpty()) {
            System.out.println("No products available in inventory.");
            return;
        }

        for (Product product : inventory.values()) {
            System.out.println(product);
        }
    }

    private boolean isInvalidProductId(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            System.out.println("Error: Product ID cannot be empty.");
            return true;
        }
        return false;
    }

    private boolean isInvalidQuantity(int quantity, String action) {
        if (quantity <= 0) {
            System.out.println("Error: " + capitalize(action) + " quantity must be greater than zero.");
            return true;
        }
        return false;
    }

    private String capitalize(String text) {
        return text == null || text.isEmpty()
                ? ""
                : text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }
}