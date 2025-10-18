import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class Product implements Serializable {
    private final String id;
    private final String name;
    private final int reorderThreshold;
    // use AtomicInteger for thread-safe increments/decrements
    private final AtomicInteger quantity;

    public Product(String id, String name, int initialQuantity, int reorderThreshold) {
        if (id == null || id.isEmpty()) throw new IllegalArgumentException("id required");
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("name required");
        if (initialQuantity < 0) throw new IllegalArgumentException("initialQuantity >= 0");
        if (reorderThreshold < 0) throw new IllegalArgumentException("reorderThreshold >= 0");
        this.id = id;
        this.name = name;
        this.quantity = new AtomicInteger(initialQuantity);
        this.reorderThreshold = reorderThreshold;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity.get(); }
    public int getReorderThreshold() { return reorderThreshold; }

    /**
     * Increase stock by amount. amount must be positive.
     */
    public void increase(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("amount must be > 0");
        quantity.addAndGet(amount);
    }

    /**
     * Try to decrease stock by amount. Throws InsufficientStockException if not enough.
     */
    public void decrease(int amount) throws InsufficientStockException {
        if (amount <= 0) throw new IllegalArgumentException("amount must be > 0");
        while (true) {
            int current = quantity.get();
            int updated = current - amount;
            if (updated < 0) {
                throw new InsufficientStockException("Not enough stock for product " + id + " (" + name + "). Requested: " + amount + ", available: " + current);
            }
            if (quantity.compareAndSet(current, updated)) {
                return;
            }
            // else loop and retry
        }
    }

    @Override
    public String toString() {
        return String.format("Product[id=%s, name=%s, qty=%d, threshold=%d]", id, name, getQuantity(), reorderThreshold);
    }

    // CSV serialization: id,name,quantity,threshold (commas escaped are not handled here — keep names simple)
    public String toCsv() {
        return String.join(",", id, name, String.valueOf(getQuantity()), String.valueOf(reorderThreshold));
    }

    public static Product fromCsv(String csvLine) {
        String[] parts = csvLine.split(",", -1);
        if (parts.length != 4) throw new IllegalArgumentException("Invalid CSV: " + csvLine);
        String id = parts[0];
        String name = parts[1];
        int qty = Integer.parseInt(parts[2]);
        int threshold = Integer.parseInt(parts[3]);
        return new Product(id, name, qty, threshold);
    }
}
