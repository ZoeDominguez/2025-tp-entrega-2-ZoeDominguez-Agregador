package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.config.MetricsConfig;
import ar.edu.utn.dds.k3003.facades.dtos.HechoDTO;
import io.micrometer.core.instrument.Timer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coleccion")
public class ColeccionController {

    private final Fachada fachadaAgregador;
    private final MetricsConfig metricsConfig;

    public ColeccionController(Fachada fachadaAgregador, MetricsConfig metricsConfig) {
        this.fachadaAgregador = fachadaAgregador;
        this.metricsConfig = metricsConfig;
    }

    @GetMapping("/{nombre}/hechos")
    public ResponseEntity<List<HechoDTO>> listarHechosPorColeccion(@PathVariable String nombre) {
        Timer.Sample timer = metricsConfig.startTimer();
        try {
            metricsConfig.incrementCounter("coleccion.hechos.consultados", "agregador", "coleccion", nombre);
            return ResponseEntity.ok(fachadaAgregador.hechos(nombre));
        } finally {
            metricsConfig.stopTimer(timer, "coleccion.timer", "agregador", "coleccion", "GET /coleccion/{nombre}/hechos");
        }
        
    }

}