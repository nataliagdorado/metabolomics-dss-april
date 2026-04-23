package dss;

public class Compound {
    private String name;
    private String formula;
    private double monoisotopicMass;
    private double logp;
    private String inchi;

    public Compound(String name, String formula, double monoisotopicMass, double logp, String inchi) {
        this.name = name;
        this.formula = formula;
        this.monoisotopicMass = monoisotopicMass;
        this.logp = logp;
        this.inchi = inchi;
    }

    public String getName() { return name; }
    public String getFormula() { return formula; }
    public double getMonoisotopicMass() { return monoisotopicMass; }
    public double getLogp() { return logp; }
    public String getInchi() { return inchi; }
}