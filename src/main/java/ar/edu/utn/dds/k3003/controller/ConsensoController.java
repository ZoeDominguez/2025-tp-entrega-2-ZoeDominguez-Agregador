package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.config.MetricsConfig;
import ar.edu.utn.dds.k3003.facades.dtos.ConsensosEnum;
import io.micrometer.core.instrument.Timer;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consenso")
public class ConsensoController {

    private final Fachada fachadaAgregador;
    private final MetricsConfig metricsConfig;

    public ConsensoController(Fachada fachadaAgregador, MetricsConfig metricsConfig) {
        this.fachadaAgregador = fachadaAgregador;
        this.metricsConfig = metricsConfig;
    }

    @PatchMapping
    public ResponseEntity<Void> configurarConsenso(@RequestBody Map<String, String> body) {
        Timer.Sample timer = metricsConfig.startTimer();
        try {
            ConsensosEnum consenso = ConsensosEnum.valueOf(body.get("tipo").toUpperCase());
            String coleccion = body.get("coleccion");

            fachadaAgregador.setConsensoStrategy(consenso, coleccion);
            metricsConfig.incrementCounter("consenso.configurado",
            "tipo", "agregador", "consenso", consenso.name(),
            "coleccion", coleccion);
            return ResponseEntity.noContent().build();
        } finally {
            metricsConfig.stopTimer(timer, "consenso.timer", "agregador", "consenso", "PATCH /consenso");
        }
        
    }

}