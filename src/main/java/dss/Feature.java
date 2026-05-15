package dss;

import lipid.Peak; // Asegúrate de que el import de Peak coincide con tu paquete
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Feature {
    private String id;
    private double monoisotopicMass;
    private double intensity;
    private double rt;               // Mediana calculada o asignada directamente
    private String adductLabel;
    private List<Peak> groupedPeaks;

    public Feature(double monoisotopicMass, String adductLabel) {
        this.monoisotopicMass = monoisotopicMass;
        this.adductLabel = adductLabel;
        this.groupedPeaks = new ArrayList<>();
    }

    public Feature(String id, double monoisotopicMass, double intensity, double rt) {
        this.id = id;
        this.monoisotopicMass = monoisotopicMass;
        this.intensity = intensity;
        this.rt = rt;
        this.groupedPeaks = new ArrayList<>();
    }

    public void addPeak(Peak peak) {
        if (peak != null) {
            this.groupedPeaks.add(peak);
            calculateRepresentativeRT();
            updateMaxIntensity();
        }
    }

    private void calculateRepresentativeRT() {
        if (groupedPeaks == null || groupedPeaks.isEmpty()) return;

        groupedPeaks.sort(Comparator.comparingDouble(Peak::getRt));
        int size = groupedPeaks.size();

        if (size % 2 == 0) {
            this.rt = (groupedPeaks.get(size / 2 - 1).getRt() + groupedPeaks.get(size / 2).getRt()) / 2.0;
        } else {
            this.rt = groupedPeaks.get(size / 2).getRt();
        }
    }

    private void updateMaxIntensity() {
        if (groupedPeaks != null && !groupedPeaks.isEmpty()) {
            this.intensity = groupedPeaks.stream()
                    .mapToDouble(Peak::getIntensity)
                    .max()
                    .orElse(0.0);
        }
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public double getMonoisotopicMass() { return monoisotopicMass; }
    public void setMonoisotopicMass(double monoisotopicMass) { this.monoisotopicMass = monoisotopicMass; }

    public double getIntensity() { return intensity; }
    public void setIntensity(double intensity) { this.intensity = intensity; }

    public double getRt() { return rt; }
    public void setRt(double rt) { this.rt = rt; }

    public String getAdductLabel() { return adductLabel; }
    public void setAdductLabel(String adductLabel) { this.adductLabel = adductLabel; }

    public List<Peak> getGroupedPeaks() { return groupedPeaks; }
}