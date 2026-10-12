package mx.edu.utez.cotizaciones.service;

import mx.edu.utez.cotizaciones.dto.CotizacionHospedajeRequest;
import mx.edu.utez.cotizaciones.dto.CotizacionHospedajeResponse;
import mx.edu.utez.cotizaciones.exception.CotizacionRechazadaException;
import mx.edu.utez.cotizaciones.model.TipoHabitacion;
import org.springframework.stereotype.Service;

@Service
public class HospedajeService {

    public CotizacionHospedajeResponse cotizar(CotizacionHospedajeRequest datos) {
        TipoHabitacion habitacion = datos.tipoHabitacion();
        int noches = datos.numeroNoches();
        int huespedes = datos.numeroHuespedes();

        if (noches > 30) {
            throw new CotizacionRechazadaException("La reservación no puede superar 30 noches");
        }
        if (huespedes > habitacion.getCapacidad()) {
            throw new CotizacionRechazadaException(
                    "La habitación " + habitacion.name() + " admite máximo "
                            + habitacion.getCapacidad() + " persona(s)");
        }

        double costoHospedaje = habitacion.getCostoNoche() * noches;

        double ajusteTemporada = switch (datos.temporada()) {
            case BAJA -> -costoHospedaje * 0.10;
            case REGULAR -> 0;
            case ALTA -> costoHospedaje * 0.25;
        };

        double costoDesayuno = datos.incluyeDesayuno() ? huespedes * noches * 150 : 0;

        double costoEstacionamiento = datos.incluyeEstacionamiento() ? noches * 100 : 0;

        double descuentoEstancia = noches >= 7 ? costoHospedaje * 0.08 : 0;

        double subtotal = costoHospedaje + ajusteTemporada - descuentoEstancia
                + costoDesayuno + costoEstacionamiento;
        double impuesto = subtotal * 0.04;
        double total = subtotal + impuesto;

        return new CotizacionHospedajeResponse(
                datos.nombreHuesped(),
                habitacion.name(),
                redondear(costoHospedaje),
                redondear(ajusteTemporada),
                redondear(descuentoEstancia),
                redondear(costoDesayuno),
                redondear(costoEstacionamiento),
                redondear(subtotal),
                redondear(impuesto),
                redondear(total)
        );
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
