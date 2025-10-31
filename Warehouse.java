import java.util.*;

public class Warehouse {
    private final Map<String, Product> products = new HashMap<>();
    private final List<StockObserver> observers = new ArrayList<>();

    public void addObserver(StockObserver observer) {
        observers.add(observer);
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
        System.out.println("✅ Product added: " + product);
    }

    public void receiveShipment(String productId, int quantity) throws ProductNotFoundException {
        Product product = products.get(productId);
        if (product == null) throw new ProductNotFoundException("Product not found: " + productId);
        product.increaseStock(quantity);
        System.out.println("📦 Shipment received: " + quantity + " units for " + product.getName());
    }

    public void fulfillOrder(String productId, int quantity)
            throws ProductNotFoundException, InsufficientStockException {
        Product product = products.get(productId);
        if (product == null) throw new ProductNotFoundException("Product not found: " + productId);

        product.decreaseStock(quantity);
        System.out.println("🧾 Order fulfilled: " + quantity + " units of " + product.getName());

        if (product.getQuantity() < product.getReorderThreshold()) {
            notifyObservers(product);
        }
    }

    private void notifyObservers(Product product) {
        for (StockObserver observer : observers) {
            observer.onLowStock(product);
        }
    }

    public void showInventory() {
        System.out.println("\n📊 Current Inventory:");
        for (Product p : products.values()) {
            System.out.println(p);
        }
    }
}
