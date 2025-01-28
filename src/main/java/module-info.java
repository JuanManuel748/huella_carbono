module com.github.JuanManuel {
    requires java.xml.bind;
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.management;

    opens com.github.JuanManuel to javafx.fxml;
    opens com.github.JuanManuel.model.connection to java.xml.bind;
    opens com.github.JuanManuel.model.entities;
    exports com.github.JuanManuel;
}
