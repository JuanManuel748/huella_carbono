package com.github.JuanManuel.model.services;

import com.github.JuanManuel.model.DAOs.actividadDAO;
import com.github.JuanManuel.model.entities.Actividad;
import com.github.JuanManuel.model.entities.Categoria;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class actividadService implements service<Actividad>{
    private Integer id;
    private String nombre;
    private Integer id_categoria;
    private Categoria categoria;

    @Override
    public boolean insert(Actividad entity) {
        boolean result = false;
        try {
            if(validate(entity)) {
                Actividad tempAct = actividadDAO.build().findByName(entity);
                if (tempAct == null) {
                    if (actividadDAO.build().insert(entity)) {
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
    public boolean update(Actividad entity) {
        boolean result = false;
        try {
            if (entity.getId() != null) {
                Actividad tempAct = actividadDAO.build().findByPK(entity);
                if (tempAct != null) {
                    if (validate(entity)) {
                        if (actividadDAO.build().update(entity)) {
                            result = true;
                        }
                    }
                }
            } else {
                Actividad tempAct2 = actividadDAO.build().findByName(entity);
                if (tempAct2 != null) {
                    entity.setId(tempAct2.getId());
                    if (validate(entity)) {
                        if (actividadDAO.build().update(entity)) {
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
    public boolean delete(Actividad entity) {
        boolean result = false;
        try {
            if (entity.getId() != null) {
                Actividad tempAct = actividadDAO.build().findByPK(entity);
                if (tempAct != null) {
                    entity = tempAct;
                    if (actividadDAO.build().delete(entity)) {
                        result = true;
                    }
                }
            } else {
                Actividad tempAct2 = actividadDAO.build().findByName(entity);
                if (tempAct2 != null) {
                    entity = tempAct2;
                    if (actividadDAO.build().delete(entity)) {
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
    public Actividad findByPK(Actividad pk) {
        Actividad result = new Actividad();
        try {
            if (pk != null) {
                if (pk.getId() != null) {
                    result = actividadDAO.build().findByPK(pk);
                }
            } else {
                if (pk.getNombre() != null && !pk.getNombre().isEmpty()) {
                    result = actividadDAO.build().findByName(pk);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Actividad> findAll() {
        List<Actividad> ls = new ArrayList<>();
        try {
            ls = actividadDAO.build().findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ls;

    }

    @Override
    public boolean validate(Actividad entity) {
        boolean result = false;
        nombre = entity.getNombre();
        categoria = entity.getIdCategoria();
        if (nombre != null && !nombre.isEmpty() && categoria != null) {
            Categoria tempCat = categoriaService.build().findByPK(categoria);
            if (tempCat != null) {
                categoria = tempCat;
                entity.setIdCategoria(categoria);
                result = true;
            }
        }
        return result;
    }

    public static actividadService build() {
        return new actividadService();
    }
}
