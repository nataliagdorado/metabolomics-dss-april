package dss;

public class Compound {
    private String id;
    private String name;
    private double monoisotopicMass;
    private String inchi;

    public Compound(String id, String name, double monoisotopicMass, String inchi) {
        this.id = id;
        this.name = name;
        this.monoisotopicMass = monoisotopicMass;
        this.inchi = inchi;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getMonoisotopicMass() { return monoisotopicMass; }
    public String getInchi() { return inchi; }

    @Override
    public String toString() {
        return "Compound{" + name + " (M=" + monoisotopicMass + ")}";
    }
}
