package com.ae.proyecto.impl;

import com.ae.proyecto.impl.UnionImpl.UnionT;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.util.JMetalLogger;

import com.ae.proyecto.Figura;
import com.ae.proyecto.Size;

public class Marco extends PiezaImpl {
	private final static int LARGO_EXTRA_MARCO = 5;
	private final static int ANCHO_MARCO = 10;
	
	public Marco(Size size,Integer cantidad, Calibre calibre) {
		this.setId(0);
		this.setForma(Forma.RECTANGULAR);
		this.setSize(size);
		this.setLargo(ANCHO_MARCO);
		this.setInitialUnion(UnionT.F);
		this.setFinalUnion(UnionT.F);
		this.setCantidad(cantidad);
		this.setCalibre(calibre);
		this.crearFiguras();
	}
	
	public Marco(Integer id, Forma forma, Size size, Integer cantidad, Calibre calibre) {
		this.setId(id);
		this.setForma(forma);
		this.setSize(size);
		this.setLargo(ANCHO_MARCO);
		this.setInitialUnion(UnionT.F);
		this.setFinalUnion(UnionT.F);
		this.setCantidad(cantidad);
		this.setCalibre(calibre);	
		this.crearFiguras();
	}
	
	
	public void crearFiguras() {
		try (PrintWriter out = new PrintWriter("marcos.out")) {    
        	
		    out.println("Marcos: ");
		    List<Figura> marco = new ArrayList<>();
			this.setPieza(marco);
			List<List<Integer>> matrizAncho = new ArrayList<>();
			Integer largoX;
			Integer largoY;
			
			//el marco está formado por 4 figuras: 2 marcoWidth y 2 marcoHigh
			largoY = ANCHO_MARCO;
			JMetalLogger.logger.info("Largo Y: "+largoY);
			
			//creo marcoWidth
			largoX = this.getSize().getWidth()+LARGO_EXTRA_MARCO;
			JMetalLogger.logger.info("Largo X: "+largoX);
			
			
			for (int y=0; y<largoY; y++) {
				List<Integer> fila = new ArrayList<>();
				for (int x=0; x<largoX;x++ ) {
					fila.add(1);
				}
				matrizAncho.add(fila);
			}		
			
			Figura marcoWidth = new FiguraImpl(matrizAncho);
			this.addFigura(marcoWidth);//agrego primer marcoWidth
			this.addFigura(marcoWidth);//agrego segundo marcoWidth
			
			//creo marcoHigh
			List<List<Integer>> matrizAlto = new ArrayList<>();
			largoX = this.getSize().getHigh()+LARGO_EXTRA_MARCO;
			for (int y=0; y<largoY; y++) {
				List<Integer> fila = new ArrayList<>();
				for (int x=0; x<largoX;x++ ) {
					fila.add(1);
				}
				matrizAlto.add(fila);
			}		
			
			Figura marcoHigh = new FiguraImpl(matrizAlto);
			this.addFigura(marcoHigh);//agrego primer marcoHigh
			this.addFigura(marcoHigh);//agrego segundo marcoHigh
			
			//imprimo los marcos
			out.println("Marcos");
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
			System.err.println("Error al crear marcos");
		}
	}

	public String toString() {
		return String.format("Marco(%s)", super.toString());
	}
}
