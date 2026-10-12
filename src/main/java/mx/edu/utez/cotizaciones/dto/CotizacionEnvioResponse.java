package mx.edu.utez.cotizaciones.dto;

public record CotizacionEnvioResponse(
        String codigoPostal,
        String tipoEnvio,
        double volumenCm3,
        double costoBase,
        double cargoPeso,
        double cargoVolumen,
        double cargoTipoEnvio,
        double seguro,
        double total
) {}
