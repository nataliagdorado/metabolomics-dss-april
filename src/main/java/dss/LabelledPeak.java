package dss;

import lipid.Peak;
import adduct.Adduct;

public class LabelledPeak {
    private Peak originalPeak;
    private Adduct assignedAdduct;
    private double monoisotopicMass; // (MZ - Delta)

    public LabelledPeak(Peak peak, Adduct adduct) {
        this.originalPeak = peak;
        this.assignedAdduct = adduct;
        this.monoisotopicMass = Adduct.getMonoisotopicMassFromMZ(peak.getMz(), adduct);
    }

    public Peak getOriginalPeak() { return originalPeak; }
    public Adduct getAssignedAdduct() { return assignedAdduct; }
    public double getMonoisotopicMass() { return monoisotopicMass; }
}