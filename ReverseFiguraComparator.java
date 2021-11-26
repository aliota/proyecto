package com.ae.proyecto;

import java.util.Comparator;

public class ReverseFiguraComparator implements Comparator<Figura> {

    @Override
    public int compare(Figura first, Figura second) {
        return -1 * Double.compare(first.getArea(), second.getArea());
    }

}
