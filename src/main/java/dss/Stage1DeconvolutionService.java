package dss;

import lipid.Peak;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Stage1DeconvolutionService {

    public List<Feature> groupPeaksByRT(List<Peak> peaks) {
        List<Feature> features = new ArrayList<>();

        peaks.sort(Comparator.comparingDouble(Peak::getRt));

        for (Peak p : peaks) {
            boolean added = false;
            for (Feature f : features) {

                if (Math.abs(f.getRt() - p.getRt()) <= 0.3) {
                    f.addPeak(p);
                    added = true;
                    break;
                }
            }
            // Si no cuadra con ninguna, creamos una nueva Feature
            if (!added) {
                // unknown
                Feature newFeature = new Feature(0.0, "Unknown");
                newFeature.addPeak(p);
                features.add(newFeature);
            }
        }
        return features;
    }
}