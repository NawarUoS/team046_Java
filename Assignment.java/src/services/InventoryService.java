package src.services;
import src.account.*;
import src.product.*;

import java.util.*;

public class InventoryService {
    public void addStock(Account member, Product productName, Integer changeVal) throws IllegalArgumentException {
        List<UserRole> roles = member.getUserRoles();
        if (!roles.contains(UserRole.STAFF)) {

        }
        try {
            Integer current = productName.getStockLevel();
            Integer newLevel = current + changeVal;
            productName.setStockLevel(newLevel);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid Name or value to add");
        }
    }

    public void removeStock(Account member, Product productName, Integer changeVal) throws IllegalArgumentException {
        List<UserRole> roles = member.getUserRoles();
        if (!roles.contains(UserRole.STAFF)) {
        }
        try {
            Integer current = productName.getStockLevel();
            if (current >= changeVal) {
                Integer newLevel = current - changeVal;
                productName.setStockLevel(newLevel);
            } else {
                System.err.println("Trying to remove a greater value of stock than exists");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid Name or value to add");
        }
    }

    public void addControllerToInventory(Account member, String brand, String name, String code, 
                            Double price, String gauge, int scale, int stock, Boolean isDig) throws IllegalArgumentException{
        try {
            Controller object = new Controller(code, name, brand, price, gauge, stock, isDig);
        } catch (IllegalArgumentException e) {
            System.err.println("One or more input fields is invalid");
        }
    }

    public void addLocomotiveToInventory(Account member, String brand, String name, String code, 
                            Double price, String gauge, int scale, int stock, List<Integer> eras, String ddcCode) throws IllegalArgumentException{
        try {
            Locomotive object = new Locomotive(code, name, brand, price, gauge, stock, eras, ddcCode);
        } catch (IllegalArgumentException e) {
            System.err.println("One or more input fields is invalid");
        }
    }

    public void addRollingStockToInventory(Account member, String brand, String name, String code, 
                            Double price, String gauge, int scale, int stock, List<Integer> eras) throws IllegalArgumentException{
        try {
            RollingStock object = new RollingStock(code, name, brand, price, gauge, stock, eras);
        } catch (IllegalArgumentException e) {
            System.err.println("One or more input fields is invalid");
        }
    }

    public void addTrackToInventory(Account member, String brand, String name, String code, 
                            Double price, String gauge, int scale, int stock, String trackName, String type ) throws IllegalArgumentException{
        try {
            Track object = new Track(code, name, brand, price, gauge, stock);
        } catch (IllegalArgumentException e) {
            System.err.println("One or more input fields is invalid");
        }
    }

    public void addTrackPackToInventory(Account member, String brand, String name, String code, 
                            Double price, String gauge, int scale, int stock, List<Track> contents) throws IllegalArgumentException{
        try {
            TrackPack object = new TrackPack(code, name, brand, price, gauge, stock, contents);
        } catch (IllegalArgumentException e) {
            System.err.println("One or more input fields is invalid");
        }
    }

    public void addTrainSetToInventory(Account member, String brand, String name, String code, 
                            Double price, String gauge, int scale, int stock,List<Locomotive> trainContent, List<TrackPack> trackContent,
                            List<RollingStock> rollingContent, Controller controller) throws IllegalArgumentException{
        try {
            TrainSet object = new TrainSet(code, name, brand, price, gauge, stock, trainContent, trackContent, rollingContent, controller);
        } catch (IllegalArgumentException e) {
            System.err.println("One or more input fields is invalid");
        }
    }
}
