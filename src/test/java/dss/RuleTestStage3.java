package dss;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class RuleTestStage3 {

    @Test
    public void testCheckDatabase() throws Exception {
        // Usa la misma URL que tienes en el DatabaseManager
        String url = "jdbc:sqlite:src/main/resources/metabolite.db";

        System.out.println("Buscando en la ruta: " + url);
        try (java.sql.Connection conn = java.sql.DriverManager.getConnection(url)) {
            java.sql.ResultSet rs = conn.getMetaData().getTables(null, null, null, new String[]{"TABLE"});

            boolean hasTables = false;
            System.out.println("TABLAS ENCONTRADAS:");
            while (rs.next()) {
                System.out.println(" -> " + rs.getString("TABLE_NAME"));
                hasTables = true;
            }

            if (!hasTables) {
                System.out.println("¡LA BASE DE DATOS ESTÁ VACÍA! Java no encuentra tu archivo y está creando uno nuevo. La ruta está mal.");
            }
        }
    }

    @Test
    public void testDatabaseAnnotation() {
        // Dopamina (153.0789)
        Feature f1 = new Feature(180.06339, "M+H");
        LabelledAdductFeature laf1 = new LabelledAdductFeature(f1, 180.06339, "M+H");

        List<LabelledAdductFeature> features = new ArrayList<>();
        features.add(laf1);

        try {
            // STAGE 3
            System.out.println("\nATABASE ANNOTATION (STAGE 3)");

            DatabaseManager dbManager = new DatabaseManager();
            Stage3AnnotationService stage3 = new Stage3AnnotationService(dbManager);

            // 0.05 Daltons
            double tolerance = 0.05;

            List<Annotation> allAnnotations = stage3.generateAnnotations(features, tolerance);

            System.out.println("Feature Mass: " + String.format("%.4f", laf1.getNeutralMass()) +
                    " -> Found " + allAnnotations.size() + " compounds.");

            // VERIFICATION & PRINTS
            System.out.println("\nFINAL ANNOTATION RESULTS");
            for (Annotation ann : allAnnotations) {
                System.out.printf("ANNOTATION: [Feature Mass %.4f] is associated with [Compound: %s (%s)]%n",
                        ann.getLabelledFeature().getNeutralMass(),
                        ann.getCompound().getName(),
                        ann.getCompound().getFormula());
            }

            // Validations
            assertNotNull("Annotation list should not be null", allAnnotations);
            assertFalse("Should have found at least one annotation in DB", allAnnotations.isEmpty());

        } catch (Exception e) {
            System.err.println("Error during Stage 3 testing: " + e.getMessage());
        }
    }
}