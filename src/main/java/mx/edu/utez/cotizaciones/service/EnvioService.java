package mx.edu.utez.cotizaciones.service;

import mx.edu.utez.cotizaciones.dto.CotizacionEnvioRequest;
import mx.edu.utez.cotizaciones.dto.CotizacionEnvioResponse;
import mx.edu.utez.cotizaciones.exception.CotizacionRechazadaException;
import org.springframework.stereotype.Service;

@Service
public class EnvioService {

    public CotizacionEnvioResponse cotizar(CotizacionEnvioRequest datos) {
        double volumen = datos.largoCm() * datos.anchoCm() * datos.altoCm();

        if (datos.pesoKg() > 50) {
            throw new CotizacionRechazadaException("No se aceptan paquetes de más de 50 kg");
        }
        if (datos.largoCm() > 150 || datos.anchoCm() > 150 || datos.altoCm() > 150) {
            throw new CotizacionRechazadaException("Ninguna dimensión puede superar los 150 cm");
        }
        if (volumen > 1_000_000) {
            throw new CotizacionRechazadaException("El volumen no puede superar 1,000,000 cm³");
        }

        double costoBase = 80;
        double cargoPeso = datos.pesoKg() * 12;
        double cargoVolumen = volumen > 50_000 ? 100 : 0;
        double acumulado = costoBase + cargoPeso + cargoVolumen;

        double cargoTipoEnvio = switch (datos.tipoEnvio()) {
            case EXPRESS -> acumulado * 0.40;
            case MISMO_DIA -> acumulado * 0.70;
            case ESTANDAR -> 0;
        };

        double seguro = datos.valorDeclarado() > 10_000 ? datos.valorDeclarado() * 0.02 : 0;

        double total = acumulado + cargoTipoEnvio + seguro;

        return new CotizacionEnvioResponse(
                datos.codigoPostal(),
                datos.tipoEnvio().name(),
                redondear(volumen),
                redondear(costoBase),
                redondear(cargoPeso),
                redondear(cargoVolumen),
                redondear(cargoTipoEnvio),
                redondear(seguro),
                redondear(total)
        );
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
