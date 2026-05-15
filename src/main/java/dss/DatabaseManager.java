package dss;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:src/main/resources/metabolite.db";
    public List<Compound> getCompoundsByMass(double mass, double tolerance) {
        List<Compound> compounds = new ArrayList<>();

        String sql = "SELECT name, formula, monoisotopicMass, logp, inchi FROM Compound WHERE monoisotopicMass BETWEEN ? AND ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, mass - tolerance);
            pstmt.setDouble(2, mass + tolerance);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                compounds.add(new Compound(
                        rs.getString("name"),
                        rs.getString("formula"),
                        rs.getDouble("monoisotopicMass"),
                        rs.getDouble("logp"),
                        rs.getString("inchi")
                ));
            }
        } catch (SQLException e) {
            System.err.println("DB Error: " + e.getMessage());
        }
        return compounds;
    }


    public List<Compound> searchByMass(double targetMass, double tolerance) {
        return getCompoundsByMass(targetMass, tolerance);
    }
}