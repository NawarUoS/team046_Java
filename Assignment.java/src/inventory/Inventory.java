package src.inventory;

import src.product.*;

import java.util.HashMap;

public class Inventory {
    HashMap<String, Product> stock = new HashMap<>();

    public HashMap<String, Product> getStock() {
        return stock;
    }
}
