package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.dto.HechoDTO;

import java.util.List;
import java.util.NoSuchElementException;

public interface FachadaFuente {
  List<HechoDTO> buscarHechosXColeccion(String coleccionId) throws NoSuchElementException;

  List<HechoDTO> buscarHechosXColeccionSinSolicitudes(String id);

  List<HechoDTO> buscarHechos();
}
