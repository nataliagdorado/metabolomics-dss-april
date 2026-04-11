package dss;

import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;
import adduct.Adduct;

/**
 * Representa la Working Memory para la fase 1: Deconvolución.
 * Implementa RuleUnitData para actuar como interfaz de datos de Drools
 * DATA SOURCE. herramienta, reserva espacio de memoria. avisa al motor de reglas
 * data source es para crear la working memory
 */
public class FeatureUnit implements RuleUnitData {

    // Entrada:picos detectados
    private final DataStore<Peak> peaks;

    // Configuración: La lista de aductos conocidos para comparar mz
    private final DataStore<Adduct> knownAdducts;

    // Salida: resultados (las agrupaciones o features generadas)
    private final DataStore<Feature> features;

    public FeatureUnit() {
        this.peaks = DataSource.createStore();
        this.knownAdducts = DataSource.createStore();
        this.features = DataSource.createStore();
    }

    // Getters obligatorios para que Drools pueda leer los DataStores
    public DataStore<Peak> getPeaks() {
        return peaks;
    }

    public DataStore<Adduct> getKnownAdducts() {
        return knownAdducts;
    }

    public DataStore<Feature> getFeatures() {
        return features;
    }
}