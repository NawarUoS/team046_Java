package src.product;

public class Track extends Product {
    
    private String trackName;
    private String trackType;

    public Track(String brandName, String productName, String productCode, double productPrice, String gaugeType,
            int modelScale, int stockLevel, String trackName, String trackType) {
        super(brandName, productName, productCode, productPrice, gaugeType, modelScale, stockLevel);
        this.trackName = trackName;
        this.trackType = trackType;
    }

    //Get methods
    public String getTrackName() {
        return trackName;
    }

    public String getTrackType() {
        return trackType;
    }
}
