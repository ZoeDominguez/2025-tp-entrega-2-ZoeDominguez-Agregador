package ar.edu.utn.dds.k3003.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.dds.k3003.repository.FuenteRepository;

@RestController
@RequestMapping("/dev")
public class DevController {
    private final FuenteRepository fuenteRepository;

    public DevController(FuenteRepository fuenteRepository) {
        this.fuenteRepository = fuenteRepository;
    }

    @DeleteMapping("/reset")
    public ResponseEntity<Void> reset() {
        fuenteRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
