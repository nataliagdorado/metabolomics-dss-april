package dss;
import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;

import java.util.ArrayList;
import java.util.List;

public class LabelledAdductFeature {
    private Feature originalFeature;
    private List<LabelledPeak> labelledPeaks = new ArrayList<>();
    private double neutralMass; // neutral mass of each peak
    private String mainAdduct;  // name of the most intense peak

    public LabelledAdductFeature(Feature feature, double neutralMass, String mainAdduct) {
        this.originalFeature = feature;
        this.neutralMass = neutralMass;
        this.mainAdduct = mainAdduct;
    }



    private DataStore<LabelledAdductFeature> labelledFeatures = DataSource.createStore();
    public DataStore<LabelledAdductFeature> getLabelledFeatures() {
        return labelledFeatures;
    }
    public void addLabelledPeak(LabelledPeak lp) {
        this.labelledPeaks.add(lp);
    }
    public List<LabelledPeak> getLabelledPeaks() {
        return labelledPeaks;
    }
    // FOR STAGE 3
    public double getNeutralMass() { return neutralMass; }
    public String getMainAdduct() { return mainAdduct; }
}