package dss;

import org.drools.ruleunits.api.RuleUnitProvider;
import org.drools.ruleunits.api.RuleUnitInstance;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RuleTestS5and6 {

    @Test
    public void testFinalRankingPipeline() {
        System.out.println("STARTING STAGE 5 & 6: ELUTION ORDER AND FINAL RANKING");

        // Compuesto A: Dopamina (LogP bajo: -2.5)
        Compound dopamine = new Compound("Dopamine", "C8H11NO2", 153.07, -2.5, "InChI=1");
        Feature f1 = new Feature(153.07, "M+H");
        LabelledAdductFeature laf1 = new LabelledAdductFeature(f1, 153.07, "M+H");
        ScoredAnnotation sa1 = new ScoredAnnotation(new Annotation(laf1, dopamine));
        sa1.setScoreAdduct(1.0);

        // Compuesto B: Acetilcolina (LogP más alto: 1.2)
        Compound acetylcholine = new Compound("Acetylcholine", "C7H16NO2", 146.11, 1.2, "InChI=2");

        Feature f2 = new Feature(146.11, "M+H");

        LabelledAdductFeature laf2 = new LabelledAdductFeature(f2, 146.11, "M+H");
        ScoredAnnotation sa2 = new ScoredAnnotation(new Annotation(laf2, acetylcholine));
        sa2.setScoreAdduct(0.8);

        // STAGE 5
        Stage5Unit unit = new Stage5Unit();
        unit.getScoredAnnotations().add(sa1);
        unit.getScoredAnnotations().add(sa2);

        RuleUnitInstance<Stage5Unit> instance = RuleUnitProvider.get().createRuleUnitInstance(unit);
        try {
            System.out.println("Firing Stage 5 Rules (logP vs RT)...");
            instance.fire();
        } finally {
            instance.close();
        }

        // STAGE 6
        List<ScoredAnnotation> listToRank = new ArrayList<>();
        listToRank.add(sa1);
        listToRank.add(sa2);

        Stage6RankingService stage6 = new Stage6RankingService();
        // weights: 50% aductos, 50% RT
        stage6.calculateFinalScores(listToRank, 0.5, 0.5);

        System.out.println("\nFINAL RANKED RESULTS (Stage 6) ");
        for (ScoredAnnotation sa : listToRank) {
            System.out.printf("Compound: %-15s | LogP: %5.2f | ScoreAdduct: %.2f | ScoreRT: %.2f | FINAL SCORE: %.4f%n",
                    sa.getAnnotation().getCompound().getName(),
                    sa.getAnnotation().getCompound().getLogp(),
                    sa.getScoreAdduct(),
                    sa.getScoreRT() != null ? sa.getScoreRT() : 0.0,
                    sa.getScoreFinal());
        }

        assertNotNull(listToRank.get(0).getScoreFinal());
        assertTrue("The list should be sorted by final score",
                listToRank.get(0).getScoreFinal() >= listToRank.get(1).getScoreFinal());
    }
}