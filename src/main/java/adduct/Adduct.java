package adduct;

public class Adduct {

    private String label;
    private double deltaM;
    private int charge; // añado charge z y multimer n. No puedo calcular M solo con la masa del aducto si la carga es 2 o un dimero
    private int multimer;

    public Adduct(String label, double deltaM, int charge, int multimer) {
        this.label = label;
        this.deltaM = deltaM;
        this.charge = charge;
        this.multimer = multimer;
    }

    /**
     * Calculate the mass to search depending on the adduct hypothesis
     *
     * @param mz mz medida por el espectrómetro
     * @param adduct objeto aducto con sus propiedades (masa, carga, multímero)
     *
     * @return la masa monoisotópica neutra (M) calculada
     */

    // este metodo recibe lo de arriba. tiene acceso atodo lo fisico de la molecula
    public static Double getMonoisotopicMassFromMZ(Double mz, Adduct adduct) {
        if (adduct == null) return null;

        double adductMass = adduct.getDeltaM();
        int z = adduct.getCharge();
        int n = adduct.getMultimer();

        /*
           mz = (n*M + adductMass) / z
           DESPEJANDO M:
           M = ((mz * z) - adductMass) / n
        */

        Double monoisotopicMass = ((mz * z) - adductMass) / n;

        return monoisotopicMass;
    }

    // GETTERS
    public String getLabel() { return label; }
    public double getDeltaM() { return deltaM; }
    public int getCharge() { return charge; }
    public int getMultimer() { return multimer; }

}