/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rafaelcosmo.grupo_11.minimax;

/**
 *
 * @author Rafael Cosmo
 */
import com.rafaelcosmo.grupo_11.modelo.Simbolo;
import com.rafaelcosmo.grupo_11.modelo.Tablero;

public class UtilidadTablero {

    // Retorna u_jugador(t) = P_jugador - P_oponente
    public int calcularUtilidad(Tablero tablero, Simbolo jugador) {
        Simbolo oponente = (jugador == Simbolo.X) ? Simbolo.O : Simbolo.X;

        int pJugador = contarLineasDisponibles(tablero, jugador, oponente);
        int pOponente = contarLineasDisponibles(tablero, oponente, jugador);

        return pJugador - pOponente;
    }

    private int contarLineasDisponibles(Tablero tablero, Simbolo jugador, Simbolo oponente) {
        int lineas = 0;
        
        // Revisar las 3 filas y 3 columnas
        for (int i = 0; i < 3; i++) {
            if (lineaDisponible(tablero.getCelda(i, 0), tablero.getCelda(i, 1), tablero.getCelda(i, 2), oponente)) lineas++;
            if (lineaDisponible(tablero.getCelda(0, i), tablero.getCelda(1, i), tablero.getCelda(2, i), oponente)) lineas++;
        }
        
        // Revisar las 2 diagonales
        if (lineaDisponible(tablero.getCelda(0, 0), tablero.getCelda(1, 1), tablero.getCelda(2, 2), oponente)) lineas++;
        if (lineaDisponible(tablero.getCelda(0, 2), tablero.getCelda(1, 1), tablero.getCelda(2, 0), oponente)) lineas++;
        
        return lineas;
    }

    // Una línea está disponible si NO contiene el símbolo del oponente
    private boolean lineaDisponible(Simbolo c1, Simbolo c2, Simbolo c3, Simbolo oponente) {
        return c1 != oponente && c2 != oponente && c3 != oponente;
    }

    // Método vital para detener el juego si hay un ganador
    public boolean esVictoria(Tablero tablero, Simbolo jugador) {
        // Revisar Filas y Columnas
        for (int i = 0; i < 3; i++) {
            if (tablero.getCelda(i, 0) == jugador && tablero.getCelda(i, 1) == jugador && tablero.getCelda(i, 2) == jugador) return true;
            if (tablero.getCelda(0, i) == jugador && tablero.getCelda(1, i) == jugador && tablero.getCelda(2, i) == jugador) return true;
        }
        // Revisar Diagonales
        if (tablero.getCelda(0, 0) == jugador && tablero.getCelda(1, 1) == jugador && tablero.getCelda(2, 2) == jugador) return true;
        if (tablero.getCelda(0, 2) == jugador && tablero.getCelda(1, 1) == jugador && tablero.getCelda(2, 0) == jugador) return true;
        
        return false;
    }
}