package src.Order;

import java.util.List;

public class Order {
    private enum Status {};
    private int orderNumber;
    private String orderDate;
    private double totalCost;
    private List<OrderLine> orderLines;
}
