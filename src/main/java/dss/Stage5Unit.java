package dss;

import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;

public class Stage5Unit implements RuleUnitData {
    private final DataStore<ScoredAnnotation> scoredAnnotations;

    public Stage5Unit() {
        this.scoredAnnotations = DataSource.createStore();
    }

    public DataStore<ScoredAnnotation> getScoredAnnotations() {
        return scoredAnnotations;
    }
}