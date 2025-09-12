package ar.edu.utn.dds.k3003.app;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.dds.k3003.clients.FuenteProxy;
import ar.edu.utn.dds.k3003.facades.dtos.ConsensosEnum;
import ar.edu.utn.dds.k3003.facades.dtos.FuenteDTO;
import ar.edu.utn.dds.k3003.facades.dtos.HechoDTO;
import ar.edu.utn.dds.k3003.facades.FachadaFuente;
import ar.edu.utn.dds.k3003.model.Agregador;
import ar.edu.utn.dds.k3003.model.Fuente;
import ar.edu.utn.dds.k3003.model.Hecho;
import ar.edu.utn.dds.k3003.repository.FuenteRepository;
import ar.edu.utn.dds.k3003.repository.InMemoryFuenteRepo;
import ar.edu.utn.dds.k3003.repository.JpaFuenteRepository;
import jakarta.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Fachada {

  private Agregador agregador = new Agregador();

  private final FuenteRepository fuenteRepository;

  private final ObjectMapper objectMapper;

  private boolean DB_outdated_Fuentes= true;

  protected Fachada() {
    this.fuenteRepository = new InMemoryFuenteRepo();
    this.objectMapper = new ObjectMapper();
  }

  @Autowired
  public Fachada(JpaFuenteRepository fuenteRepository, ObjectMapper objectMapper) {
    this.fuenteRepository = fuenteRepository;
    this.objectMapper = objectMapper;
  }

  @Transactional
  public FuenteDTO agregar(FuenteDTO fuenteDto) {
    String id = UUID.randomUUID().toString();
    Fuente fuente = new Fuente(id, fuenteDto.nombre(), fuenteDto.endpoint());
    fuenteRepository.save(fuente);
    this.DB_outdated_Fuentes= true;
    return convertirAFuenteDTO(fuente);
  }


  public List<FuenteDTO> fuentes() {
    return fuenteRepository.findAll().stream().map(this::convertirAFuenteDTO).collect(Collectors.toList());
  }


  public FuenteDTO buscarFuenteXId(String fuenteId) throws NoSuchElementException {
    return fuenteRepository.findById(fuenteId)
        .map(this::convertirAFuenteDTO)
        .orElseThrow(() -> new NoSuchElementException("Fuente no encontrada: " + fuenteId));
  }

 
  public List<HechoDTO> hechos(String nombreColeccion) throws NoSuchElementException {

    syncFuentesIfNeeded();

    List<Hecho> hechosModelo = agregador.obtenerHechosPorColeccion(nombreColeccion);

    if (hechosModelo == null || hechosModelo.isEmpty()) {
      throw new NoSuchElementException("Busqueda no encontrada de: " + nombreColeccion);
    }
    return hechosModelo.stream()
      .map(this::convertirADTO)
      .collect(Collectors.toList());
  }


  public void addFachadaFuentes(String fuenteId, FachadaFuente fuente) {
    agregador.agregarFachadaAFuente(fuenteId, fuente);
  }


  public void setConsensoStrategy(ConsensosEnum tipoConsenso, String nombreColeccion)
      throws InvalidParameterException {
    agregador.configurarConsenso(tipoConsenso, nombreColeccion);
  }

  //* --------------------
  //*  Métodos auxiliares
  //* --------------------

  private HechoDTO convertirADTO(Hecho hecho) {
    return new HechoDTO(hecho.getId(), hecho.getColeccionNombre(), hecho.getTitulo());
  }

  private FuenteDTO convertirAFuenteDTO(Fuente fuente) {
    return new FuenteDTO(fuente.getId(), fuente.getNombre(), fuente.getEndpoint());
  }

  private void syncFuentesIfNeeded() {
    if (!this.DB_outdated_Fuentes) {
      return;
    }
    List<Fuente> fuentes = fuenteRepository.findAll();
    agregador.setLista_fuentes(fuentes);

    for (Fuente fuente : fuentes) {
      if (!agregador.getFachadaFuentes().containsKey(fuente.getId())) {
      var proxy = new FuenteProxy(objectMapper, fuente.getEndpoint());
      agregador.agregarFachadaAFuente(fuente.getId(), proxy);
      }
    }
    this.DB_outdated_Fuentes = false;
  }


  public void eliminarFuente(String id) {
    fuenteRepository.deleteById(id);
    this.DB_outdated_Fuentes = true;
  }
}