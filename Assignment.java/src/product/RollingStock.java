package src.product;

import java.util.*;

public class RollingStock extends Product {

    //Type definitions
    private List<Integer> eraCode;

    // Constructs a RollingStock object traditionally
    public RollingStock(String productCode, String brandName, String productName,
                        double productPrice, String gaugeType,
            int stockLevel, List<Integer> eraCode) {
        super(productCode, brandName, productName, productPrice, gaugeType, stockLevel);
        this.eraCode = eraCode;
    }

    // Constructs a RollingStock object when given a product object and a list
    // integers for era
    public RollingStock(Product genProduct, List<Integer> eraCode) {
        super(genProduct.getProductCode(), genProduct.getBrandName(),
                genProduct.getProductName(), genProduct.getProductPrice(),
                genProduct.getGaugeType(), genProduct.getStockLevel());
        this.eraCode = eraCode;
    }

    //Getter methods
    public List<Integer> getEraCode() {
        return eraCode;
    }
}
