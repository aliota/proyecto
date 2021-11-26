package com.ae.proyecto.figuras;

import java.math.BigDecimal;

public class Tapa extends Figura {
    public Tapa(int ancho, int alto, int cantidad, BigDecimal calibre) {
        super(ancho, alto, cantidad, calibre);
    }

    public String toString() {
        return super.toString() + String.format(
                " | dims %sx%s", this.getAncho(), this.getAlto()
        );
    }
}
