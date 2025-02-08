package com.github.JuanManuel.model.services;

import com.github.JuanManuel.model.DAOs.habitoDAO;
import com.github.JuanManuel.model.entities.Actividad;
import com.github.JuanManuel.model.entities.Habito;
import com.github.JuanManuel.model.entities.HabitoId;
import com.github.JuanManuel.model.entities.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing Habito entities.
 * Provides methods to perform CRUD operations and validation.
 */
public class habitoService implements service<Habito> {
    private HabitoId id;
    private Usuario user;
    private Actividad act;
    private Integer frecuencia;
    private String tipo;
    private LocalDate fecha;

    /**
     * Inserts a new Habito entity into the database.
     *
     * @param entity the Habito entity to be inserted.
     * @return true if the insertion was successful.
     */
    @Override
    public boolean insert(Habito entity) {
        boolean result = false;
        try {
            if (validate(entity)) {
                if (entity.getId() == null) {
                    entity = createId(entity);
                    if (habitoDAO.build().insert(entity)) {
                        result = true;
                    }
                } else {
                    entity = createId(entity);
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

    /**
     * Creates a new HabitoId for the given Habito entity.
     *
     * @param entity the Habito entity to create an ID for.
     * @return the Habito entity with the new ID.
     */
    private Habito createId(Habito entity) {
        Usuario tempUser = entity.getIdUsuario();
        tempUser = usuarioService.build().findByPK(tempUser);
        Actividad tempAct = entity.getIdActividad();
        tempAct = actividadService.build().findByPK(tempAct);
        entity.setId(new HabitoId(tempUser.getId(), tempAct.getId()));
        entity.setIdUsuario(tempUser);
        entity.setIdActividad(tempAct);
        return entity;
    }

    /**
     * Updates an existing Habito entity in the database.
     *
     * @param entity the Habito entity to be updated.
     * @return true if the update was successful.
     */
    @Override
    public boolean update(Habito entity) {
        boolean result = false;
        try {
            if (entity.getId() != null) {
                Habito tempHab = habitoDAO.build().findByPK(entity);
                if (tempHab != null) {
                    if (validate(entity)) {
                        if (habitoDAO.build().update(entity)) {
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
     * Deletes an existing Habito entity from the database.
     *
     * @param entity the Habito entity to be deleted.
     * @return true if the deletion was successful.
     */
    @Override
    public boolean delete(Habito entity) {
        boolean result = false;
        try {
            if (entity.getId() != null) {
                Habito tempHab = habitoDAO.build().findByPK(entity);
                if (tempHab != null) {
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

    /**
     * Finds a Habito entity by its primary key.
     *
     * @param pk the primary key of the Habito entity to be found.
     * @return the found Habito entity, or null if not found.
     */
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

    /**
     * Finds all Habito entities in the database.
     *
     * @return a list of all Habito entities.
     */
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

    /**
     * Finds Habito entities by the associated Usuario.
     *
     * @param user the Usuario entity to search by.
     * @return a list of Habito entities associated with the given Usuario.
     */
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

    /**
     * Finds Habito entities by a date range and associated Usuario.
     *
     * @param min the start date of the range.
     * @param max the end date of the range.
     * @param currentUser the Usuario entity to search by.
     * @return a list of Habito entities within the given date range and associated with the given Usuario.
     */
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

    /**
     * Validates a Habito entity.
     *
     * @param entity the Habito entity to be validated.
     * @return true if the entity is valid.
     */
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

    /**
     * Builds a new instance of habitoService.
     *
     * @return a new instance of habitoService.
     */
    public static habitoService build() {
        return new habitoService();
    }
}