import java.util.HashMap;

public class WarehouseManager {
    private HashMap<String, Warehouse> warehouses = new HashMap<>();

    public void addWarehouse(String name) {
        if (warehouses.containsKey(name)) {
            System.out.println("⚠️ Warehouse already exists!");
            return;
        }
        warehouses.put(name, new Warehouse(name));
        System.out.println("🏬 Warehouse created: " + name);
    }

    public Warehouse getWarehouse(String name) {
        return warehouses.get(name);
    }

    public void showAllInventories() {
        if (warehouses.isEmpty()) {
            System.out.println("❌ No warehouses created yet!");
        } else {
            for (Warehouse w : warehouses.values()) {
                w.showInventory();
            }
        }
    }
}
