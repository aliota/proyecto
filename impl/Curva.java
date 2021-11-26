package com.ae.proyecto.impl;

import com.ae.proyecto.impl.UnionImpl.UnionT;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.util.JMetalLogger;

import com.ae.proyecto.Figura;
import com.ae.proyecto.Size;

public class Curva extends PiezaImpl {
	private Integer radio;
	private Double angle;
	
	public Curva(Forma forma, Size size,Integer radio, Double angle, UnionT initialUnion, UnionT finalUnion,Integer cantidad, Calibre calibre) {
		this.setId(0);
		this.setForma(forma);
		this.setSize(size);
		this.setLargo(1);
		this.setRadio(radio);
		this.setAngle(angle);
		this.setInitialUnion(initialUnion);
		this.setFinalUnion(finalUnion);
		this.setCantidad(cantidad);
		this.setCalibre(calibre);
		this.crearFiguras();
	}
	
	public Curva(Integer id, Forma forma, Size size, Integer radio, Double angle, UnionT initialUnion, UnionT finalUnion,Integer cantidad, Calibre calibre) {
		this.setId(id);
		this.setForma(forma);
		this.setSize(size);
		this.setLargo(1);
		this.setRadio(radio);
		this.setAngle(angle);
		this.setInitialUnion(initialUnion);
		this.setFinalUnion(finalUnion);
		this.setCantidad(cantidad);
		this.setCalibre(calibre);	
		this.crearFiguras();
	}
	
	
	public void crearFiguras() {
		try (PrintWriter out = new PrintWriter("curva.out")) {    
        	
		    out.println("Curva: ");
		    
		    List<Figura> curva = new ArrayList<>();
			this.setPieza(curva);
			List<List<Integer>> matrizSuperior = new ArrayList<>();
			Integer largoX;
			Integer largoY;
			
			//if(this.getForma()==Forma.RECTANGULAR) 
			
			//la curva rectangular está formada por 4 figuras: superior, inferior, lateral interior y lateral exterior
			
			//creo figuras superior e inferior
			largoX = this.getSize().getWidth()+this.getRadio()+ this.getInitialUnion().getLargoAletaO()+this.getFinalUnion().getLargoAleta();
			JMetalLogger.logger.info("Largo X: "+largoX);
			largoY = this.getSize().getWidth()+this.getRadio()+ this.getInitialUnion().getLargoAletaO()+this.getInitialUnion().getLargoAleta();
			JMetalLogger.logger.info("Largo Y: "+largoY);
			for (int y=0; y<largoY; y++) {
				List<Integer> fila = new ArrayList<>();
				for (int x=0; x<largoX;x++ ) {
					/*int distancia = (int) Math.sqrt((largoX-1-x)^2+(largoY-1-y)^2);
					double angulo = Math.asin(y/x) ;
					if(distancia<radio||distancia>largoX || angulo>angle) // según distancia y ángulo
						fila.add(0);
					else
						fila.add(1);*/
					fila.add(1);
				}
				matrizSuperior.add(fila);
			}		
			
			Figura figuraSuperior = new FiguraImpl(matrizSuperior);
			this.addFigura(figuraSuperior);//lado superior de la curva
			this.addFigura(figuraSuperior);//lado inferior de la curva igual al superior
			
			
			//creo figuras laterales interior y exterior
			List<List<Integer>> matrizLateralInterior = new ArrayList<>();
			List<List<Integer>> matrizLateralExterior = new ArrayList<>();
			Integer radioInterior=this.getRadio();
			Integer radioExterior=this.getRadio()+this.getSize().getWidth();
			
			//lateral interior
			largoX = (int)(radioInterior*Math.PI*angle/180) + this.getInitialUnion().getLargoAleta()+this.getFinalUnion().getLargoAleta();
			largoY = this.getSize().getHigh()+ this.getInitialUnion().getLargoAletaH()*2;
			for (int y=0; y<largoY; y++) {
				List<Integer> fila = new ArrayList<>();
				for (int x=0; x<largoX;x++ ) {
					fila.add(1);
				}
				matrizLateralInterior.add(fila);
			}		
			
			Figura figuraLateralInterior = new FiguraImpl(matrizLateralInterior);
			this.addFigura(figuraLateralInterior);//lateral interior de la curva
						
			//lateral exterior
			largoX = (int)(radioExterior*Math.PI*angle/180) + this.getInitialUnion().getLargoAleta()+this.getFinalUnion().getLargoAleta();
			largoY = this.getSize().getHigh()+ this.getInitialUnion().getLargoAletaH()*2;
			for (int y=0; y<largoY; y++) {
				List<Integer> fila = new ArrayList<>();
				for (int x=0; x<largoX;x++ ) {
					fila.add(1);
				}
				matrizLateralExterior.add(fila);
			}		
			
			Figura figuraLateralExterior = new FiguraImpl(matrizLateralExterior);
			this.addFigura(figuraLateralExterior);//lateral exterior de la curva
			
			
			//imprimo figuras de la curva
			out.println("Figuras de la curva");
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
		    System.err.println("Error al crear curvas");
		 }
	}
		
	public Integer getRadio() {
		return radio;
	}

	public void setRadio(Integer radio) {
		this.radio = radio;
	}

	public Double getAngle() {
		return angle;
	}

	public void setAngle(Double angle) {
		this.angle = angle;
	}

	public String toString() {
		return String.format("Curva(%s)", super.toString());
	}
	
}
