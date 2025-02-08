package com.github.JuanManuel.model.services;

import com.github.JuanManuel.model.DAOs.usuarioDAO;
import com.github.JuanManuel.model.entities.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing Usuario entities.
 * Provides methods to perform CRUD operations and validation.
 */
public class usuarioService implements service<Usuario> {
    private String name;
    private String email;
    private String password;
    private LocalDate date;

    /**
     * Inserts a new Usuario entity into the database.
     *
     * @param entity the Usuario entity to be inserted.
     * @return true if the insertion was successful.
     */
    @Override
    public boolean insert(Usuario entity) {
        boolean inserted = false;
        try {
            email = entity.getEmail();
            if (email != null && !email.isEmpty()) {
                Usuario tempUser = usuarioDAO.build().findByEmail(email);
                if (tempUser == null) {
                    if (validate(entity)) {
                        if (usuarioDAO.build().insert(entity)) {
                            inserted = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inserted;
    }

    /**
     * Updates an existing Usuario entity in the database.
     *
     * @param entity the Usuario entity to be updated.
     * @return true if the update was successful.
     */
    @Override
    public boolean update(Usuario entity) {
        boolean updated = false;
        try {
            email = entity.getEmail();
            if (entity.getId() != null) {
                Usuario tempUser2 = usuarioDAO.build().findByPK(entity);
                if (tempUser2 != null) {
                    if (validate(entity)) {
                        if (usuarioDAO.build().update(entity)) {
                            updated = true;
                        }
                    }
                }
            } else if (email != null && !email.isEmpty()) {
                Usuario tempUser = usuarioDAO.build().findByEmail(email);
                if (tempUser != null) {
                    entity.setId(tempUser.getId());
                    if (validate(entity)) {
                        if (usuarioDAO.build().update(entity)) {
                            updated = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
    }

    /**
     * Deletes an existing Usuario entity from the database.
     *
     * @param entity the Usuario entity to be deleted.
     * @return true if the deletion was successful.
     */
    @Override
    public boolean delete(Usuario entity) {
        boolean deleted = false;
        try {
            String email = entity.getEmail();

            if (email != null && !email.isEmpty()) {
                Usuario tempUser = usuarioDAO.build().findByEmail(email);
                if (tempUser != null && tempUser.getContraseña() != null) {
                    entity = tempUser;
                    if (usuarioDAO.build().delete(entity)) {
                        deleted = true;
                    }
                }
            } else {
                if (entity.getId() != null) {
                    Usuario tempUser2 = usuarioDAO.build().findByPK(entity);
                    if (tempUser2 != null && tempUser2.getContraseña() != null) {
                        entity = tempUser2;
                        if (usuarioDAO.build().delete(entity)) {
                            deleted = true;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleted;
    }

    /**
     * Finds a Usuario entity by its primary key.
     *
     * @param pk the primary key of the Usuario entity to be found.
     * @return the found Usuario entity, or null if not found.
     */
    @Override
    public Usuario findByPK(Usuario pk) {
        Usuario result = null;
        try {
            if (pk != null) {
                if (pk.getId() != null) {
                    result = usuarioDAO.build().findByPK(pk);
                } else if (pk.getEmail() != null && !pk.getEmail().isEmpty()) {
                    result = usuarioDAO.build().findByEmail(pk.getEmail());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Finds all Usuario entities in the database.
     *
     * @return a list of all Usuario entities.
     */
    @Override
    public List<Usuario> findAll() {
        List<Usuario> ls = new ArrayList<>();
        try {
            ls = usuarioDAO.build().findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    /**
     * Validates a Usuario entity.
     *
     * @param entity the Usuario entity to be validated.
     * @return true if the entity is valid.
     */
    @Override
    public boolean validate(Usuario entity) {
        boolean result = false;
        name = entity.getNombre();
        email = entity.getEmail();
        password = entity.getContraseña();
        date = entity.getFechaRegistro();

        if (name != null && email != null && password != null && date != null) {
            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Builds a new instance of usuarioService.
     *
     * @return a new instance of usuarioService.
     */
    public static usuarioService build() {
        return new usuarioService();
    }
}