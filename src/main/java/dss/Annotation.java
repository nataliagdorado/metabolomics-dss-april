package dss;

public class Annotation {
    private LabelledAdductFeature feature;
    private Compound compound;

    public Annotation(LabelledAdductFeature feature, Compound compound) {
        this.feature = feature;
        this.compound = compound;
    }

    public LabelledAdductFeature getLabelledFeature() { return feature; }
    public Compound getCompound() { return compound; }

    // Este método facilita la vida a Drools para el Stage 5
    public double getAnnotationRT() {
        if (feature != null && feature.getFeature() != null) {
            return feature.getFeature().getRt();
        }
        return 0.0;
    }
}