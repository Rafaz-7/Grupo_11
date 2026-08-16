package com.rafaelcosmo.grupo_11.modelo;

import java.util.ArrayList;
import java.util.List;

public class NodoNario<E> {
    private E contenido;
    private List<ArbolNario<E>> hijos;

    public NodoNario(E contenido) {
        this.contenido = contenido;
        this.hijos = new ArrayList<>();
    }

    public E getContenido() {
        return contenido;
    }

    public void setContenido(E contenido) {
        this.contenido = contenido;
    }

    public List<ArbolNario<E>> getHijos() {
        return hijos;
    }

    public void agregarHijo(ArbolNario<E> hijo) {
        this.hijos.add(hijo);
    }
}
