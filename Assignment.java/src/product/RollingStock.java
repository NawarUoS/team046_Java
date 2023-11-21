package src.product;

import java.util.*;

public class RollingStock extends Product {

    private List<Integer> eraCode;

    public RollingStock(String brandName, String productName, String productCode, double productPrice, String gaugeType,
            int modelScale, int stockLevel, List<Integer> eraCode) {
        super(brandName, productName, productCode, productPrice, gaugeType, modelScale, stockLevel);
        this.eraCode = eraCode;
    }

    //Get methods
    public List<Integer> getEraCode() {
        return eraCode;
    }
}
