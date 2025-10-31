import java.util.HashMap;

public class Warehouse {
    private String name;
    private HashMap<String, Product> products = new HashMap<>();

    public Warehouse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addProduct(Product p) {
        products.put(p.getId(), p);
        System.out.println("✅ " + name + " added product: " + p);
    }

    public void receiveShipment(String productId, int qty) {
        Product p = products.get(productId);
        if (p != null) {
            p.increaseStock(qty);
            System.out.println("📦 Received " + qty + " units of " + p.getName() + " in " + name);
        } else {
            System.out.println("❌ Product not found in this warehouse!");
        }
    }

    public void fulfillOrder(String productId, int qty) {
        Product p = products.get(productId);
        if (p != null) {
            p.decreaseStock(qty);
            System.out.println("🧾 Order fulfilled from " + name + ": " + qty + " " + p.getName());
            if (p.isLowStock()) {
                System.out.println("⚠️ ALERT: Low stock for " + p.getName() +
                        " in " + name + " warehouse – only " + p.getQuantity() +
                        " left (threshold " + p.getThreshold() + ")");
            }
        } else {
            System.out.println("❌ Product not found in this warehouse!");
        }
    }

    public void showInventory() {
        System.out.println("\n📍 Inventory of " + name + " warehouse:");
        if (products.isEmpty()) {
            System.out.println("   (No products found)");
        } else {
            for (Product p : products.values()) {
                System.out.println("   " + p);
            }
        }
    }
}

