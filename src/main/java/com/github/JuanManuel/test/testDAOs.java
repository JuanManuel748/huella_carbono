package com.github.JuanManuel.test;

import com.github.JuanManuel.model.entity.Usuario;
import com.github.JuanManuel.model.DAO.usuarioDAO;

import java.util.List;

public class testDAOs {
    public static void main(String[] args) {
        System.out.println("HOLAAAAAAAAAAAAAAAAAAA");
        try {
            /*
            List<Usuario> listUsers = usuarioDAO.build().findAll();
            for (Usuario user : listUsers) {
                System.out.println(user);
            }
            /* */
            Usuario usr = new Usuario();
            usr.setId(1);
            Usuario result = usuarioDAO.build().findByPK(usr);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ADIOSSSSSSSSSSSSSSSSSSSSSS");
    }
}
