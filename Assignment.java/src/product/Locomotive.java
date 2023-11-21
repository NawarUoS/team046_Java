package src.product;
import java.util.*;

public class Locomotive extends Product {

    private List<Integer> eraCode;
    private String ddcCode;

    public Locomotive(String brandName, String productName, String productCode, double productPrice, String gaugeType,
            int modelScale, int stockLevel, List<Integer> eraCode, String ddcCode) {
        super(brandName, productName, productCode, productPrice, gaugeType, modelScale, stockLevel);
        this.eraCode = eraCode;
        this.ddcCode = ddcCode;
    }

    //Get methods
    public List<Integer> getEraCode() {
        return eraCode;
    }

    public String getDdcCode() {
        return ddcCode;
    }
}
