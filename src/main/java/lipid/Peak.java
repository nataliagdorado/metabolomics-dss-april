package lipid;

public class Peak {
    private final String id;      // El ID (P1, P2...) que leemos del CSV
    private final double mz;
    private final double intensity;
    private final double rt;      // ¡Faltaba el RT aquí!

    public Peak(String id, double mz, double intensity, double rt) {
        this.id = id;
        this.mz = mz;
        this.intensity = intensity;
        this.rt = rt;
    }

    // Getters exactos para que el Test y Drools funcionen
    public String getId() { return id; }
    public double getMz() { return mz; }
    public double getIntensity() { return intensity; }
    public double getRt() { return rt; }

    @Override
    public String toString() {
        return "Peak " + id + " (mz=" + mz + ")";
    }
}