package main;

import java.util.List;
import java.util.ArrayList;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.drools.ruleunits.api.RuleUnitInstance;
import dss.DatabaseManager;

import dss.Feature;
import dss.Annotation;
import dss.ScoredAnnotation;
import dss.Compound;
import dss.Stage3AnnotationService;
import dss.Stage4Unit;
import dss.Stage5Unit;
import dss.Stage6RankingService;
public class App {
    public static void main(String[] args) {
        System.out.println("METABOLOMICS DSS");


        System.out.println("[Stage 1 & 2] Processing raw features...");
        List<Feature> features = new ArrayList<>();
        features.add(new Feature("F1", 180.06339, 1000.0, 2.0)); // Encontrará D-PSICOSE y L-PSICOSE
        features.add(new Feature("F2", 161.0688, 800.0, 5.0));   // Encontrará L-2-aminoadipic acid
        features.add(new Feature("F3", 326.0395, 500.0, 8.0));   // Encontrará Gliotoxin


        // --- STAGE 3
        System.out.println("[Stage 3] Searching candidates in SQLite...");
        DatabaseManager dbManager = new DatabaseManager();
        Stage3AnnotationService stage3 = new Stage3AnnotationService(dbManager);
        List<Annotation> annotations = stage3.findCandidates(features);

        // Convertimos a ScoredAnnotations para empezar a puntuar
        List<ScoredAnnotation> scoredList = new ArrayList<>();
        for (Annotation ann : annotations) {
            scoredList.add(new ScoredAnnotation(ann));
        }

        // --- STAGE 4: Adduct evaluation (Drools)
        System.out.println("[Stage 4] Running Fuzzy Logic rules for Adducts...");
        Stage4Unit unit4 = new Stage4Unit();
        scoredList.forEach(unit4.getScoredAnnotations()::add);

        RuleUnitInstance<Stage4Unit> instance4 = RuleUnitProvider.get().createRuleUnitInstance(unit4);
        instance4.fire();
        instance4.close();

        // --- STAGE 5: Elution time (Drools)
        System.out.println("[Stage 5] Evaluating Elution Order (LogP vs RT)...");
        Stage5Unit unit5 = new Stage5Unit();
        scoredList.forEach(unit5.getScoredAnnotations()::add);

        RuleUnitInstance<Stage5Unit> instance5 = RuleUnitProvider.get().createRuleUnitInstance(unit5);
        instance5.fire();
        instance5.close();

        // --- STAGE 6: Ranking
        System.out.println("[Stage 6] Calculating weighted average and ranking...");
        Stage6RankingService stage6 = new Stage6RankingService();
        // Pesos: 60% Aductos, 40% RT
        stage6.calculateFinalScores(scoredList, 0.6, 0.4);

        // final
        System.out.println("\n" + "=".repeat(50));
        System.out.printf("%-15s | %-10s | %-10s | %-10s%n", "Compound", "Adduct S.", "RT Score", "FINAL SCORE");
        System.out.println("-".repeat(50));

        for (ScoredAnnotation sa : scoredList) {
            System.out.printf("%-15s | %-10.2f | %-10.2f | %-10.4f%n",
                    sa.getAnnotation().getCompound().getName(),
                    sa.getScoreAdduct() != null ? sa.getScoreAdduct() : 0.0,
                    sa.getScoreRT() != null ? sa.getScoreRT() : 0.0,
                    sa.getScoreFinal());
        }
        System.out.println("=".repeat(50));
    }
}