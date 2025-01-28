package com.github.JuanManuel.model.connection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name="connection")
@XmlAccessorType(XmlAccessType.FIELD)
public class MySQLproperties implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ip;
    private String port;
    private String database;
    private String user;
    private String pass;

    public MySQLproperties() {
    }

    public MySQLproperties(String ip, String port, String database, String user, String pass) {
        this.ip = ip;
        this.port = port;
        this.database = database;
        this.user = user;
        this.pass = pass;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "MySQLproperties{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", database='" + database + '\'' +
                ", user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }

    public String getURL() {
        return "jdbc:mysql://" + ip + ":" + port + "/" + database;
    }
}
