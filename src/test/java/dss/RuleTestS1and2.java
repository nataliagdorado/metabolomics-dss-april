package dss;

import java.util.ArrayList;
import java.util.List;
import adduct.Adduct;
import lipid.Peak;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class RuleTestS1and2 {

    static final Logger LOG = LoggerFactory.getLogger(RuleTestS1and2.class);

    @Test
    public void testFeatureGeneration() {
        LOG.info("STAGE 1 AND 2. DECONVOLUTION AND MASS CALCULATION");

        List<Adduct> knownAdducts = new ArrayList<>();
        List<Peak> peaks = new ArrayList<>();

        try {
            // 2. ADDUCTS GIVEN IN THE DESCRIPTION
            // --- ADUCTOS POSITIVOS ---
            knownAdducts.add(new Adduct("M+H", 1.007276, 1, 1));
            knownAdducts.add(new Adduct("M+2H", 1.007276, 2, 1));
            knownAdducts.add(new Adduct("M+Na", 22.989218, 1, 1));
            knownAdducts.add(new Adduct("M+K", 38.963158, 1, 1));
            knownAdducts.add(new Adduct("M+NH4", 18.033823, 1, 1));
            knownAdducts.add(new Adduct("M+H-H2O", -17.0032, 1, 1));
            knownAdducts.add(new Adduct("M+H+NH4", 9.52055, 2, 1)); // Ojo, revisa esta masa con tu profe si es 19.04...
            knownAdducts.add(new Adduct("2M+H", 1.007276, 1, 2));
            knownAdducts.add(new Adduct("2M+Na", 22.989218, 1, 2));
            knownAdducts.add(new Adduct("M+H+HCOONa", 68.9946, 1, 1));
            knownAdducts.add(new Adduct("2M+H-H2O", -17.0032, 1, 2));

            // --- ADUCTOS NEGATIVOS ---
            knownAdducts.add(new Adduct("M-H", -1.007276, -1, 1));
            knownAdducts.add(new Adduct("M+Cl", 34.969402, -1, 1));
            knownAdducts.add(new Adduct("M+HCOOH-H", 44.998201, -1, 1));
            knownAdducts.add(new Adduct("M-H-H2O", -19.01839, -1, 1));
            knownAdducts.add(new Adduct("2M-H", -1.007276, -1, 2));
            knownAdducts.add(new Adduct("M-2H", -1.007276, -2, 1));
            knownAdducts.add(new Adduct("M+F", 18.99895, -1, 1)); // CORREGIDO según tabla oficial

            // 3. UPLOAD OF THE CSV
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("peak-feature-30.csv"))) {
                String line;
                br.readLine(); // jump first title row
                while ((line = br.readLine()) != null) {
                    String[] d = line.split(",");
                    if (d.length >= 6) {
                        Peak p = new Peak(
                                d[0],
                                Double.parseDouble(d[3]),
                                Double.parseDouble(d[4]),
                                Double.parseDouble(d[5])
                        );
                        peaks.add(p);
                    }
                }
                LOG.info("CSV UPLOAD COMPLETE");
            } catch (Exception e) {
                LOG.error("ERROR UPLOADING CVS: " + e.getMessage());
            }

            //STAGE 1: Deconvolución
            LOG.info(" Stage 1: Deconvolution...");
            Stage1DeconvolutionService stage1 = new Stage1DeconvolutionService();
            List<Feature> generatedFeatures = stage1.groupPeaksByRT(peaks);

            System.out.println("--- STAGE 1: CREATION OF FEATURES ---");
            System.out.println("Number of processed peaks: " + peaks.size());
            System.out.println("Number of features generated: " + generatedFeatures.size());

            // STAGE 2: Masas y aductos
            LOG.info(" Stage 2: Mass calculation");
            Stage2NeutralMassService stage2 = new Stage2NeutralMassService();
            List<LabelledAdductFeature> labelledFeatures = stage2.calculateMasses(generatedFeatures, knownAdducts);

            System.out.println("--- STAGE 2: CREATION OF LABELLED FEATURES ---");
            System.out.println("Number of Labelled Features generated: " + labelledFeatures.size());


            for (LabelledAdductFeature lf : labelledFeatures) {
                System.out.printf("LabelledFeature [%s] | Mass: %.4f | RT: %.2f | Peaks: %d%n",
                        lf.getMainAdduct(),
                        lf.getMonoisotopicMass(),
                        lf.getFeature().getRt(),
                        lf.getLabelledPeaks().size());

                for (LabelledPeak lp : lf.getLabelledPeaks()) {
                    System.out.println("   -> " + lp.getOriginalPeak().getId() + " (mz: " + lp.getOriginalPeak().getMz() + ", aducto: " + lp.getAssignedAdduct().getLabel() + ")");
                }
            }

            // Validaciones básicas de JUnit
            assertNotNull(labelledFeatures);
            assertFalse("Not generated features, problem in peaks", labelledFeatures.isEmpty());

        } catch (Exception e) {
            LOG.error("ERROR IN TEST: " + e.getMessage());
        }
    }
}