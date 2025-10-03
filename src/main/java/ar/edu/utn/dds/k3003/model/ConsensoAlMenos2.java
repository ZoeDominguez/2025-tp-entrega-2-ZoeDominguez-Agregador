package ar.edu.utn.dds.k3003.model;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConsensoAlMenos2 implements ConsensoStrategy {
    @Override
    public List<Hecho> aplicar(List<Hecho> hechos, List<Fuente> fuentes) {
        if (fuentes.size() == 1) {
            return new ArrayList<>(hechos);
        }
        Set<String> titulosRepetidos = hechos.stream()
            .collect(Collectors.groupingBy(Hecho::getTitulo,
                     Collectors.mapping(Hecho::getOrigen, Collectors.toSet())))
            .entrySet().stream()
            .filter(e -> e.getValue().size() >= 2)
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());

        return hechos.stream()
            .filter(h -> titulosRepetidos.contains(h.getTitulo()))
            .collect(Collectors.toMap(Hecho::getTitulo, Function.identity(),
                                      (h1, h2) -> h1))
            .values().stream().toList();
    }
}
