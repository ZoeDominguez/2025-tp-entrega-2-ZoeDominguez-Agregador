package ar.edu.utn.dds.k3003.repository;

import ar.edu.utn.dds.k3003.model.Hecho;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface JpaHechoRepository extends JpaRepository<Hecho, String>, HechoRepository {
}