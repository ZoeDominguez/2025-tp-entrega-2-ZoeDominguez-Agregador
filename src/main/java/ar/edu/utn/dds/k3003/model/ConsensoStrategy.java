package ar.edu.utn.dds.k3003.model;

import java.util.List;


public interface ConsensoStrategy {
    List<Hecho> aplicar(List<Hecho> hechos, List<Fuente> fuentes);
}
