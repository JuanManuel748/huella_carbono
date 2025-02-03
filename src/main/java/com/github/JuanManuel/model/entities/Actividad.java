package com.github.JuanManuel.model.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
    private com.github.JuanManuel.model.entities.Categoria idCategoria;

    @OneToMany(mappedBy = "idActividad")
    private List<com.github.JuanManuel.model.entities.Habito> habitos = new ArrayList<>();

    @OneToMany(mappedBy = "idActividad")
    private List<com.github.JuanManuel.model.entities.Huella> huellas = new ArrayList<>();

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

    public com.github.JuanManuel.model.entities.Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(com.github.JuanManuel.model.entities.Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public List<com.github.JuanManuel.model.entities.Habito> getHabitos() {
        return habitos;
    }

    public void setHabitos(List<com.github.JuanManuel.model.entities.Habito> habitos) {
        this.habitos = habitos;
    }

    public List<com.github.JuanManuel.model.entities.Huella> getHuellas() {
        return huellas;
    }

    public void setHuellas(List<com.github.JuanManuel.model.entities.Huella> huellas) {
        this.huellas = huellas;
    }

    // toString
    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}