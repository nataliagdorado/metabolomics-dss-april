package adduct;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AdductTest {

    @Test
    public void testGetMonoisotopicMass() {
        // PROTÓN [M+H]+
        // Si el espectrómetro lee 301.007276 y es un protón (+1.007276)
        // La masa neutra (M) debe ser 300.0
        Adduct protonado = new Adduct("M+H", 1.007276, 1, 1);
        Double resultado1 = Adduct.getMonoisotopicMassFromMZ(301.007276, protonado);

        // Comprobamos: (301.007276 * 1 - 1.007276) / 1 = 300.0
        assertEquals(300.0, resultado1, 0.0001);

        // CASO 2: SODIO [M+Na]+
        // Si lee 322.989218 y es Sodio (+22.989218)
        // M debe ser 300.0
        Adduct sodio = new Adduct("M+Na", 22.989218, 1, 1);
        Double resultado2 = Adduct.getMonoisotopicMassFromMZ(322.989218, sodio);
        assertEquals(300.0, resultado2, 0.0001);

        // CASO 3: DOBLE CARGA [M+2H]2+
        // Si lee 150.503638 y es carga doble (z=2)
        // M debe ser 300.0
        Adduct dobleCarga = new Adduct("M+2H", 1.007276, 2, 1);
        Double resultado3 = Adduct.getMonoisotopicMassFromMZ(150.503638, dobleCarga);

        // Comprobamos: (150.503638 * 2 - 1.007276) / 1 = 300.0
        assertEquals(300.0, resultado3, 0.0001);
    }
}