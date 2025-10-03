package ar.edu.utn.dds.k3003.model;

import java.util.List;

public class ConsensoEstricto  implements ConsensoStrategy {
    @Override
    public List<Hecho> aplicar(List<Hecho> hechos, List<Fuente> fuentes) {
        // Ejemplo: devolver solo hechos que no tengan solicitudes
        return hechos.stream()
                     .filter(h -> h.getSolicitudes() == 0)
                     .toList();
    }
}