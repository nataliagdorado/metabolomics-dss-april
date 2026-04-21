package dss;

/**
 * Representa un compuesto químico de la base de datos de metabolitos.
 * Requisito para el Stage 3 y el cálculo de Elution Order en el Stage 5.
 */
public class Compound {
    private String name;               // Nombre del compuesto (ej: Dopamine) [cite: 118]
    private String formula;            // Fórmula química [cite: 119]
    private double monoisotopicMass;   // Masa exacta para comparar con tus picos [cite: 120]
    private double logp;               // Hidrofobicidad (vital para el Stage 5) [cite: 121, 153]
    private String inchi;              // Identificador químico estándar [cite: 122]

    public Compound(String name, String formula, double monoisotopicMass, double logp, String inchi) {
        this.name = name;
        this.formula = formula;
        this.monoisotopicMass = monoisotopicMass;
        this.logp = logp;
        this.inchi = inchi;
    }

    // --- Getters necesarios para que Drools haga los cálculos ---
    public String getName() { return name; }
    public String getFormula() { return formula; }
    public double getMonoisotopicMass() { return monoisotopicMass; }
    public double getLogp() { return logp; }
    public String getInchi() { return inchi; }

    @Override
    public String toString() {
        return String.format("%s (M=%.4f, logP=%.2f)", name, monoisotopicMass, logp);
    }
}