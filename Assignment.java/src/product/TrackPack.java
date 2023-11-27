package src.product;

import java.util.List;

public class TrackPack extends Product {

    private List<Track> contents;

    public TrackPack(String productCode, String brandName, String productName, double productPrice, String gaugeType,
            int stockLevel, List<Track> contents) {
        super(productCode, brandName, productName, productPrice, gaugeType, stockLevel);
        this.contents = contents;
    }

    public List<Track> getContents() {
        return contents;
    }

    public void setContents(List<Track> contents) {
        this.contents = contents;
    }

}
