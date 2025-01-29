package com.github.JuanManuel.model.services;

import com.github.JuanManuel.model.entities.Actividad;
import com.github.JuanManuel.model.entities.Habito;
import com.github.JuanManuel.model.entities.HabitoId;
import com.github.JuanManuel.model.entities.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class habitoService implements service<Habito> {
    private HabitoId id;
    private Usuario user;
    private Actividad act;
    private Integer frecuencia;
    private String tipo;
    private LocalDate fecha;

    @Override
    public boolean insert(Habito entity) {
        boolean result = false;

        return result;
    }

    @Override
    public boolean update(Habito entity) {
        boolean result = false;

        return result;
    }

    @Override
    public boolean delete(Habito entity) {
        boolean result = false;

        return result;
    }

    @Override
    public Habito findByPK(Habito pk) {
        Habito result = new Habito();

        return result;
    }

    @Override
    public List<Habito> findAll() {
        List<Habito> ls = new ArrayList<>();

        return ls;
    }

    @Override
    public boolean validate(Habito entity) {
        boolean result = false;

        id = entity.getId();
        user = usuarioService.build().findByPK(entity.getIdUsuario());
        act = actividadService.build().findByPK(entity.getIdActividad());
        frecuencia = entity.getFrecuencia();
        tipo = entity.getTipo();
        fecha = entity.getUltimaFecha();

        // TERMINAR


        return result;
    }
}
