public class AlertService {
    public void sendAlert(String warehouseName, Product product) {
        System.out.println("⚠️ ALERT: Low stock for " + product.getName() +
                " in " + warehouseName + " warehouse – only " + product.getQuantity() +
                " left (threshold " + product.getThreshold() + ")");
    }
}

