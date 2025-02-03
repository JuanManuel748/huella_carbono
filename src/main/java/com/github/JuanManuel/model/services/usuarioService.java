package com.github.JuanManuel.model.services;

import com.github.JuanManuel.model.DAOs.usuarioDAO;
import com.github.JuanManuel.model.entities.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class usuarioService implements service<Usuario> {
    private String name;
    private String email;
    private String password;
    private LocalDate date;

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

    public static usuarioService build() {return new usuarioService();}
}
