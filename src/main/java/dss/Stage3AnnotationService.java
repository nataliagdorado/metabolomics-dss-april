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
    public List<dss.Annotation> findCandidates(List<Feature> features) {
        List<dss.Annotation> results = new ArrayList<>();

        for (Feature f : features) {
            List<Compound> candidates = dbManager.getCompoundsByMass(f.getMonoisotopicMass(), 0.05);

            for (Compound c : candidates) {
                LabelledAdductFeature laf = new LabelledAdductFeature(f, f.getMonoisotopicMass(), "M+H");
                dss.Annotation newAnnotation = new dss.Annotation(laf, c);
                results.add(newAnnotation);
            }
        }

        System.out.println("[Stage 3] Database search finished. Candidates found: " + results.size());
        return results;
    }

}