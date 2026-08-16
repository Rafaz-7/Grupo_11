module com.rafaelcosmo.grupo_11 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.rafaelcosmo.grupo_11 to javafx.fxml;
    exports com.rafaelcosmo.grupo_11;

    // Permisos para que JavaFX lea nuestra arquitectura
    opens com.rafaelcosmo.grupo_11.vista to javafx.fxml;
    exports com.rafaelcosmo.grupo_11.vista;
    exports com.rafaelcosmo.grupo_11.modelo;
    exports com.rafaelcosmo.grupo_11.minimax;
}