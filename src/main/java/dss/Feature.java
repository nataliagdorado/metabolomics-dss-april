package dss;

import lipid.Peak;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Feature {
    private double monoisotopicMass;
    private double rt;               // Mediana
    private String adductLabel;
    private List<Peak> groupedPeaks;

    public Feature(double monoisotopicMass, String adductLabel) {
        this.monoisotopicMass = monoisotopicMass;
        this.adductLabel = adductLabel;
        this.groupedPeaks = new ArrayList<>();
    }

    public void addPeak(Peak peak) {
        if (peak != null) {
            this.groupedPeaks.add(peak);
            calculateRepresentativeRT();
        }
    }

    private void calculateRepresentativeRT() {
        if (groupedPeaks == null || groupedPeaks.isEmpty()) return;
        // Ordenamos por RT para la mediana
        groupedPeaks.sort(Comparator.comparingDouble(Peak::getRt));
        int size = groupedPeaks.size();
        if (size % 2 == 0) {
            this.rt = (groupedPeaks.get(size / 2 - 1).getRt() + groupedPeaks.get(size / 2).getRt()) / 2.0;
        } else {
            this.rt = groupedPeaks.get(size / 2).getRt();
        }
    }

    public double getMonoisotopicMass() { return monoisotopicMass; }
    public double getRt() { return rt; }
    public String getAdductLabel() { return adductLabel; }
    public List<Peak> getGroupedPeaks() { return groupedPeaks; }
}