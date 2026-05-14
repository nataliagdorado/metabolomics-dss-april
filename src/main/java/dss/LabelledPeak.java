package dss;

import lipid.Peak;
import adduct.Adduct;

public class LabelledPeak {
    private Peak originalPeak;
    private Adduct assignedAdduct;

    public LabelledPeak(Peak originalPeak, Adduct assignedAdduct) {
        this.originalPeak = originalPeak;
        this.assignedAdduct = assignedAdduct;
    }

    public Peak getOriginalPeak() { return originalPeak; }
    public Adduct getAssignedAdduct() { return assignedAdduct; }

    // Calculamos la masa monoisotópica individual al vuelo usando el aducto y la masa del pico
    public double getMonoisotopicMass() {
        return Adduct.getMonoisotopicMassFromMZ(originalPeak.getMz(), assignedAdduct);
    }
}