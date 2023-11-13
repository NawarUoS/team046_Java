package src.order;

import java.util.List;

public class Order {
    public enum Status {};
    private int orderNumber;
    private String orderDate;
    private double totalCost;
    private List<OrderLine> orderLines;
}
