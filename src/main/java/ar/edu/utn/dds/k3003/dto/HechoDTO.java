package ar.edu.utn.dds.k3003.dto;

import java.time.LocalDateTime;
import java.util.List;

public record HechoDTO(String id,String nombreColeccion, String titulo, List<String> etiquetas,
                       CategoriaHechoEnum categoria,
                       String ubicacion, LocalDateTime fecha, String origen) {

  public HechoDTO(String id,String nombreColeccion, String titulo, List<String> etiquetas) {
    this(id, nombreColeccion, titulo, etiquetas, null, null, null, null);
  }
}
