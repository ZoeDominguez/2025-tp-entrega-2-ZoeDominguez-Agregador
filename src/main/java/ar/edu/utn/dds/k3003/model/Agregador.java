package ar.edu.utn.dds.k3003.model;

import java.util.*;

import ar.edu.utn.dds.k3003.app.FachadaFuente;
import ar.edu.utn.dds.k3003.dto.ConsensosEnum;
import ar.edu.utn.dds.k3003.dto.HechoDTO;
import lombok.Data;

@Data
public class Agregador {

    private List<Fuente> lista_fuentes = new ArrayList<>();
    private Map<String, FachadaFuente> fachadaFuentes = new HashMap<>();
    private Map<String, ConsensosEnum> tipoConsensoXColeccion = new HashMap<>();

    public void setLista_fuentes(List<Fuente> fuentes) {
        this.lista_fuentes = new ArrayList<>(fuentes);
    }

    public void configurarConsenso(ConsensosEnum consenso, String nombreColeccion) {
        tipoConsensoXColeccion.put(nombreColeccion, consenso);
    }

    public List<Hecho> obtenerHechosDeTodasLasFuentes(String nombreColeccion, boolean sinSolicitudes) {
    List<Hecho> hechos = new ArrayList<>();

    for (Fuente fuente : lista_fuentes) {
        FachadaFuente fachada = fachadaFuentes.get(fuente.getId());
        if (fachada == null) continue;

        try {
            List<HechoDTO> hechosDTO;

            if (nombreColeccion == null) {
                hechosDTO = fachada.buscarHechos();
                 System.out.println("Hechos encontrados: " + (hechosDTO != null ? hechosDTO.size() : "null"));
            } else {
                hechosDTO = sinSolicitudes
                        ? fachada.buscarHechosXColeccionSinSolicitudes(nombreColeccion)
                        : fachada.buscarHechosXColeccion(nombreColeccion);
            }

            if (hechosDTO == null || hechosDTO.isEmpty()) continue;

            hechos.addAll(hechosDTO.stream()
                    .map(dto -> {
                        Hecho hecho = new Hecho(dto.titulo(), dto.id(), dto.nombreColeccion());
                        hecho.setOrigen(fuente.getId());
                        return hecho;
                    })
                    .toList());

        } catch (Exception e) {
            System.err.println("Error al obtener hechos de la fuente " + fuente.getNombre() + ": " + e.getMessage());
        }
    }
    return hechos;
}


    public List<Hecho> obtenerHechosPorColeccion(String nombreColeccion) {

        if (!tipoConsensoXColeccion.containsKey(nombreColeccion)) {
            return Collections.emptyList();
        }

        ConsensosEnum estrategia = tipoConsensoXColeccion.get(nombreColeccion);
        List<Hecho> hechos = obtenerHechosDeTodasLasFuentes(nombreColeccion, estrategia.requiereHechosSinSolicitudes());

        return estrategia.getStrategy().aplicar(hechos, lista_fuentes);
    }

    public void agregarFachadaAFuente(String fuenteId, FachadaFuente fuente) {
        Fuente existe_Fuente = lista_fuentes.stream()
                .filter(f -> f.getId().equals(fuenteId))
                .findAny()
                .orElse(null);

        if (existe_Fuente == null) {
            throw new NoSuchElementException("No se encontro la fuente");
        }
        fachadaFuentes.put(fuenteId, fuente);
        existe_Fuente.setFachadaFuente(fuente);
    }
    
}