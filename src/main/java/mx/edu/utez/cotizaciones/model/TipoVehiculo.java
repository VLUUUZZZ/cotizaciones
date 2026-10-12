package mx.edu.utez.cotizaciones.model;

public enum TipoVehiculo {
    COMPACTO(550), SEDAN(700), SUV(950), CAMIONETA(1200);

    private final double costoDiario;

    TipoVehiculo(double costoDiario) {
        this.costoDiario = costoDiario;
    }

    public double getCostoDiario() {
        return costoDiario;
    }
}
