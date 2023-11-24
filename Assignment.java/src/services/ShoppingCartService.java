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

    public void addItemToOrder(int orderLineNumber, String productCode,
                           int quantity,
                        double orderLineCost , int orderNumber) {
        cartItems.add(new OrderLine(orderLineNumber, productCode, quantity,
                orderLineCost, orderNumber));

    }

    public void removeOrderLine(Product productCode) {



    }
}