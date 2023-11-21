package src.product;

public class Controller extends Product {
    private Boolean isDigital;

    public Controller(String brandName, String productName, String productCode, double productPrice, String gaugeType,
    int modelScale, int stockLevel, Boolean isDigital) {
        super(brandName, productName, productCode, productPrice, gaugeType, modelScale, stockLevel);
        this.isDigital = isDigital;
    }

    public Boolean getDigital() {
        return isDigital;
    }
}
