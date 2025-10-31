
public interface StockObserver {
    void onLowStock(String warehouseName, Product product);
}
