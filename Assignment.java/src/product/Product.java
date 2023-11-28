package src.product;

public class Product {

    // Type definitions
    String productCode;
    private String brandName;
    private String productName; 
    private double productPrice;
    private String gaugeType;
    private int stockLevel;

    public Product(String productCode, String brandName, String productName,
                   double productPrice, String gaugeType, int stockLevel) {
        this.productCode = productCode;
        this.brandName = brandName;
        this.productName = productName;
        this.productPrice = productPrice;
        this.gaugeType = gaugeType;
        this.stockLevel = stockLevel;
    }

    // Getter methods
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
    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }
}
    