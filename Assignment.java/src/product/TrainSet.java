package src.product;

import java.util.List;

public class TrainSet extends Product {

    // Type defintion for content in the format List<String[ProductID, quantity]>
    private List<String[]> content;

    // Constructs a TrainSet object traditionally
    public TrainSet(String productCode, String brandName, String productName,
                    double productPrice, String gaugeType, int stockLevel,
                    List<String[]> content) {
        super(productCode, brandName, productName, productPrice, gaugeType, stockLevel);
        this.content = content;
    }

    // Constructs a TrainSet object when given a product object and a contents
    // list
    public TrainSet(Product genProduct, List<String[]> content) {
        super(genProduct.getProductCode(), genProduct.getBrandName(),
                genProduct.getProductName(), genProduct.getProductPrice(),
                genProduct.getGaugeType(), genProduct.getStockLevel());
        this.content = content;
    }

    // Getter methods
    public List<String[]> getContent() {
        return content;
    }

    // Setter methods
    public void setContent(List<String[]> content) {
        this.content = content;
    }
}
