package src.product;

public class Track extends Product {
    
    // private String trackName;
    // private String trackType;

    public Track(String productCode, String brandName, String productName, double productPrice, String gaugeType,
            int modelScale, int stockLevel) {
        super(productCode, brandName, productName, productPrice, gaugeType, modelScale, stockLevel);
    }
}
