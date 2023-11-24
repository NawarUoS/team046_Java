package src.order;

public class OrderLine {
    private int orderLineNumber;
    private String productCode;
    private int quantity;
    private double orderLineCost;
    private int orderNumber;

    public OrderLine(int orderLineNumber, String productCode, int quantity, double orderLineCost , int orderNumber) {
        this.orderLineNumber = orderLineNumber;
        this.productCode = productCode;
        this.quantity = quantity;
        this.orderLineCost = orderLineCost;
        this.orderNumber = orderNumber;
    }

    public int getOrderLineNumber() {
        return orderLineNumber;
    }

    public String getProductCode() {
        return productCode;
    }
    
    public int getQuantity() {
        return quantity;
    }
    public double getOrderLineCost() {
        return orderLineCost;
    }

    public int getOrderNumber() {
        return orderNumber;
    }
}