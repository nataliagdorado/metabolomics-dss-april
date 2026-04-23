package dss;

import adduct.Adduct;
import lipid.Peak;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.drools.ruleunits.api.RuleUnitInstance;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class RuleTestStage3 {

    @Test
    public void testDatabaseAnnotation() {
        // 1. CONFIGURACIÓN E INICIALIZACIÓN (Igual que Stage 2)
        FeatureUnit unit = new FeatureUnit();
        RuleUnitInstance<FeatureUnit> instance = RuleUnitProvider.get().createRuleUnitInstance(unit);

        // Añadimos aductos mínimos para la prueba
        unit.getKnownAdducts().add(new Adduct("M+H", 1.007276, 1, 1));
        unit.getKnownAdducts().add(new Adduct("M+Na", 22.989218, 1, 1));

        // Añadimos un par de picos que sabemos que darán una masa conocida (ej: Dopamina ~153.07)
        // Pico 1: m/z 154.0862 (M+H) -> Masa Neutra: 153.0789
        unit.getPeaks().add(new Peak("P1", 154.0862, 500000, 2.0));
        // Pico 2: m/z 176.0681 (M+Na) -> Masa Neutra: 153.0789
        unit.getPeaks().add(new Peak("P2", 176.0681, 200000, 2.1));

        try {
            // 2. EJECUCIÓN STAGE 1 y 2 (Generación de Features y Masas Neutras)
            instance.fire();

            // Recuperamos las Labelled Features
            List<LabelledAdductFeature> features = instance.executeQuery("GetLabelledFeatures").toList("$lf");

            // 3. EJECUCIÓN STAGE 3 (Búsqueda en Base de Datos)
            DatabaseManager dbManager = new DatabaseManager();
            List<Annotation> allAnnotations = new ArrayList<>();
            double tolerancia = 0.01; // Tolerancia en Daltons

            System.out.println("\n=== INICIANDO ANOTACIÓN (STAGE 3) ===");

            for (LabelledAdductFeature laf : features) {
                double masaABuscar = laf.getNeutralMass();

                // Buscamos candidatos en la DB
                List<Compound> candidatos = dbManager.searchByMass(masaABuscar, tolerancia);

                System.out.println("Feature con Masa " + String.format("%.4f", masaABuscar) +
                        " -> Encontrados " + candidatos.size() + " compuestos.");

                // Por cada compuesto encontrado, creamos una Anotación
                for (Compound comp : candidatos) {
                    Annotation ann = new Annotation(laf, comp);
                    allAnnotations.add(ann);
                }
            }

            // 4. VERIFICACIÓN DE RESULTADOS
            System.out.println("\n=== RESULTADOS FINALES DE ANOTACIÓN ===");
            for (Annotation ann : allAnnotations) {
                System.out.printf("ANOTACIÓN: [Feature Masa %.4f] se asocia con [Compuesto: %s (%s)]%n",
                        ann.getLabelledFeature().getNeutralMass(),
                        ann.getCompound().getName(),
                        ann.getCompound().getFormula());
            }

            // Validaciones
            assertNotNull("La lista de anotaciones no debería ser nula", allAnnotations);
            assertFalse("Debería haberse encontrado al menos una anotación en la DB", allAnnotations.isEmpty());

        } finally {
            instance.close();
        }
    }
}