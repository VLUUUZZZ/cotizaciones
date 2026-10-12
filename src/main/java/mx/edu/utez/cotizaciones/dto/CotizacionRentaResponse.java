package mx.edu.utez.cotizaciones.dto;

public record CotizacionRentaResponse(
        String nombreCliente,
        String tipoVehiculo,
        double costoRenta,
        int kilometrosIncluidos,
        int kilometrosAdicionales,
        double cargoKilometros,
        double cargoEdad,
        double seguro,
        double descuento,
        double total
) {}
