package src.Products;

import java.util.List;

public class TrainSet extends BoxedSet {
    private enum eraCode {};
    private String trainSetType;
    private List<String> contents;

    //Get methods
    public String getTrainSetType() {
        return trainSetType;
    }

    public List<String> getContents() {
        return contents;
    }
}
