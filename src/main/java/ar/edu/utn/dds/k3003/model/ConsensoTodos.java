package ar.edu.utn.dds.k3003.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConsensoTodos implements ConsensoStrategy {

    @Override
    public List<Hecho> aplicar(List<Hecho> hechos, List<Fuente> fuentes){
        return new ArrayList<>(hechos.stream()
                .collect(Collectors.toMap(
                        Hecho::getTitulo,
                        Function.identity(),
                        (existente, nuevo) -> existente))
                        .values());
    }
}


