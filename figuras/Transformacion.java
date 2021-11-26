package com.ae.proyecto.figuras;

import java.math.BigDecimal;

public class Transformacion extends Figura {
    private int hasta_ancho;
    private int hasta_alto;
    private int hasta_largo;

    public Transformacion(int de_ancho, int de_alto, int hasta_ancho, int hasta_alto,
                          int hasta_largo, int cantidad, BigDecimal calibre) {
        super(de_ancho, de_alto, cantidad, calibre);
        this.hasta_ancho = hasta_ancho;
        this.hasta_alto = hasta_alto;
        this.hasta_largo = hasta_largo;
    }

    public int getDeAncho() {
        return this.getAncho();
    }

    public int getDeAlto() {
        return this.getAlto();
    }

    public int getHastaAncho() {
        return this.hasta_ancho;
    }

    public int getHastaAlto() {
        return this.hasta_alto;
    }

    public int getHastaLargo() {
        return this.hasta_largo;
    }

    public String toString() {
        return super.toString() + String.format(
                " | dims de=%sx%s; hasta=%sx%sx%s", this.getAncho(), this.getAlto(), this.hasta_ancho,
                this.hasta_alto, this.hasta_largo
        );
    }
}
