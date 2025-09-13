package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.config.MetricsConfig;
import ar.edu.utn.dds.k3003.facades.FachadaAgregador;
import ar.edu.utn.dds.k3003.facades.dtos.ConsensosEnum;
import io.micrometer.core.instrument.Timer;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consenso")
public class ConsensoController {

    private final MetricsConfig metricsConfig;

    private final FachadaAgregador fachadaAgregador;

    public ConsensoController(MetricsConfig metricsConfig, FachadaAgregador fachadaAgregador) {
        this.metricsConfig = metricsConfig;
        this.fachadaAgregador = fachadaAgregador;
    }

    @PatchMapping
    public ResponseEntity<String> configurarConsenso(@RequestBody Map<String, String> body) {
        Timer.Sample timer = metricsConfig.startTimer();
        try {
            ConsensosEnum consenso = ConsensosEnum.valueOf(body.get("tipo").toUpperCase());
            String coleccion = body.get("coleccion");

            metricsConfig.incrementCounter("consenso.configurado", "agregador","tipo","consenso", consenso.name(),"coleccion", coleccion);
            
            fachadaAgregador.setConsensoStrategy(consenso, coleccion);
            return ResponseEntity.ok("Configuración aplicada correctamente de consenso " + consenso);
        } finally {
            metricsConfig.stopTimer(timer, "consenso.timer", "agregador", "tipo", "consenso", "PATCH /consenso");
        }
        
    }

}