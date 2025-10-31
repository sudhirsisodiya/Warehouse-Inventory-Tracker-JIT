public class Product {
    private final String id;
    private String name;
    private int quantity;
    private int reorderThreshold;

    public Product(String id, String name, int quantity, int reorderThreshold) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.reorderThreshold = reorderThreshold;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public int getReorderThreshold() { return reorderThreshold; }

    public void increaseStock(int amount) {
        if (amount > 0) quantity += amount;
    }

    public void decreaseStock(int amount) throws InsufficientStockException {
        if (amount > quantity)
            throw new InsufficientStockException("Not enough stock for " + name);
        quantity -= amount;
    }

    @Override
    public String toString() {
        return String.format("Product[id=%s, name=%s, qty=%d, threshold=%d]", id, name, quantity, reorderThreshold);
    }
}
