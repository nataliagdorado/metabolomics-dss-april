package lipid;

public class Peak {
    private final String id;
    private final double mz;
    private final double intensity;
    private final double rt;

    public Peak(String id, double mz, double intensity, double rt) {
        this.id = id;
        this.mz = mz;
        this.intensity = intensity;
        this.rt = rt;
    }

    public String getId() { return id; }
    public double getMz() { return mz; }
    public double getIntensity() { return intensity; }
    public double getRt() { return rt; }

    @Override
    public String toString() {
        return "Peak " + id + " (mz=" + mz + ")";
    }
}