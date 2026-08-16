module com.rafaelcosmo.grupo_11 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.rafaelcosmo.grupo_11 to javafx.fxml;
    exports com.rafaelcosmo.grupo_11;
}
