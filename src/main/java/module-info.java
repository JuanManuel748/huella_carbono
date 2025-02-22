module com.github.JuanManuel {
    requires java.xml.bind;
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.management;
    requires kernel;
    requires layout;
    requires java.desktop;
    requires io;
    requires org.jfree.jfreechart;

    opens com.github.JuanManuel to javafx.fxml;
    opens com.github.JuanManuel.model.connection to java.xml.bind;
    opens com.github.JuanManuel.view to javafx.fxml;
    opens com.github.JuanManuel.model.entities;

    exports com.github.JuanManuel;
    exports com.github.JuanManuel.view;
    exports com.github.JuanManuel.model.entities;
}