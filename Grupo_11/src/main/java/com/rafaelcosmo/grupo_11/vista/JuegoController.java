/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rafaelcosmo.grupo_11.vista;

/**
 *
 * @author Rafael Cosmo
 */
import com.rafaelcosmo.grupo_11.minimax.MotorMinimax;
import com.rafaelcosmo.grupo_11.minimax.UtilidadTablero;
import com.rafaelcosmo.grupo_11.modelo.Simbolo;
import com.rafaelcosmo.grupo_11.modelo.Tablero;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class JuegoController {

    @FXML private GridPane gridTablero;
    @FXML private ComboBox<String> cbSimboloHumano;
    @FXML private ComboBox<String> cbQuienInicia;
    @FXML private ComboBox<String> cbModoJuego; 
    @FXML private Label lblEstado;
    @FXML private Button btnSugerir; 

    private Tablero tablero;
    private MotorMinimax motorComputadora;
    private UtilidadTablero evaluador;
    
    private Simbolo simboloJugador1;
    private Simbolo simboloJugador2; 
    private Simbolo turnoActual;
    
    private boolean juegoActivo;
    private boolean contraComputadora; 
    private Button[][] botones;

    @FXML
    public void initialize() {
        evaluador = new UtilidadTablero();
        botones = new Button[3][3];
        
        //Opciones de modos de juego
        cbModoJuego.getItems().addAll("Humano vs Computadora", "Humano vs Humano", "Computadora vs Computadora");
        cbSimboloHumano.getItems().addAll("X", "O");
        cbQuienInicia.getItems().addAll("Jugador 1", "Jugador 2 (o Computadora)");
        
        cbModoJuego.getSelectionModel().selectFirst();
        cbSimboloHumano.getSelectionModel().selectFirst();
        cbQuienInicia.getSelectionModel().selectFirst();
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = new Button("");
                btn.setPrefSize(100, 100);
                btn.setStyle("-fx-font-size: 2.5em; -fx-font-weight: bold;");
                
                int fila = i;
                int col = j;
                btn.setOnAction(e -> clickCelda(fila, col));
                
                botones[i][j] = btn;
                gridTablero.add(btn, j, i);
            }
        }
        desactivarTablero();
    }

    @FXML
    public void iniciarJuego() {
        tablero = new Tablero();
        juegoActivo = true;
        limpiarBotones();
        gridTablero.setDisable(false);
        btnSugerir.setDisable(false);

        contraComputadora = cbModoJuego.getValue().equals("Humano vs Computadora");
        simboloJugador1 = cbSimboloHumano.getValue().equals("X") ? Simbolo.X : Simbolo.O;
        simboloJugador2 = (simboloJugador1 == Simbolo.X) ? Simbolo.O : Simbolo.X;
        
        if (contraComputadora) {
            motorComputadora = new MotorMinimax(simboloJugador2);
        }

        turnoActual = cbQuienInicia.getValue().equals("Jugador 1") ? simboloJugador1 : simboloJugador2;

        if (cbModoJuego.getValue().equals("Computadora vs Computadora")) {
            btnSugerir.setText("Siguiente Turno Computadora");
            lblEstado.setText("Modo Computadora vs Computadora: Usa el botón superior para avanzar el turno.");
        } else {
            btnSugerir.setText("Sugerir Movimiento");
            lblEstado.setText("Turno actual: " + turnoActual);
        }

        if (contraComputadora && turnoActual == simboloJugador2) {
            juegaComputadora();
        }
    }

    private void clickCelda(int fila, int col) {
        if (!juegoActivo || tablero.getCelda(fila, col) != Simbolo.VACIO) {
            return;
        }

        if (cbModoJuego.getValue().equals("Computadora vs Computadora")) {
            return;
        }

        // Si es contra la computadora y no es el turno del humano, ignorar el click
        if (contraComputadora && turnoActual != simboloJugador1) {
            return; 
        }

        // Ejecutar el movimiento
        tablero.setCelda(fila, col, turnoActual);
        actualizarVista();

        if (revisarFinJuego(turnoActual)) return;

        // Ceder el turno
        turnoActual = (turnoActual == simboloJugador1) ? simboloJugador2 : simboloJugador1;
        lblEstado.setText("Turno actual: " + turnoActual);

        // Si es el turno de la computadora, llamarla
        if (contraComputadora && turnoActual == simboloJugador2) {
            juegaComputadora();
        }
    }

    private void juegaComputadora() {
        if (!juegoActivo) return;

        Tablero mejorMovimiento = motorComputadora.obtenerMejorMovimiento(tablero);
        if (mejorMovimiento != null) {
            tablero = mejorMovimiento;
        }
        actualizarVista();

        if (revisarFinJuego(simboloJugador2)) return;

        turnoActual = simboloJugador1;
        lblEstado.setText("Turno actual: " + turnoActual);
    }

    //al servicio del humano
   @FXML
    public void sugerirMovimiento() {
        if (!juegoActivo) return;
        
        if (cbModoJuego.getValue().equals("Computadora vs Computadora")) {
            MotorMinimax motorGuerra = new MotorMinimax(turnoActual);
            Tablero mejorMovimiento = motorGuerra.obtenerMejorMovimiento(tablero);
            if (mejorMovimiento != null) {
                tablero = mejorMovimiento;
            }
            actualizarVista();

            if (revisarFinJuego(turnoActual)) return;

            turnoActual = (turnoActual == simboloJugador1) ? simboloJugador2 : simboloJugador1;
            lblEstado.setText("Turno actual: " + turnoActual + " (Presiona Siguiente Turno)");
            
        } else {
            // El comportamiento sugerencia para los humanos
            MotorMinimax motorSugerencia = new MotorMinimax(turnoActual);
            Tablero tableroSugerido = motorSugerencia.obtenerMejorMovimiento(tablero);
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tablero.getCelda(i, j) == Simbolo.VACIO && tableroSugerido.getCelda(i, j) == turnoActual) {
                        mostrarAlerta("Análisis Estratégico", "El movimiento matemáticamente óptimo está en la Fila " + (i + 1) + ", Columna " + (j + 1) + ".");
                        return;
                    }
                }
            }
        }
    }

    private void actualizarVista() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Simbolo s = tablero.getCelda(i, j);
                botones[i][j].setText(s == Simbolo.VACIO ? "" : s.toString());
            }
        }
    }

    private boolean revisarFinJuego(Simbolo jugador) {
        if (evaluador.esVictoria(tablero, jugador)) {
            juegoActivo = false;
            lblEstado.setText("Ganador: " + jugador);
            desactivarTablero();
            mostrarAlerta("Fin de la partida", "El ganador es: " + jugador);
            return true;
        } else if (tablero.estaLleno()) {
            juegoActivo = false;
            lblEstado.setText("Empate");
            desactivarTablero();
            mostrarAlerta("Fin de la partida", "¡Ha ocurrido un empate!");
            return true;
        }
        return false;
    }

    private void limpiarBotones() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j].setText("");
            }
        }
    }
    
    private void desactivarTablero() {
        gridTablero.setDisable(true);
        btnSugerir.setDisable(true);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}