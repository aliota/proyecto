package com.ae.proyecto.figuras;

import java.math.BigDecimal;

public class Pantalon extends Figura {
    private int izq_ancho;
    private int izq_alto;
    private int izq_radio;
    private int izq_angulo;

    private int der_ancho;
    private int der_alto;
    private int der_radio;
    private int der_angulo;


    public Pantalon(int ini_ancho, int ini_alto, int cantidad, BigDecimal calibre) {
        super(ini_ancho, ini_alto, cantidad, calibre);
    }

    public void setLadoIzquierdo(int ancho, int alto, int radio, int angulo) {
        this.izq_ancho = ancho;
        this.izq_alto = alto;
        this.izq_radio = radio;
        this.izq_angulo = angulo;
    }

    public void setLadoDerecho(int ancho, int alto, int radio, int angulo) {
        this.der_ancho = ancho;
        this.der_alto = alto;
        this.der_radio = radio;
        this.der_angulo = angulo;
    }

    public int getLadoIzqAncho() { return this.izq_ancho; }
    public int getLadoIzqAlto() { return this.izq_alto; }
    public int getLadoIzqRadio() { return this.izq_radio; }
    public int getLadoIzqAngulo() { return this.izq_angulo; }

    public int getLadoDerAncho() { return this.der_ancho; }
    public int getLadoDerAlto() { return this.der_alto; }
    public int getLadoDerRadio() { return this.der_radio; }
    public int getLadoDerAngulo() { return this.der_angulo; }

    public String toString() {
        return super.toString() + String.format(
                " | dims ini=%sx%s; izq=%sx%s r=%s, a=%s, der=%sx%s r=%s, a=%s",
                this.getAncho(), this.getAlto(),
                this.izq_ancho,
                this.izq_alto,
                this.izq_radio,
                this.izq_angulo,

                this.der_ancho,
                this.der_alto,
                this.der_radio,
                this.der_angulo
        );
    }
}
