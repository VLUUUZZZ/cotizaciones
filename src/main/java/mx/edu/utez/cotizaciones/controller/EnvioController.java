package mx.edu.utez.cotizaciones.controller;

import jakarta.validation.Valid;
import mx.edu.utez.cotizaciones.dto.CotizacionEnvioRequest;
import mx.edu.utez.cotizaciones.dto.CotizacionEnvioResponse;
import mx.edu.utez.cotizaciones.service.EnvioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/envios")
public class EnvioController {

    private final EnvioService servicio;

    public EnvioController(EnvioService servicio) {
        this.servicio = servicio;
    }

    @PostMapping("/cotizar")
    public ResponseEntity<CotizacionEnvioResponse> cotizar(
            @Valid @RequestBody CotizacionEnvioRequest datos) {
        return ResponseEntity.ok(servicio.cotizar(datos));
    }
}
