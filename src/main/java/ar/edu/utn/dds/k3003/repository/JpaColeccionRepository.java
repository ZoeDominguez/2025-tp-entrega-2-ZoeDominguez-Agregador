package ar.edu.utn.dds.k3003.repository;

import ar.edu.utn.dds.k3003.model.Coleccion;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface JpaColeccionRepository extends JpaRepository<Coleccion, String>, ColeccionRepository {
}