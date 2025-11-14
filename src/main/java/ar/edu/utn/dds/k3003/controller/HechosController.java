package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.config.MetricsConfig;
import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.dto.HechoDTO;
import io.micrometer.core.instrument.Timer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/hechos")
public class HechosController {

    private final Fachada fachadaAgregador;
    private final MetricsConfig metricsConfig;

    public HechosController(Fachada fachadaAgregador, MetricsConfig metricsConfig) {
        this.fachadaAgregador = fachadaAgregador;
        this.metricsConfig = metricsConfig;
    }

    @GetMapping()
    public ResponseEntity<List<HechoDTO>> listarTodosLosHechos() {
        Timer.Sample timer = metricsConfig.startTimer();
        try {
            metricsConfig.incrementCounter("hechos.consultados", "componente", "agregador", "tipo", "hechos");
            List<HechoDTO> hechos = fachadaAgregador.obtenerTodosLosHechos();
            return hechos.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(hechos);
        }finally {
            metricsConfig.stopTimer(timer, "hechos.timer", "componente", "agregador","tipo", "hechos","metodo", "GET /hechos");
        }
    }

   

}
