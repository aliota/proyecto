package com.ae.proyecto;

import java.util.List;

public interface Figura {
	
	public List<List<Integer>> getFigura();
	public void setFigura(List<List<Integer>> figura);
	public Figura invertirFigura();
	public Figura girarFigura();
	public Integer getSizeY();
	public Integer getSizeX();
	public double getArea();
	public List<Integer> getFila(Integer index);
}
