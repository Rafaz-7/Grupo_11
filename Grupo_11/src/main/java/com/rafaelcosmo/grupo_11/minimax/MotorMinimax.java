/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rafaelcosmo.grupo_11.minimax;

/**
 *
 * @author Rafael Cosmo
 */
import com.rafaelcosmo.grupo_11.modelo.ArbolNario;
import com.rafaelcosmo.grupo_11.modelo.NodoNario;
import com.rafaelcosmo.grupo_11.modelo.Simbolo;
import com.rafaelcosmo.grupo_11.modelo.Tablero;

public class MotorMinimax {
    private Simbolo computadora;
    private Simbolo humano;
    private UtilidadTablero evaluador; 

    public MotorMinimax(Simbolo computadora) {
        this.computadora = computadora;
        this.humano = (computadora == Simbolo.X) ? Simbolo.O : Simbolo.X;
        this.evaluador = new UtilidadTablero(); // Instancia normal, sin usar métodos estáticos
    }

    // Retorna el tablero resultante tras el mejor movimiento posible de la computadora
    public Tablero obtenerMejorMovimiento(Tablero tableroActual) {
        ArbolNario<Tablero> arbol = new ArbolNario<>(tableroActual);

        // Nivel 1 del árbol: Generar todas las jugadas posibles de la computadora
        generarHijos(arbol.getRaiz(), computadora);

        int maxUtilidad = Integer.MIN_VALUE;
        Tablero mejorTablero = null;

        for (ArbolNario<Tablero> subArbol1 : arbol.getRaiz().getHijos()) {
            NodoNario<Tablero> nodoNivel1 = subArbol1.getRaiz();

            // Atajo: Si este movimiento asegura la victoria inmediata, lo elegimos sin dudar
            if (evaluador.esVictoria(nodoNivel1.getContenido(), computadora)) {
                return nodoNivel1.getContenido(); 
            }

            // Nivel 2 del árbol: Generar las posibles respuestas del oponente (humano)
            generarHijos(nodoNivel1, humano);

            int minUtilidadLocal = Integer.MAX_VALUE;

            // Si el tablero se llena en el Nivel 1, calculamos la utilidad ahí mismo
            if (nodoNivel1.getHijos().isEmpty()) {
                minUtilidadLocal = evaluador.calcularUtilidad(nodoNivel1.getContenido(), computadora);
            } else {
                // Evaluar las hojas (Nivel 2) para encontrar la utilidad mínima de esta rama
                for (ArbolNario<Tablero> subArbol2 : nodoNivel1.getHijos()) {
                    Tablero tableroNieto = subArbol2.getRaiz().getContenido();
                    int utilidadNieto = evaluador.calcularUtilidad(tableroNieto, computadora);
                    
                    if (utilidadNieto < minUtilidadLocal) {
                        minUtilidadLocal = utilidadNieto;
                    }
                }
            }
            imprimirAnalisisConsola(nodoNivel1.getContenido(), minUtilidadLocal);
            
            //Elegir el tablero propio con la MÁXIMA de todas las utilidades mínimas

            // La computadora elige la rama cuya utilidad mínima sea la más alta
            if (minUtilidadLocal > maxUtilidad) {
                maxUtilidad = minUtilidadLocal;
                mejorTablero = nodoNivel1.getContenido();
            }
        }

        // Respaldo de seguridad por si todos los movimientos son desfavorables
        if (mejorTablero == null && !arbol.getRaiz().getHijos().isEmpty()) {
            mejorTablero = arbol.getRaiz().getHijos().get(0).getRaiz().getContenido();
        }

        return mejorTablero;
    }

    // Crea nuevos nodos hijos por cada casilla vacía disponible en el tablero actual
    private void generarHijos(NodoNario<Tablero> nodoPadre, Simbolo turnoActual) {
        Tablero tableroPadre = nodoPadre.getContenido();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tableroPadre.getCelda(i, j) == Simbolo.VACIO) {
                    Tablero nuevoTablero = tableroPadre.clonar();
                    nuevoTablero.setCelda(i, j, turnoActual);
                    nodoPadre.agregarHijo(new ArbolNario<>(nuevoTablero));
                }
            }
        }
    }
    //Mostrar los tableros intermedios y los valores de utilidad
    private void imprimirAnalisisConsola(Tablero t, int utilidad) {
        System.out.println("Tablero analizado (Utilidad Minima Garantizada: " + utilidad + ")");
        for (int i = 0; i < 3; i++) {
            System.out.println("  " + t.getCelda(i,0) + " | " + t.getCelda(i,1) + " | " + t.getCelda(i,2));
        }
        System.out.println("---------------------");
    }
}

