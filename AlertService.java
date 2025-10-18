public class AlertService implements StockObserver {
    private final String name;

    public AlertService(String name) {
        this.name = name;
    }

    @Override
    public void onLowStock(String warehouseName, Product product) {
        // simple alert printed to console; real implementations could send emails/SMS/etc.
        System.out.printf("[ALERT - %s] Low stock for '%s' (ID=%s) in warehouse '%s' — only %d left (threshold %d)%n",
                name, product.getName(), product.getId(), warehouseName, product.getQuantity(), product.getReorderThreshold());
    }
}
