module com.github.JuanManuel {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.management;

    opens com.github.JuanManuel to javafx.fxml;
    opens com.github.JuanManuel.model.entity;
    exports com.github.JuanManuel;
}
