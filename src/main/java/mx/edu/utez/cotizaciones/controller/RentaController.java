package mx.edu.utez.cotizaciones.controller;

import jakarta.validation.Valid;
import mx.edu.utez.cotizaciones.dto.CotizacionRentaRequest;
import mx.edu.utez.cotizaciones.dto.CotizacionRentaResponse;
import mx.edu.utez.cotizaciones.service.RentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rentas")
public class RentaController {

    private final RentaService servicio;

    public RentaController(RentaService servicio) {
        this.servicio = servicio;
    }

    @PostMapping("/cotizar")
    public ResponseEntity<CotizacionRentaResponse> cotizar(
            @Valid @RequestBody CotizacionRentaRequest datos) {
        return ResponseEntity.ok(servicio.cotizar(datos));
    }
}
