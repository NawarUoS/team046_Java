package src.product;

import java.util.List;

public class Track extends Product {
    // Track is essentially the same as product, it exists as its own class
    // to make object manipulations easier

    // Creates a track object traditionally
    public Track(String productCode, String brandName, String productName,
                 double productPrice, String gaugeType, int stockLevel) {
        super(productCode, brandName, productName, productPrice, gaugeType, stockLevel);
    }

    // Creates a Track object when given a product object
    public Track(Product genProduct) {
        super(genProduct.getProductCode(), genProduct.getBrandName(),
                genProduct.getProductName(), genProduct.getProductPrice(),
                genProduct.getGaugeType(), genProduct.getStockLevel());
    }
}
