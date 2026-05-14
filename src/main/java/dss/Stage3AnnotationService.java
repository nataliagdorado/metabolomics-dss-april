package dss;

import java.util.ArrayList;
import java.util.List;

public class Stage3AnnotationService {

    private DatabaseManager dbManager;

    public Stage3AnnotationService(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public List<Annotation> generateAnnotations(List<LabelledAdductFeature> features, double tolerance) {
        List<Annotation> allAnnotations = new ArrayList<>();

        for (LabelledAdductFeature laf : features) {
            double targetMass = laf.getNeutralMass();

            // Search candidates in DB
            List<Compound> candidates = dbManager.searchByMass(targetMass, tolerance);

            // Create an Annotation for each matched compound
            for (Compound comp : candidates) {
                Annotation ann = new Annotation(laf, comp);
                allAnnotations.add(ann);
            }
        }
        return allAnnotations;
    }
}