package src;

import src.product.*;
import src.order.*;
import src.account.*;

// This is just to test the classes, we will add actual meaningful code later
public class App {
    public static void main(String[] args) throws Exception {
        Product track = new TrackPack();
        System.out.print(track.getStockLevel());
    }
}
