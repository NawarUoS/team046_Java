package src.order;

import java.util.List;

public class Order {
    public OrderStatus status;
    private int orderNumber;
    private String orderDate;
    private double totalCost;
    private List<OrderLine> orderLines;

    public Order(OrderStatus status, int orderNumber, String orderDate,
                 double totalCost, List<OrderLine> orderLines) {
        this.status = status;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.orderLines = orderLines;
    }

    // Get methods
    public void setOrderStatus(OrderStatus status) {
        this.status = status;
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

