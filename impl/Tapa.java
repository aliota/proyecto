package com.ae.proyecto.impl;

import com.ae.proyecto.impl.UnionImpl.UnionT;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.util.JMetalLogger;

import com.ae.proyecto.Figura;
import com.ae.proyecto.Size;

public class Tapa extends PiezaImpl {
	private final static int LARGO_EXTRA_TAPA = 4;
	
	public Tapa(Size size,Integer cantidad, Calibre calibre) {
		this.setId(0);
		this.setForma(Forma.RECTANGULAR);
		this.setSize(size);
		this.setLargo(size.getHigh());
		this.setInitialUnion(UnionT.F);
		this.setFinalUnion(UnionT.F);
		this.setCantidad(cantidad);
		this.setCalibre(calibre);
		this.crearFiguras();
	}
	
	public Tapa(Integer id, Forma forma, Size size, Integer cantidad, Calibre calibre) {
		this.setId(id);
		this.setForma(forma);
		this.setSize(size);
		this.setLargo(size.getHigh());
		this.setInitialUnion(UnionT.F);
		this.setFinalUnion(UnionT.F);
		this.setCantidad(cantidad);
		this.setCalibre(calibre);	
		this.crearFiguras();
	}
	
	
	public void crearFiguras() {
		try (PrintWriter out = new PrintWriter("tapa.out")) {    
        	
	    out.println("Tapa: ");
	    List<Figura> tapa = new ArrayList<>();
		this.setPieza(tapa);
		List<List<Integer>> matriz = new ArrayList<>();
		Integer largoX;
		Integer largoY;
		
		//la tapa está formada por 1 figura
		
		
		//creo tapa
		largoX = this.getSize().getWidth()+LARGO_EXTRA_TAPA;
		JMetalLogger.logger.info("Largo X: "+largoX);
		largoY = this.getLargo()+LARGO_EXTRA_TAPA;
		JMetalLogger.logger.info("Largo Y: "+largoY);
		
		for (int y=0; y<largoY; y++) {
			List<Integer> fila = new ArrayList<>();
			for (int x=0; x<largoX;x++ ) {
				fila.add(1);
			}
			matriz.add(fila);
		}		
		
		Figura miTapa = new FiguraImpl(matriz);
		this.addFigura(miTapa);//agrego tapa
		
		//imprimo tapa
		out.println("Figura de la tapa");
		for(int i=0;i<this.getPieza().size();i++)	{
			out.println("Figura "+i+" : ");
			for (int y=0; y<this.getPieza().get(i).getSizeY(); y++) {
				for (int x=0; x<getPieza().get(i).getFila(y).size();x++ ) {
					out.print(getPieza().get(i).getFila(y).get(x)+" ");
				}
				out.println();
			}
		}
		
}
catch (FileNotFoundException e) {
    System.err.println("Error al crear tapas");
 }
	}

	public String toString() {
		return String.format("Tapa(%s)", super.toString());
	}
		
}
