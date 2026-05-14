package dss;

import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;

public class Stage4Unit implements RuleUnitData {
    private final DataStore<ScoredAnnotation> scoredAnnotations = DataSource.createStore();

    public DataStore<ScoredAnnotation> getScoredAnnotations() {
        return scoredAnnotations;
    }
}