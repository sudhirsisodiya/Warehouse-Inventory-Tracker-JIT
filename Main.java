import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Create two warehouses (bonus: multiple warehouses)
            Warehouse whA = new Warehouse("Central");
            Warehouse whB = new Warehouse("Overflow");

            // Register alert services (demonstrates Observer pattern)
            AlertService consoleAlert = new AlertService("ConsoleNotifier");
            whA.registerObserver(consoleAlert);
            whB.registerObserver(consoleAlert);

            // Add product "Laptop" with threshold 5
            Product laptop = new Product("P001", "Laptop", 0, 5);
            whA.addProduct(laptop);

            // Example workflow from prompt:
            System.out.println("=== Example workflow ===");
            whA.receiveShipment("P001", 10); // total = 10
            System.out.println("After shipment: " + whA.getProduct("P001"));
            whA.fulfillOrder("P001", 6);     // remaining = 4 -> triggers alert
            System.out.println("After fulfilling 6 orders: " + whA.getProduct("P001"));

            // Save state to file
            Path file = Paths.get("warehouse_central.csv");
            whA.saveToFile(file);
            System.out.println("Saved inventory to " + file.toAbsolutePath());

            // Demonstrate reload into a new Warehouse instance
            Warehouse reload = new Warehouse("Central_Reloaded");
            reload.registerObserver(consoleAlert);
            reload.loadFromFile(file);
            System.out.println("Reloaded products:");
            for (Product p : reload.listProducts()) {
                System.out.println(" - " + p);
            }

            // Multithreading simulation: simulate many concurrent orders and shipments
            System.out.println("\n=== Multithreaded simulation ===");
            Product phone = new Product("P002", "Phone", 50, 10);
            whA.addProduct(phone);

            ExecutorService ex = Executors.newFixedThreadPool(8);
            int tasks = 50;
            CountDownLatch latch = new CountDownLatch(tasks);
            for (int i = 0; i < tasks; i++) {
                final int taskNo = i;
                ex.submit(() -> {
                    try {
                        if (taskNo % 3 == 0) {
                            // shipment arrives sometimes
                            whA.receiveShipment("P002", 5);
                        } else {
                            try {
                                whA.fulfillOrder("P002", 3);
                            } catch (InsufficientStockException e) {
                                // handle gracefully: print and continue
                                System.out.println("Thread " + Thread.currentThread().getName() + " - " + e.getMessage());
                            }
                        }
                    } catch (ProductNotFoundException pnfe) {
                        System.err.println("Unexpected: " + pnfe.getMessage());
                    } finally {
                        latch.countDown();
                    }
                });
            }
            latch.await();
            ex.shutdown();
            System.out.println("Final state for Phone: " + whA.getProduct("P002"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
