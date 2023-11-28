package src.product;

public class Controller extends Product {

    // Type definitions
    private Boolean isDigital;

    //Constructs a Controller object traditionally
    public Controller(String productCode, String brandName, String productName,
                      double productPrice, String gaugeType, int stockLevel,
                      Boolean isDigital) {
        super(productCode, brandName, productName, productPrice, gaugeType, stockLevel);
        this.isDigital = isDigital;
    }

    // Constructs a controller object when given a generic product object and
    // unique fields
    public Controller(Product genProduct, Boolean isDigital) {
        super(genProduct.getProductCode(), genProduct.getBrandName(), 
        genProduct.getProductName(), genProduct.getProductPrice(), 
        genProduct.getGaugeType(), genProduct.getStockLevel());
        this.isDigital = isDigital;
    }

    // Getter method
    public Boolean getDigital() {
        return isDigital;
    }
}
