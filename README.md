# Cotizaciones

API REST en Spring Boot con tres cotizadores: envíos de paquetería, renta de vehículos y hospedaje.

## Requisitos

- Java 17 o superior
- No hace falta instalar Maven, el proyecto incluye `mvnw`

## Cómo ejecutarlo

```bash
./mvnw spring-boot:run
```

En Windows:

```bash
mvnw.cmd spring-boot:run
```

La API queda en `http://localhost:8080/api`.

## Estructura

```
mx.edu.utez.cotizaciones
├── controller   EnvioController, RentaController, HospedajeController
├── service      EnvioService, RentaService, HospedajeService
├── dto          Request y Response de cada cotizador
├── model        TipoEnvio, TipoVehiculo, TipoHabitacion, Temporada
└── exception    CotizacionRechazadaException, ManejadorErrores
```

## Endpoints

| Método | URL | Descripción |
|---|---|---|
| POST | `http://localhost:8080/api/envios/cotizar` | Cotización de envío |
| POST | `http://localhost:8080/api/rentas/cotizar` | Cotización de renta de vehículo |
| POST | `http://localhost:8080/api/hospedajes/cotizar` | Cotización de hospedaje |

En Postman: método `POST`, pestaña **Body** → **raw** → **JSON**.

---

## 1. Envíos

`POST http://localhost:8080/api/envios/cotizar`

Body:

```json
{
  "codigoPostal": "62000",
  "pesoKg": 8.5,
  "largoCm": 30,
  "anchoCm": 20,
  "altoCm": 15,
  "tipoEnvio": "EXPRESS",
  "valorDeclarado": 12000
}
```

Respuesta `200 OK`:

```json
{
  "codigoPostal": "62000",
  "tipoEnvio": "EXPRESS",
  "volumenCm3": 9000.0,
  "costoBase": 80.0,
  "cargoPeso": 102.0,
  "cargoVolumen": 0.0,
  "cargoTipoEnvio": 72.8,
  "seguro": 240.0,
  "total": 494.8
}
```

Cálculo: 80 + 102 = 182, más 40 % por EXPRESS = 254.8, más seguro de 240 = **494.8**.

Valores permitidos de `tipoEnvio`: `ESTANDAR`, `EXPRESS`, `MISMO_DIA`.

Reglas de rechazo: peso mayor a 50 kg, alguna dimensión mayor a 150 cm o volumen mayor a 1,000,000 cm³.

---

## 2. Renta de vehículos

`POST http://localhost:8080/api/rentas/cotizar`

Body:

```json
{
  "nombreCliente": "Ana",
  "edadConductor": 22,
  "tipoVehiculo": "SUV",
  "diasRenta": 5,
  "kilometrosEstimados": 650,
  "seguroCompleto": true
}
```

Respuesta `200 OK`:

```json
{
  "nombreCliente": "Ana",
  "tipoVehiculo": "SUV",
  "costoRenta": 4750.0,
  "kilometrosIncluidos": 500,
  "kilometrosAdicionales": 150,
  "cargoKilometros": 600.0,
  "cargoEdad": 802.5,
  "seguro": 900.0,
  "descuento": 0.0,
  "total": 7052.5
}
```

Cálculo: 4750 de renta + 600 por kilómetros + 802.5 por edad + 900 de seguro = **7052.5**.

Valores permitidos de `tipoVehiculo`: `COMPACTO` (550/día), `SEDAN` (700/día), `SUV` (950/día), `CAMIONETA` (1200/día).

Reglas de rechazo: conductor menor de 18 años, más de 30 días, más de 5,000 km, o CAMIONETA con conductor menor de 25 años.

El cargo por edad se calcula antes del descuento de 10 %, porque el descuento no afecta a ese cargo.

---

## 3. Hospedaje

`POST http://localhost:8080/api/hospedajes/cotizar`

Body:

```json
{
  "nombreHuesped": "Luis",
  "tipoHabitacion": "DOBLE",
  "numeroNoches": 4,
  "numeroHuespedes": 2,
  "temporada": "ALTA",
  "incluyeDesayuno": true,
  "incluyeEstacionamiento": true
}
```

Respuesta `200 OK`:

```json
{
  "nombreHuesped": "Luis",
  "tipoHabitacion": "DOBLE",
  "costoHospedaje": 4400.0,
  "ajusteTemporada": 1100.0,
  "descuentoEstancia": 0.0,
  "costoDesayuno": 1200.0,
  "costoEstacionamiento": 400.0,
  "subtotal": 7100.0,
  "impuestoHospedaje": 284.0,
  "total": 7384.0
}
```

Cálculo: subtotal de 7100 más 4 % de impuesto (284) = **7384**.

Valores permitidos de `tipoHabitacion`: `INDIVIDUAL` (700/noche, 1 persona), `DOBLE` (1100/noche, 2 personas), `SUITE` (1800/noche, 4 personas).

Valores permitidos de `temporada`: `BAJA` (−10 %), `REGULAR`, `ALTA` (+25 %).

Reglas de rechazo: más de 30 noches o más huéspedes de los que admite la habitación.

---

## Ejemplos de errores

Todos los errores regresan `400 Bad Request` con un mensaje.

### Regla de negocio no cumplida

Envío con `"pesoKg": 60`:

```json
{
  "mensaje": "No se aceptan paquetes de más de 50 kg"
}
```

Renta de `"tipoVehiculo": "CAMIONETA"` con `"edadConductor": 22`:

```json
{
  "mensaje": "Para rentar una CAMIONETA el conductor debe tener al menos 25 años"
}
```

Hospedaje en `"tipoHabitacion": "DOBLE"` con `"numeroHuespedes": 3`:

```json
{
  "mensaje": "La habitación DOBLE admite máximo 2 persona(s)"
}
```

### Error de validación (`@Valid`)

Envío con `"codigoPostal": ""`:

```json
{
  "codigoPostal": "El código postal debe tener 5 dígitos"
}
```

### Valor no permitido o JSON mal escrito

Envío con `"tipoEnvio": "AVION"`:

```json
{
  "mensaje": "El cuerpo de la petición no es válido o contiene un valor no permitido"
}
```
