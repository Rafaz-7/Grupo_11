package com.rafaelcosmo.modelo;

import java.util.Arrays;

public class Tablero {
    private Simbolo[][] matriz;

    public Tablero() {
        matriz = new Simbolo[3][3];
        for (int i = 0; i < 3; i++) {
            Arrays.fill(matriz[i], Simbolo.VACIO);
        }
    }

    private Tablero(Simbolo[][] estadoAnterior) {
        matriz = new Simbolo[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(estadoAnterior[i], 0, matriz[i], 0, 3);
        }
    }

    public Simbolo getCelda(int fila, int col) {
        return matriz[fila][col];
    }

    public void setCelda(int fila, int col, Simbolo simbolo) {
        if (matriz[fila][col] == Simbolo.VACIO) {
            matriz[fila][col] = simbolo;
        }
    }

    public boolean estaLleno() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matriz[i][j] == Simbolo.VACIO) return false;
            }
        }
        return true;
    }

    public Tablero clonar() {
        return new Tablero(this.matriz);
    }
}

