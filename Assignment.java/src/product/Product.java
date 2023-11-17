package src.product;

public class Product {

    private String brandName;

    private String productName; 

    String productCode;

    private double productPrice;

    private String gaugeType;

    private int modelScale;

    private int stockLevel;

    // Getter method
    public String getBrandName() {
        return brandName;
    }
    public String getProductName() {
        return productName;
    }
    public String getProductCode() {
        return productCode;
    }
    public double getProductPrice() {
        return productPrice;
    }
    public String getGaugeType() {
        return gaugeType;
    }
    public int getModelScale() {
        return modelScale;
    }
    public int getStockLevel() {
        return stockLevel;
    }

    // Setter methods
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
    public void setGaugeType(String gaugeType) {
        this.gaugeType = gaugeType;
    }
    public void setModelScale(int modelScale) {
        this.modelScale = modelScale;
    }
    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }
}
    