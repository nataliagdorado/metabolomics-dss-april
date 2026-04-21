package dss;

import java.util.List;
import adduct.Adduct;
import lipid.Peak;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.drools.ruleunits.api.RuleUnitInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class RuleTest {

    static final Logger LOG = LoggerFactory.getLogger(RuleTest.class);

    @Test
    public void testFeatureGeneration() {
        LOG.info("Iniciando Fase 1 y 2: Deconvolución y Cálculo de Masa");

        // 1. Instanciar la unidad de la práctica
        FeatureUnit unit = new FeatureUnit();
        RuleUnitInstance<FeatureUnit> instance = RuleUnitProvider.get().createRuleUnitInstance(unit);

        try {
            // 2. CARGA DE ADUCTOS (Positivos y Negativos)
            unit.getKnownAdducts().add(new Adduct("M+H", 1.007276, 1, 1));
            unit.getKnownAdducts().add(new Adduct("M+2H", 1.007276, 2, 1));
            unit.getKnownAdducts().add(new Adduct("M+Na", 22.989218, 1, 1));
            unit.getKnownAdducts().add(new Adduct("M+K", 38.963158, 1, 1));
            unit.getKnownAdducts().add(new Adduct("M+NH4", 18.033823, 1, 1));
            unit.getKnownAdducts().add(new Adduct("M-H", -1.007276, -1, 1));
            unit.getKnownAdducts().add(new Adduct("M+Cl", 34.969402, -1, 1));
            unit.getKnownAdducts().add(new Adduct("2M-H", -1.007276, -1, 2));

            // 3. CARGA DE PICOS DESDE CSV
            // Asegúrate de que el archivo "peak-feature-30.csv" esté en la raíz del proyecto
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("peak-feature-30.csv"))) {
                String line;
                br.readLine(); // Saltamos cabecera
                while ((line = br.readLine()) != null) {
                    String[] d = line.split(",");
                    if (d.length >= 6) {
                        Peak p = new Peak(
                                d[0],                         // ID
                                Double.parseDouble(d[3]),      // m/z
                                Double.parseDouble(d[4]),      // Intensity
                                Double.parseDouble(d[5])       // RT
                        );
                        unit.getPeaks().add(p);
                    }
                }
                LOG.info(">>> CSV cargado con éxito.");
            } catch (Exception e) {
                LOG.error("Error al leer el CSV: " + e.getMessage());
            }

            // 4. EJECUCIÓN DE REGLAS
            LOG.info("Ejecutando motor de reglas...");
            instance.fire();

            // 5. RECUPERACIÓN Y VISUALIZACIÓN DE RESULTADOS
            List<Feature> results = instance.executeQuery("GetFeatures").toList("$f");

            System.out.println("\n==================================================");
            System.out.println("   REPORTE DE GENERACIÓN DE FEATURES (STAGE 1 & 2)");
            System.out.println("==================================================");
            System.out.println("Total de picos procesados: " + unit.getPeaks().getClass().getSimpleName());            System.out.println("Total de Features generadas: " + results.size());
            System.out.println("--------------------------------------------------");

            // Dentro del bucle de resultados en RuleTest.java
            for (Feature f : results) {
                // Fíjate que usamos %f para números decimales y %d para enteros
                System.out.printf("Feature [%s] | Masa: %.4f | RT: %.2f | Picos: %d%n",
                        f.getAdductLabel(),
                        f.getMonoisotopicMass(),
                        f.getRt(),
                        f.getGroupedPeaks().size());

                // El bucle de picos dentro de la Feature
                for (Peak p : f.getGroupedPeaks()) {
                    System.out.println("   -> " + p.getId() + " (mz: " + p.getMz() + ", rt: " + p.getRt() + ")");
                }
            }

            // Validaciones básicas de JUnit
            assertNotNull(results);
            assertFalse("No se han generado features, revisa los picos", results.isEmpty());
        } finally {
            instance.close();
        }
    }
}