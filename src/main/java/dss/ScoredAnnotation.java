package dss;

public class ScoredAnnotation {
    private Annotation annotation;
    private Double scoreAdduct;
    private Double scoreRT;
    private Double scoreFinal;

    public ScoredAnnotation(Annotation annotation) {
        this.annotation = annotation;
        this.scoreAdduct = null;
        this.scoreRT = null;
        this.scoreFinal = null;
    }

    public Annotation getAnnotation() { return annotation; }

    public Double getScoreAdduct() { return scoreAdduct; }
    public void setScoreAdduct(Double scoreAdduct) { this.scoreAdduct = scoreAdduct; }

    public Double getScoreRT() { return scoreRT; }
    public void setScoreRT(Double scoreRT) { this.scoreRT = scoreRT; }

    public Double getScoreFinal() { return scoreFinal; }
    public void setScoreFinal(Double scoreFinal) { this.scoreFinal = scoreFinal; }
}