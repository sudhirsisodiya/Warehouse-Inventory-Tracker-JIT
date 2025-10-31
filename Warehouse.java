import java.util.*;

public class Warehouse {
    private final String name;
    private final Map<String, Product> inventory = new HashMap<>();
    private final List<StockObserver> observers = new ArrayList<>();

    public Warehouse(String name) {
        this.name = name;
    }

    public void addObserver(StockObserver observer) {
        observers.add(observer);
    }

    public void addProduct(Product product) {
        inventory.put(product.getId(), product);
        System.out.println("✅ " + name + " added product: " + product);
    }

    public void receiveShipment(String productId, int quantity) throws ProductNotFoundException {
        Product p = inventory.get(productId);
        if (p == null) throw new ProductNotFoundException("Product not found: " + productId);
        p.increaseStock(quantity);
        System.out.println("📦 Shipment received in " + name + ": " + quantity + " units of " + p.getName());
    }

    public void fulfillOrder(String productId, int quantity)
            throws ProductNotFoundException, InsufficientStockException {
        Product p = inventory.get(productId);
        if (p == null) throw new ProductNotFoundException("Product not found: " + productId);

        p.decreaseStock(quantity);
        System.out.println("🧾 Order fulfilled from " + name + ": " + quantity + " " + p.getName());

        if (p.getQuantity() < p.getReorderThreshold()) {
            for (StockObserver observer : observers)
                observer.onLowStock(name, p);
        }
    }

    public void showInventory() {
        System.out.println("\n🏭 " + name + " Inventory:");
        for (Product p : inventory.values()) {
            System.out.println("   → " + p);
        }
    }
}
