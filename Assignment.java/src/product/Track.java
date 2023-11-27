package src.product;

import java.util.List;

public class Track extends Product {
    
    // private String trackName;
    // private String trackType;

    public Track(String productCode, String brandName, String productName, double productPrice, String gaugeType,
            int stockLevel) {
        super(productCode, brandName, productName, productPrice, gaugeType, stockLevel);
    }

    public Track(Product genProduct) {
        super(genProduct.getProductCode(), genProduct.getBrandName(),
                genProduct.getProductName(), genProduct.getProductPrice(),
                genProduct.getGaugeType(), genProduct.getStockLevel());
    }
}
