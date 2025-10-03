package ar.edu.utn.dds.k3003.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.dds.k3003.config.MetricsConfig;
import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.dto.FuenteDTO;
import io.micrometer.core.instrument.Timer;

@RestController
@RequestMapping("/fuentes")
public class FuenteController {

    private final Fachada fachadaAgregador;

    private final MetricsConfig metricsConfig;

    public FuenteController(Fachada fachadaAgregador, MetricsConfig metricsConfig) {
        this.fachadaAgregador = fachadaAgregador;
        this.metricsConfig = metricsConfig;
    }

    @GetMapping
    public ResponseEntity<List<FuenteDTO>> fuentes() {
        Timer.Sample timer = metricsConfig.startTimer();
        try {
            metricsConfig.incrementCounter("fuentes.consultas", "agregador", "fuentes", "listar", "GET /fuentes");
            return ResponseEntity.ok(fachadaAgregador.fuentes());
        } finally {
            metricsConfig.stopTimer(timer, "get_fuentes.timer",
                    "agregador", "fuentes", "listar", "GET /fuentes");
        }
    }

    @PostMapping
    public ResponseEntity<FuenteDTO> agregarFuente(@RequestBody FuenteDTO fuenteDTO) {
        Timer.Sample timer = metricsConfig.startTimer();
        try {
            metricsConfig.incrementCounter("fuentes.agregadas", "agregador", "contador", "fuentes", "POST /fuentes");

            return ResponseEntity.ok(fachadaAgregador.agregar(fuenteDTO));
        }finally {
            metricsConfig.stopTimer(timer, "fuentes.agregadas.timer",
                    "agregador", "contador", "fuentes", "POST /fuentes");
        }
    }
    
}
