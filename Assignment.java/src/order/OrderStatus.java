package src.order;

public enum OrderStatus {
    PENDING,
    CONFIRMED,
    FULFILLED;

    public String enumToString(OrderStatus orderStatus) {
        return "";
    }

    public OrderStatus stringToEnum(String string) {
        return OrderStatus.PENDING;
    }
}
