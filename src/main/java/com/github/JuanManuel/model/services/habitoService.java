package com.github.JuanManuel.model.services;

import com.github.JuanManuel.model.DAOs.habitoDAO;
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
        try {
            if (validate(entity)) {
                if (entity.getId() == null) {
                    if (habitoDAO.build().insert(entity)) {
                        result = true;
                    }
                } else {
                    Habito tempHab = habitoDAO.build().findByPK(entity);
                    if (tempHab == null) {
                        if (habitoDAO.build().insert(entity)) {
                            result = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean update(Habito entity) {
        boolean result = false;

        try {
            if (entity.getId() != null) {
                Habito tempHab = habitoDAO.build().findByPK(entity);
                if (tempHab != null) {
                    if (validate(entity)) {
                        if(habitoDAO.build().update(entity)) {
                            result = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean delete(Habito entity) {
        boolean result = false;
        try {
            if (entity.getId() != null) {
                Habito tmepHab = habitoDAO.build().findByPK(entity);
                if (tmepHab != null) {
                    if (habitoDAO.build().delete(entity)) {
                        result = true;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Habito findByPK(Habito pk) {
        Habito result = new Habito();
        try {
            if (pk.getId() != null) {
                result = habitoDAO.build().findByPK(pk);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Habito> findAll() {
        List<Habito> ls = new ArrayList<>();
        try {
            ls = habitoDAO.build().findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ls;
    }

    public List<Habito> findByUser(Usuario user) {
        List<Habito> ls = new ArrayList<>();

        try {
            if (user.getId() != null || user.getEmail() != null) {
                Usuario tempUser = usuarioService.build().findByPK(user);
                if (tempUser != null) {
                    ls = habitoDAO.build().findByUser(tempUser);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ls;
    }

    public List<Habito> findByDateRange(LocalDate min, LocalDate max, Usuario currentUser) {
        List<Habito> ls = new ArrayList<>();
        try {
            if (!min.isAfter(max) && min.isAfter(LocalDate.MIN) && max.isBefore(LocalDate.MAX)) {
                if ((currentUser != null) && (currentUser.getId() != null || currentUser.getEmail() != null)) {
                    Usuario tempUser = usuarioService.build().findByPK(currentUser);
                    if (tempUser != null) {
                        ls = habitoDAO.build().findByDateRange(min, max, tempUser);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ls;
    }

    @Override
    public boolean validate(Habito entity) {
        boolean result = false;

        id = entity.getId();
        user = entity.getIdUsuario();
        act = entity.getIdActividad();
        user = usuarioService.build().findByPK(entity.getIdUsuario());
        act = actividadService.build().findByPK(entity.getIdActividad());
        frecuencia = entity.getFrecuencia();
        tipo = entity.getTipo();
        fecha = entity.getUltimaFecha();

        if (user != null && act != null && frecuencia != null && tipo != null && fecha != null && frecuencia > 0) {
            result = true;
        }
        return result;
    }

    public static habitoService build() {
        return new habitoService();
    }


}
