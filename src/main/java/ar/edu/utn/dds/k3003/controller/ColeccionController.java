package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.dtos.HechoDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coleccion")
public class ColeccionController {

    private final Fachada fachadaAgregador;

    public ColeccionController(Fachada fachadaAgregador) {
        this.fachadaAgregador = fachadaAgregador;
    }

    @GetMapping("/{nombre}/hechos")
    public ResponseEntity<List<HechoDTO>> listarHechosPorColeccion(@PathVariable String nombre) {
        return ResponseEntity.ok(fachadaAgregador.hechos(nombre));
    }

}