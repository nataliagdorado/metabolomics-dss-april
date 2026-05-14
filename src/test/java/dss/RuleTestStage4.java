package dss;

import adduct.Adduct;
import lipid.Peak;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.drools.ruleunits.api.RuleUnitInstance;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class RuleTestStage4 {

    @Test
    public void testFuzzyLogicScoring() {
        System.out.println("STARTING STAGE 4: RULE-BASED ADDUCT EVALUATION");

        Adduct adductMH = new Adduct("M+H", 1.007276, 1, 1);
        Adduct adductMNa = new Adduct("M+Na", 22.989218, 1, 1);

        Peak peakMH = new Peak("P1", 90.055, 1000, 2.0);
        Peak peakMNa = new Peak("P2", 112.037, 344, 2.0);

        LabelledPeak lpMH = new LabelledPeak(peakMH, adductMH);
        LabelledPeak lpMNa = new LabelledPeak(peakMNa, adductMNa);

        Feature feature = new Feature(89.047, "M+H");
        LabelledAdductFeature laf = new LabelledAdductFeature(feature, 89.047, "M+H");
        laf.addLabelledPeak(lpMH);
        laf.addLabelledPeak(lpMNa);

        Compound alanine = new Compound("Alanine", "C3H7NO2", 89.0476, -2.85, "InChI=...");

        Annotation annotation = new Annotation(laf, alanine);
        ScoredAnnotation scoredAnnotation = new ScoredAnnotation(annotation);

        // DROOLS EXECUTION
        Stage4Unit unit = new Stage4Unit();
        unit.getScoredAnnotations().add(scoredAnnotation);

        RuleUnitInstance<Stage4Unit> instance = RuleUnitProvider.get().createRuleUnitInstance(unit);

        try {
            System.out.println("Firing Drools Rules...");
            instance.fire();

            // RESULTADOS
            List<ScoredAnnotation> results = instance.executeQuery("GetScoredAnnotations").toList("$sa");

            System.out.println("\nRESULTS ");
            for (ScoredAnnotation sa : results) {
                System.out.println("Compound: " + sa.getAnnotation().getCompound().getName());
                System.out.println("Adduct Score (Fuzzy Logic): " + sa.getScoreAdduct());
            }

            assertNotNull(results);

        } finally {
            instance.close();
        }
    }
}