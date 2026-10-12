package mx.edu.utez.cotizaciones.dto;

import jakarta.validation.constraints.*;
import mx.edu.utez.cotizaciones.model.Temporada;
import mx.edu.utez.cotizaciones.model.TipoHabitacion;

public record CotizacionHospedajeRequest(
        @NotBlank(message = "El nombre del huésped es obligatorio")
        String nombreHuesped,

        @NotNull(message = "El tipo de habitación es obligatorio (INDIVIDUAL, DOBLE, SUITE)")
        TipoHabitacion tipoHabitacion,

        @NotNull(message = "El número de noches es obligatorio")
        @Positive(message = "El número de noches debe ser mayor que cero")
        Integer numeroNoches,

        @NotNull(message = "El número de huéspedes es obligatorio")
        @Positive(message = "El número de huéspedes debe ser mayor que cero")
        Integer numeroHuespedes,

        @NotNull(message = "La temporada es obligatoria (BAJA, REGULAR, ALTA)")
        Temporada temporada,

        @NotNull(message = "Indica si incluye desayuno")
        Boolean incluyeDesayuno,

        @NotNull(message = "Indica si incluye estacionamiento")
        Boolean incluyeEstacionamiento
) {}
