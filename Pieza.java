package com.ae.proyecto;

import java.util.List;

public interface Pieza {
	
	public void crearFiguras();
	public List<Figura> getPieza();
	public void setPieza(List<Figura> pieza);
	public void addFigura(Figura figura);
		
}
