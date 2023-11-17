package src.order;

import src.product.*;

public class OrderLine {
    private int orderLineNumber;
    private Product item;
    private int quantity;

    public int getOrderLineNumber() {
        return orderLineNumber;
    }

    public Product getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
}
