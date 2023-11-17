package src.product;

import java.util.List;

public class TrainSet extends Product {
    private enum eraCode {};
    private String trainSetType;
    private List<String> contents;

    //Getters
    public String getTrainSetType() {
        return trainSetType;
    }

    public List<String> getContents() {
        return contents;
    }

    //Setters
    public void setTrainSetType(String trainSetType) {
        this.trainSetType = trainSetType;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }
}
