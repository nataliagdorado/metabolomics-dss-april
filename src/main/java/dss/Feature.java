package dss;

import java.util.ArrayList;
import java.util.List;

public class Feature {
    private double monoisotopicMass; // La M calculada
    private double rt;               // El RT medio del grupo. HA DICHO DE METER UNA MEDIANA.
    private List<Peak> groupedPeaks; // Los picos que forman esta feature

    public Feature(double monoisotopicMass, double rt) {
        this.monoisotopicMass = monoisotopicMass;
        this.rt = rt;
        this.groupedPeaks = new ArrayList<>();
    }

    public void addPeak(Peak peak) {
        this.groupedPeaks.add(peak);
    }

    // Getters necesarios para que el motor y el test vean los resultados
    public double getMonoisotopicMass() { return monoisotopicMass; }
    public double getRt() { return rt; }
    public List<Peak> getGroupedPeaks() { return groupedPeaks; }
}