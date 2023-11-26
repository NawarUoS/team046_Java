package src.product;
import java.util.*;

public class Locomotive extends Product {

    private List<Integer> eraCode;
    private String dccCode;

    public Locomotive(String productCode, String brandName, String productName, double productPrice, String gaugeType,
            int modelScale, int stockLevel, List<Integer> eraCode, String dccCode) {
        super(productCode, brandName, productName, productPrice, gaugeType, modelScale, stockLevel);
        this.eraCode = eraCode;
        this.dccCode = dccCode;
    }

    //Get methods
    public List<Integer> getEraCode() {
        return eraCode;
    }

    public String getDdcCode() {
        return dccCode;
    }
}
