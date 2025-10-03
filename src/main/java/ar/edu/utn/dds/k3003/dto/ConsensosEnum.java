package ar.edu.utn.dds.k3003.dto;

import ar.edu.utn.dds.k3003.model.*;

public enum ConsensosEnum {
    TODOS(new ConsensoTodos(), false),
    AL_MENOS_2(new ConsensoAlMenos2(), false),
    ESTRICTO(new ConsensoEstricto(), true);

    private final ConsensoStrategy strategy;
    private final boolean requiereHechosSinSolicitudes;

    ConsensosEnum(ConsensoStrategy strategy, boolean requiereHechosSinSolicitudes) {
        this.strategy = strategy;
        this.requiereHechosSinSolicitudes = requiereHechosSinSolicitudes;
    }

    public ConsensoStrategy getStrategy() {
        return strategy;
    }

    public boolean requiereHechosSinSolicitudes() {
        return requiereHechosSinSolicitudes;
    }
  }
  