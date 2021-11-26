package com.ae.proyecto.figuras;

import java.math.BigDecimal;

public class CuelloRectangular extends Ducto {
    public CuelloRectangular (int ancho, int alto, int largo, int cantidad, BigDecimal calibre) {
        super(ancho, alto, largo, cantidad, calibre);
    }

    public String toString() {
        return super.toString() + String.format(
                " | dims %sx%sx%s", this.getAncho(), this.getAlto(), this.getLargo()
        );
    }
}
