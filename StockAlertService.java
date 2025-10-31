package service;

import model.Product;

public interface AlertService {
    void onLowStock(Product product);
}