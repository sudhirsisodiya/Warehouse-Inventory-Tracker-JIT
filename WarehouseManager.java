import java.util.*;

public class WarehouseManager {
    private final Map<String, Warehouse> warehouses = new HashMap<>();
    private final AlertService alertService = new AlertService();

    public void addWarehouse(String name) {
        Warehouse w = new Warehouse(name);
        w.addObserver(alertService);
        warehouses.put(name, w);
        System.out.println("🏬 Warehouse created: " + name);
    }

    public Warehouse getWarehouse(String name) {
        return warehouses.get(name);
    }

    public void showAllInventories() {
        System.out.println("\n============================");
        System.out.println("📊 All Warehouse Inventories");
        System.out.println("============================");
        for (Warehouse w : warehouses.values()) {
            w.showInventory();
        }
    }
}
