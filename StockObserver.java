public interface StockObserver {
    /**
     * Called when a product's stock falls below its reorder threshold.
     *
     * @param warehouseName the warehouse in which this happened
     * @param product the product whose stock is low
     */
    void onLowStock(String warehouseName, Product product);
}
