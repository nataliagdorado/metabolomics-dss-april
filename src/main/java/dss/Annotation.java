package dss;

public class Annotation {
    private Feature feature;
    private Compound compound;
    private experimentalMonoisotopicMass;

    public Annotation(Feature f, Compound c) {
        this.feature = f;
        this.compound = c;
    }
}