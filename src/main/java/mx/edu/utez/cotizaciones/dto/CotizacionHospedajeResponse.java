package mx.edu.utez.cotizaciones.dto;

public record CotizacionHospedajeResponse(
        String nombreHuesped,
        String tipoHabitacion,
        double costoHospedaje,
        double ajusteTemporada,
        double descuentoEstancia,
        double costoDesayuno,
        double costoEstacionamiento,
        double subtotal,
        double impuestoHospedaje,
        double total
) {}
