package adduct;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

// mejor map. si fuese List, habria que leer uno por uno. en hash map las lalves son unicas,
//asi no tengo unmismo aducto como M+H, que seria un problema cuando calculo la masa monoisotípica
// con map bsuco para ir directamente. Key: nombre del aducto
// value: objeto adduct
public class AdductList {

    public static final Map<String, Double> MAPMZPOSITIVEADDUCTS;
    public static final Map<String, Double> MAPMZNEGATIVEADDUCTS;

    //iniciador estático, inicio una sola vez
    static {
        // creo el objeto en la memoria. LinkedHashMap para que los aductos queden guardados en el mismo orden
        Map<String, Double> mapMZPositiveAdductsTMP = new LinkedHashMap<>();

        // inserto los datos. Lo de los corchetes es la key que va a buscar. el numero es el valor. la d al final es que sea de tipo double
        mapMZPositiveAdductsTMP.put("[M+H]+", -1.007276d);
        mapMZPositiveAdductsTMP.put("[M+2H]2+", -1.007276d);
        mapMZPositiveAdductsTMP.put("[M+Na]+", -22.989218d);
        mapMZPositiveAdductsTMP.put("[M+K]+", -38.963158d);
        mapMZPositiveAdductsTMP.put("[M+NH4]+", -18.033823d);
        mapMZPositiveAdductsTMP.put("[M+H-H2O]+", 17.0032d);
        mapMZPositiveAdductsTMP.put("[M+H+NH4]2+", -9.52055d);
        mapMZPositiveAdductsTMP.put("[2M+H]+", -1.007276d);
        mapMZPositiveAdductsTMP.put("[2M+Na]+", -22.989218d);
        // no puedo poner nada más en esta lista, hago el mapa inmodificable:
        MAPMZPOSITIVEADDUCTS = Collections.unmodifiableMap(mapMZPositiveAdductsTMP);

        Map<String, Double> mapMZNegativeAdductsTMP = new LinkedHashMap<>();
        mapMZNegativeAdductsTMP.put("[M-H]−", 1.007276d);
        mapMZNegativeAdductsTMP.put("[M+Cl]−", -34.969402d);
        mapMZNegativeAdductsTMP.put("[M+HCOOH-H]−", -44.998201d);
        mapMZNegativeAdductsTMP.put("[M-H-H2O]−", 19.01839d);
        mapMZNegativeAdductsTMP.put("[2M-H]−", 1.007276d);
        mapMZNegativeAdductsTMP.put("[M-2H]2−", 1.007276d);
        MAPMZNEGATIVEADDUCTS = Collections.unmodifiableMap(mapMZNegativeAdductsTMP);
    }

}
