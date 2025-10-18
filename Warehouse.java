import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Warehouse {
    private final String name;
    private final Map<String, Product> products = new ConcurrentHashMap<>();
    private final List<StockObserver> observers = Collections.synchronizedList(new ArrayList<>());

    public Warehouse(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Warehouse name required");
        this.name = name;
    }

    public String getName() { return name; }

    /**
     * Add a product. If a product with same id exists, throws IllegalArgumentException.
     */
    public void addProduct(Product product) {
        Objects.requireNonNull(product);
        Product prev = products.putIfAbsent(product.getId(), product);
        if (prev != null) {
            throw new IllegalArgumentException("Product with ID already exists: " + product.getId());
        }
        // optional: if added product is already below threshold, notify observers
        checkAndNotify(product);
    }

    /**
     * Receive a shipment (increase quantity). Throws ProductNotFoundException for invalid id.
     */
    public void receiveShipment(String productId, int amount) throws ProductNotFoundException {
        Product p = products.get(productId);
        if (p == null) throw new ProductNotFoundException(productId);
        p.increase(amount);
        // After increase no need to alert (unless negative thresholds, but not expected)
    }

    /**
     * Fulfill an order (decrease quantity). Throws ProductNotFoundException or InsufficientStockException.
     */
    public void fulfillOrder(String productId, int amount) throws ProductNotFoundException, InsufficientStockException {
        Product p = products.get(productId);
        if (p == null) throw new ProductNotFoundException(productId);
        p.decrease(amount);
        checkAndNotify(p);
    }

    public Product getProduct(String productId) {
        return products.get(productId);
    }

    public Collection<Product> listProducts() {
        return Collections.unmodifiableCollection(products.values());
    }

    public void registerObserver(StockObserver observer) {
        observers.add(Objects.requireNonNull(observer));
    }

    public void unregisterObserver(StockObserver observer) {
        observers.remove(observer);
    }

    private void checkAndNotify(Product p) {
        if (p.getQuantity() < p.getReorderThreshold()) {
            // notify all observers
            synchronized (observers) {
                for (StockObserver obs : observers) {
                    try {
                        obs.onLowStock(name, p);
                    } catch (Exception e) {
                        System.err.println("Observer threw exception: " + e.getMessage());
                    }
                }
            }
        }
    }

   
    // Simple persistence (CSV)
    /**
     * Save inventory to a simple CSV text file. Overwrites if exists.
     */
    public void saveToFile(Path path) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Product p : products.values()) {
            lines.add(p.toCsv());
        }
        Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Load inventory from CSV file. Existing products are cleared.
     */
    public void loadFromFile(Path path) throws IOException {
        products.clear();
        if (!Files.exists(path)) return;
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            Product p = Product.fromCsv(line);
            products.put(p.getId(), p);
        }
    }
}
