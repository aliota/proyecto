package com.ae.proyecto.impl;

import com.ae.proyecto.impl.UnionImpl.UnionT;

import java.util.ArrayList;
import java.util.List;

import com.ae.proyecto.Figura;
import com.ae.proyecto.Size;

public class Cuello extends PiezaImpl {
	
	public Cuello(Forma forma, Size size, Integer largo, UnionT initialUnion, UnionT finalUnion,Integer cantidad, Calibre calibre) {
		this.setId(0);
		this.setForma(forma);
		this.setSize(size);
		this.setLargo(largo);
		this.setInitialUnion(initialUnion);
		this.setFinalUnion(finalUnion);
		this.setCantidad(cantidad);
		this.setCalibre(calibre);
		this.crearFiguras();
	}
	
	public Cuello(Integer id, Forma forma, Size size, Integer largo, UnionT initialUnion, UnionT finalUnion,Integer cantidad, Calibre calibre) {
		this.setId(id);
		this.setForma(forma);
		this.setSize(size);
		this.setLargo(largo);
		this.setInitialUnion(initialUnion);
		this.setFinalUnion(finalUnion);
		this.setCantidad(cantidad);
		this.setCalibre(calibre);	
		this.crearFiguras();
	}
	
	
	public void crearFiguras() {
		
		List<List<Integer>> matriz = new ArrayList<>();
		Integer largoX;
		Integer largoY;
		if(this.getForma()==Forma.RECTANGULAR) {
			largoX = (this.getSize().getWidth()+this.getSize().getHigh())*2+ this.getInitialUnion().getLargoAletaH()+this.getInitialUnion().getLargoAletaO();
		}else	{
			largoX = (int)(this.getSize().getDiameter()*Math.PI)+ this.getInitialUnion().getLargoAletaH()+this.getInitialUnion().getLargoAletaO();
		}
		largoY = this.getLargo()+this.getInitialUnion().getLargoAleta()+this.getFinalUnion().getLargoAleta();
		for (int y=0; y<largoY; y++) {
			List<Integer> fila = new ArrayList<>();
			for (int x=0; x<largoX;x++ ) {
				fila.add(1);
			}
			matriz.add(fila);
		}		
		Figura figura = new FiguraImpl(matriz);
		List<Figura> cuello = new ArrayList<>();	
		this.setPieza(cuello);
		this.addFigura(figura);
	}

	public String toString() {
		return String.format("Cuello(%s)", super.toString());
	}
	
}
