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

        OrderLine newOrderLine = new OrderLine(productCode, quantity);
        cartItems.add(newOrderLine);

    }

    public void removeOrderLine(Product productCode) {



    }
}