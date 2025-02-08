package com.github.JuanManuel.model.services;

import com.github.JuanManuel.model.DAOs.actividadDAO;
import com.github.JuanManuel.model.entities.Actividad;
import com.github.JuanManuel.model.entities.Categoria;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing Actividad entities.
 * Provides methods to perform CRUD operations and validation.
 */
public class actividadService implements service<Actividad> {
    private Integer id;
    private String nombre;
    private Integer id_categoria;
    private Categoria categoria;

    /**
     * Inserts a new Actividad entity into the database.
     *
     * @param entity the Actividad entity to be inserted.
     * @return true if the insertion was successful.
     */
    @Override
    public boolean insert(Actividad entity) {
        boolean result = false;
        try {
            if (validate(entity)) {
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

    /**
     * Updates an existing Actividad entity in the database.
     *
     * @param entity the Actividad entity to be updated.
     * @return true if the update was successful.
     */
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

    /**
     * Deletes an existing Actividad entity from the database.
     *
     * @param entity the Actividad entity to be deleted.
     * @return true if the deletion was successful.
     */
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

    /**
     * Finds an Actividad entity by its primary key.
     *
     * @param pk the primary key of the Actividad entity to be found.
     * @return the found Actividad entity, or null if not found.
     */
    @Override
    public Actividad findByPK(Actividad pk) {
        Actividad result = new Actividad();
        try {
            if (pk != null) {
                if (pk.getId() != null) {
                    result = actividadDAO.build().findByPK(pk);
                } else {
                    if (pk.getNombre() != null && !pk.getNombre().isEmpty()) {
                        result = actividadDAO.build().findByName(pk);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Finds all Actividad entities in the database.
     *
     * @return a list of all Actividad entities.
     */
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

    /**
     * Validates an Actividad entity.
     *
     * @param entity the Actividad entity to be validated.
     * @return true if the entity is valid.
     */
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

    /**
     * Builds a new instance of actividadService.
     *
     * @return a new instance of actividadService.
     */
    public static actividadService build() {
        return new actividadService();
    }
}