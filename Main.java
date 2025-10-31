public class Main {
    public static void main(String[] args) {
        try {
            WarehouseManager manager = new WarehouseManager();

            // Add multiple warehouses
            manager.addWarehouse("Mumbai");
            manager.addWarehouse("Delhi");
            manager.addWarehouse("Chennai");

            Warehouse mumbai = manager.getWarehouse("Mumbai");
            Warehouse delhi = manager.getWarehouse("Delhi");
            Warehouse chennai = manager.getWarehouse("Chennai");

            // Add same product (Laptop) but with different quantities and thresholds
            mumbai.addProduct(new Product("P001", "Laptop", 4, 5));
            delhi.addProduct(new Product("P001", "Laptop", 10, 3));
            chennai.addProduct(new Product("P001", "Laptop", 1, 5));

            // Add different products to some warehouses
            delhi.addProduct(new Product("P002", "Mouse", 15, 8));
            mumbai.addProduct(new Product("P003", "Keyboard", 2, 3));

            // Fulfill some orders
            mumbai.fulfillOrder("P001", 2); // Laptop mumbai me alert trigger hoga
            delhi.fulfillOrder("P002", 10); // Mouse delhi me alert trigger hoga
            chennai.fulfillOrder("P001", 1); // Laptop chennai out of stock

            // Show all inventories
            manager.showAllInventories();

        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
}
