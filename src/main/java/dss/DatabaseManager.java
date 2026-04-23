package dss;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:src/main/resources/metabolites.db";

    public List<Compound> searchByMass(double targetMass, double tolerance) {
        List<Compound> compounds = new ArrayList<>();

        // rango de busqueda
        double minMass = targetMass - tolerance;
        double maxMass = targetMass + tolerance;

        // parametrized SQL
        String sql = "SELECT name, formula, monoisotopicMass, logp, inchi FROM Compound " +
                "WHERE monoisotopicMass BETWEEN ? AND ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // en el primer ? que veo, meto minMass. En el segundo, maxMass
            pstmt.setDouble(1, minMass);
            pstmt.setDouble(2, maxMass);

            ResultSet rs = pstmt.executeQuery();

            // create objects Compound with that mass range
            while (rs.next()) {
                Compound c = new Compound(
                        rs.getString("name"),
                        rs.getString("formula"),
                        rs.getDouble("monoisotopicMass"),
                        rs.getDouble("logp"),
                        rs.getString("inchi")
                );
                compounds.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar la base de datos: " + e.getMessage());
        }
        return compounds;
    }
}