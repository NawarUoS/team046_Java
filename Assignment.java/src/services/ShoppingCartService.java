package src.services;

import src.order.OrderLine;
import src.product.Product;

import java.util.*;

public class ShoppingCartService extends Service {

    private int currentUserID;
    List<OrderLine> cartItems;

    public ShoppingCartService() {
        cartItems = new ArrayList<>();
    }

    public void addItem(Product productCode,int quantity) {
        cartItems.add(new OrderLine(productCode, quantity));

    }

    public void removeOrderLine(Product productCode) {



    }
}