package ar.edu.utn.dds.k3003.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Hecho")
public class Hecho {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "nombre_coleccion", nullable = false)
    private String coleccionNombre;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "lugar", nullable = false)
    private String lugar;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "fecha_carga", nullable = false)
    private LocalDate fecha_carga;

    @Column(name = "origen", nullable = false)
    private String origen;

    @ManyToOne()
    @JoinColumn(name = "Fuente_id", referencedColumnName = "id")
    private Fuente fuente;

    public Hecho(String titulo, String id, String coleccion) {
        this.titulo = titulo;
        this.id = id;
        this.coleccionNombre = coleccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Hecho hecho = (Hecho) o;
        return Objects.equals(titulo, hecho.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColeccionNombre() {
        return coleccionNombre;
    }

    public void setColeccionNombre(String coleccionNombre) {
        this.coleccionNombre = coleccionNombre;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Fuente getFuente() {
        return fuente;
    }

    public void setFuente(Fuente fuente) {
        this.fuente = fuente;
    }
}