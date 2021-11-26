package com.ae.proyecto.figuras;

import java.math.BigDecimal;

public class Figura {
    private int ancho;
    private int alto;
    private int cantidad;
    private BigDecimal calibre;

    private String union_inicial;
    private String union_final;
    private String direccion;

    public Figura(int ancho, int alto, int cantidad, BigDecimal calibre) {
        this.ancho = ancho;
        this.alto = alto;
        this.cantidad = cantidad;
        this.calibre = calibre;
    }

    public void setObservaciones(String union_inicial, String union_final, String direccion) {
        this.union_inicial = union_inicial;
        this.union_final = union_final;
        this.direccion = direccion;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getCalibre() {
        return calibre;
    }

    public String getUnionInicial() {
        return union_inicial;
    }

    public String getUnionFinal() {
        return union_final;
    }

    public String getDireccion() {
        return direccion;
    }

    public String toString() {
        return String.format(
                "%s (cantidad=%d, calibre=%s, union_inicial=%s, union_final=%s, direccion=%s)",
                this.getClass().getSimpleName(),
                this.cantidad,
                this.calibre,
                this.union_inicial,
                this.union_final,
                this.direccion
                );
    }
}
