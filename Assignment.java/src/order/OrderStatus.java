package src.order;

public enum OrderStatus {
    PENDING,
    CONFIRMED,
    FULFILLED,
    BLOCKED;

    public String enumToString(OrderStatus orderStatus) {
        return "";
    }

    public static OrderStatus stringToEnum(String status) throws Error {
        switch (status) {
            case "P":
                return OrderStatus.PENDING;  
            case "C":
                return OrderStatus.CONFIRMED; 
            case "F":
                return OrderStatus.FULFILLED;
            case "B":
                return OrderStatus.BLOCKED;
            default:
                break;
        }
        throw new Error("Invalid order status");
    }
}
