package com.github.JuanManuel.model.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "actividad")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private com.github.JuanManuel.model.entity.Categoria idCategoria;

    @OneToMany(mappedBy = "idActividad")
    private Set<com.github.JuanManuel.model.entity.Habito> habitos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idActividad")
    private Set<com.github.JuanManuel.model.entity.Huella> huellas = new LinkedHashSet<>();

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

    public com.github.JuanManuel.model.entity.Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(com.github.JuanManuel.model.entity.Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Set<com.github.JuanManuel.model.entity.Habito> getHabitos() {
        return habitos;
    }

    public void setHabitos(Set<com.github.JuanManuel.model.entity.Habito> habitos) {
        this.habitos = habitos;
    }

    public Set<com.github.JuanManuel.model.entity.Huella> getHuellas() {
        return huellas;
    }

    public void setHuellas(Set<com.github.JuanManuel.model.entity.Huella> huellas) {
        this.huellas = huellas;
    }

    // toString
    @Override
    public String toString() {
        return "Actividad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", idCategoria=" + idCategoria +
                '}';
    }
}