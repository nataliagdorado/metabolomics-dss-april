package dss;

import adduct.Adduct;
import lipid.Peak;
import java.util.ArrayList;
import java.util.List;

public class Stage2NeutralMassService {

    public List<LabelledAdductFeature> calculateMasses(List<Feature> features, List<Adduct> knownAdducts) {
        List<LabelledAdductFeature> results = new ArrayList<>();

        for (Feature f : features) {
            // only one peak, no
            if (f.getGroupedPeaks().size() < 2) continue;

            // the first two
            Peak p1 = f.getGroupedPeaks().get(0);
            Peak p2 = f.getGroupedPeaks().get(1);
            boolean matched = false;

            // try all combinations
            for (Adduct a1 : knownAdducts) {
                for (Adduct a2 : knownAdducts) {
                    if (a1 == a2) continue;

                    double m1 = Adduct.getMonoisotopicMassFromMZ(p1.getMz(), a1);
                    double m2 = Adduct.getMonoisotopicMassFromMZ(p2.getMz(), a2);

                    // tolerancia 0.01
                    if (Math.abs(m1 - m2) <= 0.01) {
                        double mMedia = (m1 + m2) / 2.0;
                        String mainName = (p1.getIntensity() >= p2.getIntensity()) ? a1.getLabel() : a2.getLabel();

                        LabelledAdductFeature laf = new LabelledAdductFeature(f, mMedia, mainName);
                        laf.addLabelledPeak(new LabelledPeak(p1, a1));
                        laf.addLabelledPeak(new LabelledPeak(p2, a2));
                        results.add(laf);
                        matched = true;
                        break;
                    }
                }
                if(matched) break;
            }
        }
        return results;
    }
}