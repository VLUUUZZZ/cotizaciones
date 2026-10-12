package mx.edu.utez.cotizaciones.service;

import mx.edu.utez.cotizaciones.dto.CotizacionRentaRequest;
import mx.edu.utez.cotizaciones.dto.CotizacionRentaResponse;
import mx.edu.utez.cotizaciones.exception.CotizacionRechazadaException;
import mx.edu.utez.cotizaciones.model.TipoVehiculo;
import org.springframework.stereotype.Service;

@Service
public class RentaService {

    public CotizacionRentaResponse cotizar(CotizacionRentaRequest datos) {
        int edad = datos.edadConductor();
        int dias = datos.diasRenta();
        int km = datos.kilometrosEstimados();

        if (edad < 18) {
            throw new CotizacionRechazadaException("El conductor debe ser mayor de edad");
        }
        if (dias > 30) {
            throw new CotizacionRechazadaException("La renta no puede superar 30 días");
        }
        if (km > 5000) {
            throw new CotizacionRechazadaException("Los kilómetros estimados no pueden superar 5,000");
        }
        if (datos.tipoVehiculo() == TipoVehiculo.CAMIONETA && edad < 25) {
            throw new CotizacionRechazadaException("Para rentar una CAMIONETA el conductor debe tener al menos 25 años");
        }

        double costoRenta = datos.tipoVehiculo().getCostoDiario() * dias;

        int kmIncluidos = dias * 100;
        int kmAdicionales = Math.max(0, km - kmIncluidos);
        double cargoKilometros = kmAdicionales * 4;

        double cargoEdad = (edad >= 18 && edad <= 24)
                ? (costoRenta + cargoKilometros) * 0.15
                : 0;

        double seguro = datos.seguroCompleto() ? 180 * dias : 0;

        double descuento = dias >= 7 ? costoRenta * 0.10 : 0;

        double total = costoRenta - descuento + cargoKilometros + cargoEdad + seguro;

        return new CotizacionRentaResponse(
                datos.nombreCliente(),
                datos.tipoVehiculo().name(),
                redondear(costoRenta),
                kmIncluidos,
                kmAdicionales,
                redondear(cargoKilometros),
                redondear(cargoEdad),
                redondear(seguro),
                redondear(descuento),
                redondear(total)
        );
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
