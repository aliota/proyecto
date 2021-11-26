package com.ae.proyecto.figuras;

import java.math.BigDecimal;

public class Ducto extends Figura {
    private int largo;

    public Ducto(int ancho, int alto, int largo, int cantidad, BigDecimal calibre) {
        super(ancho, alto, cantidad, calibre);
        this.largo = largo;
    }

    public int getLargo() {
        return this.largo;
    }

    public String toString() {
        return super.toString() + String.format(" | dims %sx%sx%s", this.getAncho(), this.getAlto(), this.largo);
    }
}
