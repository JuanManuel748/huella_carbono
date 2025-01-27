package com.github.JuanManuel.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "habito")
public class Habito {
    @EmbeddedId
    private HabitoId id;

    @MapsId("idUsuario")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private com.github.JuanManuel.model.entity.Usuario idUsuario;

    @MapsId("idActividad")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_actividad", nullable = false)
    private Actividad idActividad;

    @Column(name = "frecuencia", nullable = false)
    private Integer frecuencia;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "ultima_fecha", nullable = false)
    private LocalDate ultimaFecha;

    public HabitoId getId() {
        return id;
    }

    public void setId(HabitoId id) {
        this.id = id;
    }

    public com.github.JuanManuel.model.entity.Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(com.github.JuanManuel.model.entity.Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Actividad getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Actividad idActividad) {
        this.idActividad = idActividad;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getUltimaFecha() {
        return ultimaFecha;
    }

    public void setUltimaFecha(LocalDate ultimaFecha) {
        this.ultimaFecha = ultimaFecha;
    }

}