package mx.edu.utez.cotizaciones.model;

public enum TipoHabitacion {
    INDIVIDUAL(700, 1), DOBLE(1100, 2), SUITE(1800, 4);

    private final double costoNoche;
    private final int capacidad;

    TipoHabitacion(double costoNoche, int capacidad) {
        this.costoNoche = costoNoche;
        this.capacidad = capacidad;
    }

    public double getCostoNoche() {
        return costoNoche;
    }

    public int getCapacidad() {
        return capacidad;
    }
}
