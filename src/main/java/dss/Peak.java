package dss;

public class Peak {

    private String id;
    private double mz;        // Relación Masa/Carga
    private double intensity; // Abundancia de la señal
    private double rt;        // Tiempo de Retención en minutos

    // Constructor completo para facilitar la creación desde el Parser del CSV
    public Peak(String id, double mz, double intensity, double rt) {
        this.id = id;
        this.mz = mz;
        this.intensity = intensity;
        this.rt = rt;
    }

    // Los GETTERS son obligatorios para que Drools pueda acceder a los atributos
    public String getId() {
        return id;
    }

    public double getMz() {
        return mz;
    }

    public double getIntensity() {
        return intensity;
    }

    public double getRt() {
        return rt;
    }

    @Override
    public String toString() {
        return "Peak{" +
                "id='" + id + '\'' +
                ", mz=" + mz +
                ", intensity=" + intensity +
                ", rt=" + rt +
                '}';
    }
}