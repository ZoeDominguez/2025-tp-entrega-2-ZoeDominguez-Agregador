package ar.edu.utn.dds.k3003.dto;

import ar.edu.utn.dds.k3003.model.*;

public enum ConsensosEnum {
    TODOS(new ConsensoTodos()),
    AL_MENOS_2(new ConsensoAlMenos2()),
    ESTRICTO(new ConsensoEstricto());

    private final ConsensoStrategy strategy;

    ConsensosEnum(ConsensoStrategy strategy) {
        this.strategy = strategy;
    }

    public ConsensoStrategy getStrategy() {
        return strategy;
    }
  }
  