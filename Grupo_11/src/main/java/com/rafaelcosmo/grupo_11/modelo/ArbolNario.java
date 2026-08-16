package com.rafaelcosmo.grupo_11.modelo;

public class ArbolNario<E> {
    private NodoNario<E> raiz;

    public ArbolNario(E contenidoRaiz) {
        this.raiz = new NodoNario<>(contenidoRaiz);
    }

    public NodoNario<E> getRaiz() {
        return raiz;
    }

    public boolean esHoja() {
        return raiz.getHijos().isEmpty();
    }
}
