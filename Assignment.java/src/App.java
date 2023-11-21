package src;

import src.product.*;
import src.order.*;
import src.account.*;

// This is just to test the classes, we will add actual meaningful code later
public class App {
    public static void main(String[] args) throws Exception {
        Product controller = new Controller("Dapol", "DCC Elite Controller", "C123", 12.0, "N", 148, 50, true);
        System.out.print(controller.getStockLevel());
    }
}
