package com.ae.proyecto.impl;

import com.ae.proyecto.impl.UnionImpl.UnionT;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.util.JMetalLogger;

import com.ae.proyecto.Figura;
import com.ae.proyecto.Size;

public class Garganta extends PiezaImpl {
	private Integer radio;
		
	public Garganta(Forma forma, Size size,Integer radio, UnionT initialUnion, UnionT finalUnion,Integer cantidad, Calibre calibre) {
		this.setId(0);
		this.setForma(forma);
		this.setSize(size);
		this.setLargo(radio);
		this.setRadio(radio);
		this.setInitialUnion(initialUnion);
		this.setFinalUnion(finalUnion);
		this.setCantidad(cantidad);
		this.setCalibre(calibre);
		this.crearFiguras();
	}
	
	public Garganta(Integer id, Forma forma, Size size, Integer radio, UnionT initialUnion, UnionT finalUnion,Integer cantidad, Calibre calibre) {
		this.setId(id);
		this.setForma(forma);
		this.setSize(size);
		this.setLargo(radio);
		this.setRadio(radio);
		this.setInitialUnion(initialUnion);
		this.setFinalUnion(finalUnion);
		this.setCantidad(cantidad);
		this.setCalibre(calibre);	
		this.crearFiguras();
	}
	
	
	public void crearFiguras() {
		try (PrintWriter out = new PrintWriter("garganta.out")) {    
        	
		    out.println("Garganta: ");
		    List<Figura> garganta = new ArrayList<>();
			this.setPieza(garganta);
			List<List<Integer>> matrizSuperior = new ArrayList<>();
			Integer largoX;
			Integer largoY;
			
			//la garganta rectangular está formada por 2 figuras: (superior, inferior, lateral recto) y lateral curvo
			
			//creo figura superior, inferior, lateral recto
			largoX = (this.getSize().getWidth()+this.getRadio())*2+ this.getSize().getHigh();
			JMetalLogger.logger.info("Largo X: "+largoX);
			largoY = this.getRadio()+ this.getInitialUnion().getLargoAletaO()+this.getFinalUnion().getLargoAleta();
			JMetalLogger.logger.info("Largo Y: "+largoY);
			for (int y=0; y<largoY; y++) {
				List<Integer> fila = new ArrayList<>();
				for (int x=0; x<largoX;x++ ) {
					/*int distancia = (int) Math.sqrt((largoX-1-x)^2+(largoY-1-y)^2);
					double angulo = Math.asin(y/x) ;
					if(distancia<radio||distancia>largoX // según distancia 
						fila.add(0);
					else
						fila.add(1);*/
					fila.add(1);
				}
				matrizSuperior.add(fila);
			}		
			
			Figura figuraA = new FiguraImpl(matrizSuperior);
			this.addFigura(figuraA);//lado superior lateral recto e inferior de la garganta
			
			//creo figuras laterales curvo
			List<List<Integer>> matrizLateralCurva = new ArrayList<>();
			
			//lateral curvo
			largoX = (int)(this.getRadio()*Math.PI/2) + this.getInitialUnion().getLargoAleta()+this.getFinalUnion().getLargoAleta();
			largoY = this.getSize().getHigh()+ this.getInitialUnion().getLargoAletaH()*2;
			for (int y=0; y<largoY; y++) {
				List<Integer> fila = new ArrayList<>();
				for (int x=0; x<largoX;x++ ) {
					fila.add(1);
				}
				matrizLateralCurva.add(fila);
			}		
			
			Figura figuraLateralCurva = new FiguraImpl(matrizLateralCurva);
			this.addFigura(figuraLateralCurva);//lateral curvo de la garganta
			
			//imprimo figuras de la garganta
			out.println("Figuras de la garganta");
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
			System.err.println("Error al crear garganta");
		}
	}

	public Integer getRadio() {
		return radio;
	}

	public void setRadio(Integer radio) {
		this.radio = radio;
	}

	public String toString() {
		return String.format("Garganta(%s)", super.toString());
	}
	
}
