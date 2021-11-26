package com.ae.proyecto.figuras;

import java.math.BigDecimal;

public class Curva extends Figura {
    private int radio;
    private int angulo;

    public Curva(int ancho, int alto, int radio, int angulo, int cantidad, BigDecimal calibre) {
        super(ancho, alto, cantidad, calibre);
        this.angulo = angulo;
        this.radio = radio;
    }

    public int getRadio() {
        return radio;
    }

    public int getAngulo() {
        return angulo;
    }

    public String toString() {
        return super.toString() + String.format(
                " | dims %sx%s; r=%s, a=%s", this.getAncho(), this.getAlto(), this.radio, this.angulo
        );
    }
}
