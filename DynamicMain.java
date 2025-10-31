import java.util.Scanner;

public class DynamicMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        WarehouseManager manager = new WarehouseManager();

        while (true) {
            System.out.println("\n========= Warehouse Inventory Menu =========");
            System.out.println("1. Add Warehouse");
            System.out.println("2. Add Product to Warehouse");
            System.out.println("3. Receive Shipment");
            System.out.println("4. Fulfill Order");
            System.out.println("5. Show All Inventories");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // newline clear

            switch (choice) {
                case 1:
                    System.out.print("Enter warehouse name: ");
                    String wName = sc.nextLine();
                    manager.addWarehouse(wName);
                    break;

                case 2:
                    System.out.print("Enter warehouse name: ");
                    String wname2 = sc.nextLine();
                    Warehouse w = manager.getWarehouse(wname2);
                    if (w == null) {
                        System.out.println("❌ Warehouse not found!");
                        break;
                    }
                    System.out.print("Enter product ID: ");
                    String pid = sc.nextLine();
                    System.out.print("Enter product name: ");
                    String pname = sc.nextLine();
                    System.out.print("Enter quantity: ");
                    int qty = sc.nextInt();
                    System.out.print("Enter reorder threshold: ");
                    int threshold = sc.nextInt();
                    sc.nextLine();
                    w.addProduct(new Product(pid, pname, qty, threshold));
                    break;

                case 3:
                    System.out.print("Enter warehouse name: ");
                    String wh1 = sc.nextLine();
                    Warehouse w1 = manager.getWarehouse(wh1);
                    if (w1 == null) {
                        System.out.println("❌ Warehouse not found!");
                        break;
                    }
                    System.out.print("Enter product ID: ");
                    String pid1 = sc.nextLine();
                    System.out.print("Enter shipment quantity: ");
                    int shipQty = sc.nextInt();
                    sc.nextLine();
                    w1.receiveShipment(pid1, shipQty);
                    break;

                case 4:
                    System.out.print("Enter warehouse name: ");
                    String wh2 = sc.nextLine();
                    Warehouse w2 = manager.getWarehouse(wh2);
                    if (w2 == null) {
                        System.out.println("❌ Warehouse not found!");
                        break;
                    }
                    System.out.print("Enter product ID: ");
                    String pid2 = sc.nextLine();
                    System.out.print("Enter order quantity: ");
                    int orderQty = sc.nextInt();
                    sc.nextLine();
                    w2.fulfillOrder(pid2, orderQty);
                    break;

                case 5:
                    manager.showAllInventories();
                    break;

                case 6:
                    System.out.println("👋 Exiting system. Bye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice, try again!");
            }
        }
    }
}

