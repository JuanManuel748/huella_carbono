package com.github.JuanManuel.tests;

import com.github.JuanManuel.model.connection.MySQLConnection;
import com.github.JuanManuel.model.connection.MySQLproperties;
import com.github.JuanManuel.utils.XMLManager;

public class testConnection {
    public static void main(String[] args) {
        /*
        MySQLproperties c = new MySQLproperties("localhost","3306","huella_carbono_db","root","");
        XMLManager.writeXML(c,"connection.xml");
        /* */
        MySQLproperties c = XMLManager.readXML(new MySQLproperties(),"connection.xml");
        MySQLConnection conn = MySQLConnection.getConnection();
        if (conn!= null) {
            System.out.println("Conexion exitosa");
        } else {
            System.out.println("Conexion fallida");
        }
    }
}
