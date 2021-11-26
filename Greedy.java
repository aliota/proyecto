package com.ae.proyecto;

import java.util.List;


public class Greedy {

    public static Solucion algoritmoGreedy(List<Figura> cortes) {
        cortes.sort(new ReverseFiguraComparator());

        for (Figura fig: cortes) {
            // Obtener una chapa 'c'
            // Chequear que hay espacio en 'c'
            // Si hay => posicionar 'fig'
            // Sino => marcar 'c' completa, y obtener otra chapa "c_next"
        }

        // contar las chapas usadas x calibre.
        int chapa22 = 10;
        int chapa24 = 5;
        int chapa26 = 0;

        return new Solucion(chapa22, chapa24, chapa26);
    }
}
