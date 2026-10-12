package mx.edu.utez.cotizaciones.controller;

import jakarta.validation.Valid;
import mx.edu.utez.cotizaciones.dto.CotizacionHospedajeRequest;
import mx.edu.utez.cotizaciones.dto.CotizacionHospedajeResponse;
import mx.edu.utez.cotizaciones.service.HospedajeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hospedajes")
public class HospedajeController {

    private final HospedajeService servicio;

    public HospedajeController(HospedajeService servicio) {
        this.servicio = servicio;
    }

    @PostMapping("/cotizar")
    public ResponseEntity<CotizacionHospedajeResponse> cotizar(
            @Valid @RequestBody CotizacionHospedajeRequest datos) {
        return ResponseEntity.ok(servicio.cotizar(datos));
    }
}
