public class Product {
    private String id;
    private String name;
    private int quantity;
    private int threshold;

    public Product(String id, String name, int quantity, int threshold) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.threshold = threshold;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getThreshold() {
        return threshold;
    }

    public void increaseStock(int qty) {
        this.quantity += qty;
    }

    public void decreaseStock(int qty) {
        if (qty > quantity) {
            System.out.println("⚠️ Not enough stock to fulfill the order!");
            return;
        }
        this.quantity -= qty;
    }

    public boolean isLowStock() {
        return quantity <= threshold;
    }

    @Override
    public String toString() {
        return "Product[id=" + id + ", name=" + name + ", qty=" + quantity + ", threshold=" + threshold + "]";
    }
}
