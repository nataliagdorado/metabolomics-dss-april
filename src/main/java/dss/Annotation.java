package dss;

import lipid.Lipid;
import lipid.Peak;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * Clase Annotation movida al paquete dss.
 * Mantiene toda la lógica original para que el programa funcione como ayer.
 */
public class Annotation {

    private final Lipid lipid;
    private final double mz;
    private final double intensity;
    private final double rtMin;
    private String adduct;
    private final Set<Peak> groupedSignals;
    private int score = 0;

    public Annotation(Lipid lipid, double mz, double intensity, double retentionTime, Set<Peak> groupedSignals) {
        this.lipid = lipid;
        this.mz = mz;
        this.rtMin = retentionTime;
        this.intensity = intensity;
        // Mantenemos el TreeSet para que las señales estén ordenadas
        this.groupedSignals = new TreeSet<>(groupedSignals);
    }

    // --- GETTERS Y SETTERS ORIGINALES ---
    public Lipid getLipid() { return lipid; }
    public double getMz() { return mz; }
    public double getRtMin() { return rtMin; }
    public String getAdduct() { return adduct; }
    public void setAdduct(String adduct) { this.adduct = adduct; }
    public double getIntensity() { return intensity; }
    public Set<Peak> getGroupedSignals() { return Collections.unmodifiableSet(groupedSignals); }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public void addScore(int delta) {
        this.score += delta;
    }

    // --- TUS MÉTODOS DE COMPARACIÓN Y VISUALIZACIÓN ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Annotation)) return false;
        Annotation that = (Annotation) o;
        return Double.compare(that.mz, mz) == 0 &&
                Double.compare(that.rtMin, rtMin) == 0 &&
                Objects.equals(lipid, that.lipid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lipid, mz, rtMin);
    }

    @Override
    public String toString() {
        return String.format("Annotation(%s, mz=%.4f, RT=%.2f, adduct=%s, intensity=%.1f, score=%d)",
                lipid.getName(), mz, rtMin, adduct, intensity, score);
    }
}