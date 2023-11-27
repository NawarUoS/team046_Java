package src.product;

import java.util.*;

public class RollingStock extends Product {

    private List<Integer> eraCode;

    public RollingStock(String productCode, String brandName, String productName, double productPrice, String gaugeType,
            int stockLevel, List<Integer> eraCode) {
        super(productCode, brandName, productName, productPrice, gaugeType, stockLevel);
        this.eraCode = eraCode;
    }

    public RollingStock(Product genProduct, List<Integer> eraCode) {
        super(genProduct.getProductCode(), genProduct.getBrandName(),
                genProduct.getProductName(), genProduct.getProductPrice(),
                genProduct.getGaugeType(), genProduct.getStockLevel());
        this.eraCode = eraCode;
    }

    //Get methods
    public List<Integer> getEraCode() {
        return eraCode;
    }
}
