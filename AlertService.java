public class AlertService implements StockObserver {
    @Override
    public void onLowStock(String warehouseName, Product product) {
        System.out.println("⚠️  ALERT: Low stock for " + product.getName() +
                " in " + warehouseName +
                " warehouse – only " + product.getQuantity() + " left (threshold " + product.getReorderThreshold() + ")");
    }
}
