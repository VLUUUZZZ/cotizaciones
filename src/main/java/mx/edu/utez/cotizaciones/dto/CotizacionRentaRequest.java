package mx.edu.utez.cotizaciones.dto;

import jakarta.validation.constraints.*;
import mx.edu.utez.cotizaciones.model.TipoVehiculo;

public record CotizacionRentaRequest(
        @NotBlank(message = "El nombre del cliente es obligatorio")
        String nombreCliente,

        @NotNull(message = "La edad del conductor es obligatoria")
        @Positive(message = "La edad debe ser mayor que cero")
        Integer edadConductor,

        @NotNull(message = "El tipo de vehículo es obligatorio (COMPACTO, SEDAN, SUV, CAMIONETA)")
        TipoVehiculo tipoVehiculo,

        @NotNull(message = "Los días de renta son obligatorios")
        @Positive(message = "Los días de renta deben ser mayores que cero")
        Integer diasRenta,

        @NotNull(message = "Los kilómetros estimados son obligatorios")
        @PositiveOrZero(message = "Los kilómetros no pueden ser negativos")
        Integer kilometrosEstimados,

        @NotNull(message = "Indica si se contrata el seguro completo")
        Boolean seguroCompleto
) {}
