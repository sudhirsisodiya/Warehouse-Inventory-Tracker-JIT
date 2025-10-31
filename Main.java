public class Main {
    public static void main(String[] args) {
        try {
            Warehouse warehouse = new Warehouse();
            AlertService alertService = new AlertService();

            warehouse.addObserver(alertService);

            // Step 1: Add Product
            Product laptop = new Product("P001", "Laptop", 0, 5);
            warehouse.addProduct(laptop);

            // Step 2: Receive Shipment
            warehouse.receiveShipment("P001", 10);

            // Step 3: Fulfill Orders
            warehouse.fulfillOrder("P001", 6); // Remaining = 4 -> Alert triggers

            // Step 4: View Inventory
            warehouse.showInventory();

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
