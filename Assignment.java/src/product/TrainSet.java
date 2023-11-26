package src.product;

import java.util.List;

public class TrainSet extends Product {
    
    private List<Locomotive> trainContent;
    private List<TrackPack> trackContent;
    private List<RollingStock> rollingContent;
    private Controller controller;

    public TrainSet(String productCode, String brandName, String productName, double productPrice, String gaugeType,
            int modelScale, int stockLevel, List<Locomotive> trainContent, List<TrackPack> trackContent,
            List<RollingStock> rollingContent, Controller controller) {
        super(productCode, brandName, productName, productPrice, gaugeType, modelScale, stockLevel);
        this.trainContent = trainContent;
        this.trackContent = trackContent;
        this.rollingContent = rollingContent;
        this.controller = controller;
    }

    public List<Locomotive> getTrainContent() {
        return trainContent;
    }
    public List<TrackPack> getTrackContent() {
        return trackContent;
    }
    public List<RollingStock> getRollingContent() {
        return rollingContent;
    }
    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
    public void setTrackContent(List<TrackPack> trackContent) {
        this.trackContent = trackContent;
    }
    public void setRollingContent(List<RollingStock> rollingContent) {
        this.rollingContent = rollingContent;
    }
    public void setTrainContent(List<Locomotive> trainContent) {
        this.trainContent = trainContent;
    }
}
