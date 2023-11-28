package src.product;

import java.util.List;

public class TrackPack extends Product {

    // List of the ID's of the track objects in the track pack as well as quantity
    // Format as List<String[productID, quantity]>
    private List<String[]> contents;

    // Creates a trackPack traditionally
    public TrackPack(String productCode, String brandName, String productName,
                     double productPrice, String gaugeType, int stockLevel,
                     List<String[]> contents) {
        super(productCode, brandName, productName, productPrice, gaugeType, stockLevel);
        this.contents = contents;
    }

    // Creates a TrackPack when given a product and a contents list
    public TrackPack(Product genProduct, List<String[]> contents) {
        super(genProduct.getProductCode(), genProduct.getBrandName(),
                genProduct.getProductName(), genProduct.getProductPrice(),
                genProduct.getGaugeType(), genProduct.getStockLevel());
        this.contents = contents;
    }

    // Getter methods
    public List<String[]> getContents() {
        return contents;
    }

    // Setter methods
    public void setContents(List<String[]> contents) {
        this.contents = contents;
    }

}
