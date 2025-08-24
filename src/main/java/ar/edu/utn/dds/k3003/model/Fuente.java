package ar.edu.utn.dds.k3003.model;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.dds.k3003.facades.FachadaFuente;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Fuente")
@NoArgsConstructor
public class Fuente {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "endpoint", nullable = false)
    private String endpoint;

    @Transient
    private FachadaFuente fachadaFuente;

    @OneToMany(mappedBy = "fuente", cascade = CascadeType.ALL)
    private List<Hecho> lista_hechos = new ArrayList<>();

    public List<Hecho> obtenerHechos(String coleccionId) {
        return lista_hechos;
    }

    public Fuente(String id, String nombre, String endpoint) {
        this.id = id;
        this.nombre = nombre;
        this.endpoint = endpoint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public FachadaFuente getFachadaFuente() {
        return fachadaFuente;
    }

    public void setFachadaFuente(FachadaFuente fachadaFuente) {
        this.fachadaFuente = fachadaFuente;
    }

    public List<Hecho> getLista_hechos() {
        return lista_hechos;
    }

    public void setLista_hechos(List<Hecho> lista_hechos) {
        this.lista_hechos = lista_hechos;
    }
}
