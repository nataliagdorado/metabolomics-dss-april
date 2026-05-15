package dss;

import java.util.List;
import java.util.Comparator;

public class Stage6RankingService {
    public void calculateFinalScores(List<ScoredAnnotation> annotations, double wAdduct, double wRT) {
        for (ScoredAnnotation sa : annotations) {
            double sAdduct = (sa.getScoreAdduct() != null) ? sa.getScoreAdduct() : 0.0;
            double sRT = (sa.getScoreRT() != null) ? sa.getScoreRT() : 0.0;

            // Fórmula: (wA * sA + wRT * sRT) / (wA + wRT)
            double finalScore = (wAdduct * sAdduct + wRT * sRT) / (wAdduct + wRT);
            sa.setScoreFinal(finalScore);
        }
        //mayor a menor puntuación
        annotations.sort(Comparator.comparingDouble(ScoredAnnotation::getScoreFinal).reversed());
    }


}