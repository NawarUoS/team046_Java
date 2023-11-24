package src.order;

import java.util.List;

public class Order {
    public OrderStatus orderStatus;
    private int orderNumber;
    private String orderDate;
    private double totalCost;
    private List<OrderLine> orderLines;

    public Order(OrderStatus orderStatus, int orderNumber, String orderDate,
                 double totalCost, List<OrderLine> orderLines) {
        this.orderStatus = orderStatus;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.orderLines = orderLines;
    }

    // Set methods
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    // Get methods
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    public int getOrderNumber() {
        return orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }
}
