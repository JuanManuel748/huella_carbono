package com.github.JuanManuel.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "factor_emision", nullable = false, precision = 10, scale = 3)
    private BigDecimal factorEmision;

    @Column(name = "unidad", nullable = false, length = 5)
    private String unidad;

    @OneToMany(mappedBy = "idCategoria")
    private List<Actividad> actividades = new ArrayList<>();

    @OneToMany(mappedBy = "idCategoria")
    private List<com.github.JuanManuel.model.entities.Recomendacion> recomendaciones = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getFactorEmision() {
        return factorEmision;
    }

    public void setFactorEmision(BigDecimal factorEmision) {
        this.factorEmision = factorEmision;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividads) {
        this.actividades = actividads;
    }

    public List<com.github.JuanManuel.model.entities.Recomendacion> getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(List<com.github.JuanManuel.model.entities.Recomendacion> recomendacions) {
        this.recomendaciones = recomendacions;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", factorEmision=" + factorEmision +
                ", unidad='" + unidad + '\'' +
                ", actividades=" + actividades +
                ", recomendaciones=" + recomendaciones +
                '}';
    }

    public String print() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", factorEmision=" + factorEmision +
                ", unidad='" + unidad + '\'' +
                '}';
    }
}