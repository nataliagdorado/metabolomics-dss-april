
package dss;

import java.util.List;
import adduct.Adduct;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.drools.ruleunits.api.RuleUnitInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RuleTest {

    static final Logger LOG = LoggerFactory.getLogger(RuleTest.class);

    @Test
    public void test() {
        LOG.info("Creating RuleUnit");
        // CREO UNA INSTANCIA DE MEASUREMENT UNIT
        MeasurementUnit measurementUnit = new MeasurementUnit();

        RuleUnitInstance<MeasurementUnit> instance = RuleUnitProvider.get().createRuleUnitInstance(measurementUnit);
        try {
            LOG.info("Insert data");
            measurementUnit.getMeasurements().add(new Measurement("color", "red"));
            measurementUnit.getMeasurements().add(new Measurement("color", "green"));
            measurementUnit.getMeasurements().add(new Measurement("color", "blue"));

            LOG.info("Run query. Rules are also fired");
            List<Measurement> queryResult = instance.executeQuery("FindColor").toList("$m");

            assertEquals(3, queryResult.size());
            System.out.println(queryResult);
            assertTrue("contains red", measurementUnit.getControlSet().contains("red"));
            assertTrue("contains green", measurementUnit.getControlSet().contains("green"));
            assertTrue("contains blue", measurementUnit.getControlSet().contains("blue"));
        } finally {
            instance.close();
        }
    }



    @Test
    public void testFeatureGeneration() {
        LOG.info("Iniciando Fase 1: Deconvolución");

        // 1. Instanciar tu unidad de la práctica
        FeatureUnit unit = new FeatureUnit();
        RuleUnitInstance<FeatureUnit> instance = RuleUnitProvider.get().createRuleUnitInstance(unit);

        try {
            // --- LISTA DE ADUCTOS POSITIVOS ---
            unit.getKnownAdducts().add(new Adduct("M+H", 1.007276, 1, 1));
            unit.getKnownAdducts().add(new Adduct("M+2H", 1.007276, 2, 1)); // Doble carga
            unit.getKnownAdducts().add(new Adduct("M+Na", 22.989218, 1, 1));
            unit.getKnownAdducts().add(new Adduct("M+K", 38.963158, 1, 1));
            unit.getKnownAdducts().add(new Adduct("M+NH4", 18.033823, 1, 1));
            unit.getKnownAdducts().add(new Adduct("M+H-H2O", -17.0032, 1, 1));
            unit.getKnownAdducts().add(new Adduct("M+H+NH4", 9.52055, 1, 1));
            unit.getKnownAdducts().add(new Adduct("2M+H", 1.007276, 1, 2)); // Dímero
            unit.getKnownAdducts().add(new Adduct("2M+Na", 22.989218, 1, 2));
            unit.getKnownAdducts().add(new Adduct("M+H+HCOONa", 68.9946, 1, 1));
            unit.getKnownAdducts().add(new Adduct("2M+H-H2O", -17.0032, 1, 2));

// --- LISTA DE ADUCTOS NEGATIVOS ---
            unit.getKnownAdducts().add(new Adduct("M-H", -1.007276, -1, 1));
            unit.getKnownAdducts().add(new Adduct("M+Cl", 34.969402, -1, 1));
            unit.getKnownAdducts().add(new Adduct("M+HCOOH-H", 44.998201, -1, 1));
            unit.getKnownAdducts().add(new Adduct("M-H-H2O", -19.01839, -1, 1));
            unit.getKnownAdducts().add(new Adduct("2M-H", -1.007276, -1, 2));
            unit.getKnownAdducts().add(new Adduct("M-2H", -1.007276, -2, 1));
            unit.getKnownAdducts().add(new Adduct("M+F+H", 20.00623, -1, 1));

// 3. Cargar Picos desde el CSV
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("peak-feature-30.csv"))) {
                String line;
                br.readLine(); // Saltamos la cabecera (Peak, Feature, Adduct...)
                while ((line = br.readLine()) != null) {
                    String[] d = line.split(",");

                    // Extraemos solo lo que nuestra clase Peak necesita:
                    // id = d[0], mz = d[3], intensity = d[4], rt = d[5]
                    Peak p = new Peak(
                            d[0],
                            Double.parseDouble(d[3]),
                            Double.parseDouble(d[4]),
                            Double.parseDouble(d[5])
                    );

                    unit.getPeaks().add(p);
                }
                System.out.println(">>> CSV cargado con éxito.");
            } catch (Exception e) {
                LOG.error("Error al leer el CSV: " + e.getMessage());
            }

            // 4. Ejecutar reglas y ver resultados
            instance.fire();

            // Usamos una query para ver cuántas features se han generado
            List<Feature> results = instance.executeQuery("GetFeatures").toList("$f");
            System.out.println(">>> Total de Features generadas: " + results.size());

        } finally {
            instance.close();
        }
    }
}