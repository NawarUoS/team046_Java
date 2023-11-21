package src.product;

import java.util.List;

public class TrackPack extends Product {

    private List<Track> contents;

    public TrackPack(String brandName, String productName, String productCode, double productPrice, String gaugeType,
            int modelScale, int stockLevel, List<Track> contents) {
        super(brandName, productName, productCode, productPrice, gaugeType, modelScale, stockLevel);
        this.contents = contents;
    }

    public List<Track> getContents() {
        return contents;
    }

    public void setContents(List<Track> contents) {
        this.contents = contents;
    }

}
