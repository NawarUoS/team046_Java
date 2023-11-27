package src.product;

public class Controller extends Product {
    private Boolean isDigital;

    public Controller(String productCode, String brandName, String productName, double productPrice, String gaugeType,
    int modelScale, int stockLevel, Boolean isDigital) {
        super(productCode, brandName, productName, productPrice, gaugeType, modelScale, stockLevel);
        this.isDigital = isDigital;
    }

    public Controller(Product genProduct, Boolean isDigital) {
        super(genProduct.getProductCode(), genProduct.getBrandName(), 
        genProduct.getProductName(), genProduct.getProductPrice(), 
        genProduct.getGaugeType(), genProduct.getModelScale(), 
        genProduct.getStockLevel());
        this.isDigital = isDigital;
    }

    public Boolean getDigital() {
        return isDigital;
    }
}
