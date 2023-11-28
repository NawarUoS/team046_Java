package src.product;
import java.util.*;

public class Locomotive extends Product {

    // Type definitions
    private List<Integer> eraCode;
    private String dccCode;

    // Construct a locomotive traditionally
    public Locomotive(String productCode, String brandName, String productName,
                      double productPrice, String gaugeType,
                      int stockLevel, List<Integer> eraCode, String dccCode) {
        super(productCode, brandName, productName, productPrice, gaugeType, stockLevel);
        this.eraCode = eraCode;
        this.dccCode = dccCode;
    }

    // Constructs a Locomotive object by being given a product object and
    // unique fields
    public Locomotive(Product genProduct, List<Integer> eraCode, String dccCode) {
        super(genProduct.getProductCode(), genProduct.getBrandName(), 
        genProduct.getProductName(), genProduct.getProductPrice(), 
        genProduct.getGaugeType(), genProduct.getStockLevel());
        this.eraCode = eraCode;
        this.dccCode = dccCode;
    }


    //Getter methods
    public List<Integer> getEraCode() {
        return eraCode;
    }

    public String getDdcCode() {
        return dccCode;
    }
}
