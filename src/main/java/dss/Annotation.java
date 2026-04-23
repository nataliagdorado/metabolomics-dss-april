package dss;

public class Annotation {
    private LabelledAdductFeature feature; // La señal que detectó el equipo
    private Compound compound;             // El nombre que hemos encontrado en la DB

    public Annotation(LabelledAdductFeature feature, Compound compound) {
        this.feature = feature;
        this.compound = compound;
    }

    // Getters
    public LabelledAdductFeature getLabelledFeature() { return feature; }
    public Compound getCompound() { return compound; }


}