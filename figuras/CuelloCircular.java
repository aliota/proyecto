package com.ae.proyecto.figuras;

import java.math.BigDecimal;

public class CuelloCircular extends Figura {
    private int diametro;
    private int largo;

    public CuelloCircular (int diametro, int largo, int cantidad, BigDecimal calibre) {
        super(0, 0, cantidad, calibre);
        this.diametro = diametro;
        this.largo = largo;
    }

    public int getDiametro() {
        return diametro;
    }

    public int getLargo() {
        return largo;
    }
    public String toString() {
        return super.toString() + String.format(
                " | dims D=%s, l=%s", this.diametro, this.largo
        );
    }
}