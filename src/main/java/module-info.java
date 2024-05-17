module com.example.projet_javaaa {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.example.projet_javaaa to javafx.fxml;
    exports com.example.projet_javaaa;
}