package mx.edu.utez.cotizaciones.dto;

import jakarta.validation.constraints.*;
import mx.edu.utez.cotizaciones.model.TipoEnvio;

public record CotizacionEnvioRequest(
        @NotBlank(message = "El código postal es obligatorio")
        @Pattern(regexp = "\\d{5}", message = "El código postal debe tener 5 dígitos")
        String codigoPostal,

        @NotNull(message = "El peso es obligatorio")
        @Positive(message = "El peso debe ser mayor que cero")
        Double pesoKg,

        @NotNull(message = "El largo es obligatorio")
        @Positive(message = "El largo debe ser mayor que cero")
        Double largoCm,

        @NotNull(message = "El ancho es obligatorio")
        @Positive(message = "El ancho debe ser mayor que cero")
        Double anchoCm,

        @NotNull(message = "El alto es obligatorio")
        @Positive(message = "El alto debe ser mayor que cero")
        Double altoCm,

        @NotNull(message = "El tipo de envío es obligatorio (ESTANDAR, EXPRESS, MISMO_DIA)")
        TipoEnvio tipoEnvio,

        @NotNull(message = "El valor declarado es obligatorio")
        @PositiveOrZero(message = "El valor declarado no puede ser negativo")
        Double valorDeclarado
) {}
