package dss;

import java.util.List;
import adduct.Adduct;
import lipid.Peak;
import dss.Feature;
import dss.LabelledPeak;
import dss.LabelledAdductFeature; // Clase nueva del Stage 2
import org.drools.ruleunits.api.RuleUnitProvider;
import org.drools.ruleunits.api.RuleUnitInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class RuleTestStage2 {

    static final Logger LOG = LoggerFactory.getLogger(RuleTest.class);

    @Test
    public void testFeatureGeneration() {
        // i create a log object to print to know exact time
        LOG.info("STAGE 1 AND 2. DECONVOLUTION AND MASS CALCULATION");

        //1. create the feature unit creating the object. this is the class that contain dataStore
        FeatureUnit unit = new FeatureUnit();
        // access to the provider, creating the instance that is goibg to process the rules
        RuleUnitInstance<FeatureUnit> instance = RuleUnitProvider.get().createRuleUnitInstance(unit);

        try {
            // 2. ADDUCTS GIVEN IN THE DESCRIPTION
            // multicharged, m divided by that
            // --- ADUCTOS POSITIVOS ---
            unit.getKnownAdducts().add(new Adduct("M+H", 1.007276, 1, 1));
            unit.getKnownAdducts().add(new Adduct("M+2H", 1.007276, 2, 1));
            unit.getKnownAdducts().add(new Adduct("M+Na", 22.989218, 1, 1));
            unit.getKnownAdducts().add(new Adduct("M+K", 38.963158, 1, 1));
            unit.getKnownAdducts().add(new Adduct("M+NH4", 18.033823, 1, 1));
            unit.getKnownAdducts().add(new Adduct("M+H-H2O", -17.0032, 1, 1));
            unit.getKnownAdducts().add(new Adduct("M+H+NH4", 9.52055, 2, 1));
            unit.getKnownAdducts().add(new Adduct("2M+H", 1.007276, 1, 2));
            unit.getKnownAdducts().add(new Adduct("2M+Na", 22.989218, 1, 2));
            unit.getKnownAdducts().add(new Adduct("M+H+HCOONa", 68.9946, 1, 1));
            unit.getKnownAdducts().add(new Adduct("2M+H-H2O", -17.0032, 1, 2));

            // --- ADUCTOS NEGATIVOS ---
            unit.getKnownAdducts().add(new Adduct("M-H", -1.007276, -1, 1));
            unit.getKnownAdducts().add(new Adduct("M+Cl", 34.969402, -1, 1));
            unit.getKnownAdducts().add(new Adduct("M+HCOOH-H", 44.998201, -1, 1));
            unit.getKnownAdducts().add(new Adduct("M-H-H2O", -19.01839, -1, 1));
            unit.getKnownAdducts().add(new Adduct("2M-H", -1.007276, -1, 2));
            unit.getKnownAdducts().add(new Adduct("M-2H", -1.007276, -2, 1));
            unit.getKnownAdducts().add(new Adduct("M+F+H", 20.00623, -1, 1));

            // 3. UPLOAD OF THE CSV
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("peak-feature-30.csv"))) {
                String line;
                br.readLine(); //jump first title row
                while ((line = br.readLine()) != null) { //a while for reading all the lines
                    String[] d = line.split(","); // comma separator, each time it founds a comma it splits
                    if (d.length >= 6) { // solo crea el pico si tiene seis columnas
                        Peak p = new Peak(
                                d[0],// ID
                                Double.parseDouble(d[3]),// m/z
                                Double.parseDouble(d[4]),// Intensity
                                Double.parseDouble(d[5])// RT
                        );
                        unit.getPeaks().add(p); // IMPORTANT, we put the Peak n the DATASTORE
                    }
                }
                LOG.info("CSV UPLOAD COMPLETE");
            } catch (Exception e) {
                LOG.error("ERROR UPLOADING CVS: " + e.getMessage());
            }

            // DROOLS
            LOG.info("Drools working...");
            //la instance que hice arriba - llamo al metodo fire de RuleUnitInstance
            instance.fire();

            // 5. QUERIES - RECUPERACIÓN DE DATOS
            // Recuperamos las Features (Stage 1)
            List<Feature> results = instance.executeQuery("GetFeatures").toList("$f");

            // PARTE NUEVA
            //lf debe coincidir con el nombre query del .drl
            List<LabelledAdductFeature> labelledResults = instance.executeQuery("GetLabelledFeatures").toList("$lf");

            // PRINTS STAGE 1
            System.out.println("\nSTAGE 1: GRUPOS POR RT");
            System.out.println("Number of features generated: " + results.size());

            //  PRINTS STAGE 2
            System.out.println("\nSTAGE 2: DECONVOLUTION RESULTS");
            System.out.println("Number of labelled lipids: " + labelledResults.size());

            for (LabelledAdductFeature laf : labelledResults) {
                System.out.printf("NEUTRAL MASS: %.4f | MAIN ADDUCT: %s%n",
                        laf.getNeutralMass(),
                        laf.getMainAdduct());

                // PICOS ETIQUETADOS DENTRO
                for (LabelledPeak lp : laf.getLabelledPeaks()) {
                    System.out.println("   ID: " + lp.getOriginalPeak().getId() +
                                       " | Adduct: " + lp.getAssignedAdduct().getLabel() +
                                       " | Indiv. Mass: " + String.format("%.4f", lp.getMonoisotopicMass()));
                }
            }

            // ERRORES
            assertNotNull(labelledResults);
            assertFalse("No se han generado LabelledFeatures", labelledResults.isEmpty());

        } finally {
            instance.close();
        }
    }
}
