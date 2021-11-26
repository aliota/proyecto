package com.ae.proyecto.figuras;

import java.math.BigDecimal;

public class Garganta extends Figura {
    private int radio;

    public Garganta(int ancho, int alto, int radio, int cantidad, BigDecimal calibre) {
        super(ancho, alto, cantidad, calibre);
        this.radio = radio;
    }

    public int getRadio() {
        return this.radio;
    }

    public String toString() {
        return super.toString() + String.format(
                " | dims %sx%s; r=%s", this.getAncho(), this.getAlto(), this.radio
        );
    }
}
