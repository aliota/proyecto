package com.ae.proyecto.impl;

import com.ae.proyecto.impl.UnionImpl.UnionT;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.util.JMetalLogger;

import com.ae.proyecto.Figura;
import com.ae.proyecto.Size;
import com.ae.proyecto.Union;

public class Pantalon extends PiezaImpl {
	
	private Size sizeRight;
	private Integer radioRight;
	
	private Size sizeLeft;
	private Integer radioLeft;
	private Union unionLeft;
		
	
	public Pantalon(Size initialSize,Size sizeRight,Integer radioRight,Size sizeLeft,Integer radioLeft, UnionT initialUnion, UnionT unionRight,UnionT unionLeft,Integer cantidad, Calibre calibre) {
		this.setId(0);
		this.setForma(Forma.RECTANGULAR);
		this.setSize(initialSize);
		this.setSizeRight(sizeRight);
		this.setSizeLeft(sizeLeft);
		this.setLargo(1);
		this.setRadioRight(radioRight);
		this.setRadioLeft(radioLeft);
		this.setInitialUnion(initialUnion);
		this.setFinalUnion(unionRight);
		this.setUnionLeft(unionLeft);
		this.setCantidad(cantidad);
		this.setCalibre(calibre);
		this.crearFiguras();
	}
	
	public Pantalon(Integer id,Size initialSize,Size sizeRight,Integer radioRight,Size sizeLeft,Integer radioLeft, UnionT initialUnion, UnionT unionRight,UnionT unionLeft,Integer cantidad, Calibre calibre) {
		this.setId(id);
		this.setForma(Forma.RECTANGULAR);
		this.setSize(initialSize);
		this.setSizeRight(sizeRight);
		this.setSizeLeft(sizeLeft);
		this.setLargo(1);
		this.setRadioRight(radioRight);
		this.setRadioLeft(radioLeft);
		this.setInitialUnion(initialUnion);
		this.setFinalUnion(unionRight);
		this.setUnionLeft(unionLeft);
		this.setCantidad(cantidad);
		this.setCalibre(calibre);
		this.crearFiguras();
	}
	
	
	public void crearFiguras() {
		try (PrintWriter out = new PrintWriter("pantalon.out")) {    
        	
		    out.println("Pantalón: ");
		    
		    List<Figura> pantalon = new ArrayList<>();
			this.setPieza(pantalon);
			List<List<Integer>> matrizSuperior = new ArrayList<>();
			Integer largoX;
			Integer largoY;
			
			//if(this.getForma()==Forma.RECTANGULAR) 
			
			//el pantalón está formado por 6 figuras: superior, inferior, lateral interior derecha, lateral exterior derecha, lateral exterior izda y lateral interior izda		
			//creo figuras superior e inferior
			largoX = this.getSizeRight().getWidth()+this.getRadioRight()+ this.getInitialUnion().getLargoAleta()+this.getInitialUnion().getLargoAletaO();
			JMetalLogger.logger.info("Largo X: "+largoX);
			largoY = this.getUnionLeft().getLargoAleta()+this.getRadioLeft()+this.getSize().getWidth()+this.getRadioRight()+this.getFinalUnion().getLargoAleta();
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
			this.addFigura(figuraSuperior);//lado superior del pantalon
			this.addFigura(figuraSuperior);//lado inferior del pantalon igual al superior
			
			//creo figuras laterales interiores y exteriores
			List<List<Integer>> matrizLateralInteriorDerecha = new ArrayList<>();
			Integer radioInteriorDerecho=this.getRadioRight();
			Integer radioExteriorDerecho=this.getRadioRight()+this.getSizeRight().getWidth();
			Integer radioInteriorIzquierdo=this.getRadioLeft();
			Integer radioExteriorIzquierdo=this.getRadioLeft()+this.getSizeLeft().getWidth();
					
			
			//lateral interior derecho
			largoX = (int)(radioInteriorDerecho*Math.PI/2) + this.getInitialUnion().getLargoAleta()+this.getFinalUnion().getLargoAleta();
			largoY = this.getSize().getHigh()+ this.getInitialUnion().getLargoAletaH()*2;
			for (int y=0; y<largoY; y++) {
				List<Integer> fila = new ArrayList<>();
				for (int x=0; x<largoX;x++ ) {
					fila.add(1);
				}
				matrizLateralInteriorDerecha.add(fila);
			}		
			
			Figura figuraLateralInteriorDerecha = new FiguraImpl(matrizLateralInteriorDerecha);
			this.addFigura(figuraLateralInteriorDerecha);//lateral interior derecha
							
			//lateral interior izquierdo
			List<List<Integer>> matrizLateralInteriorIzquierda = new ArrayList<>();
			largoX = (int)(radioInteriorIzquierdo*Math.PI/2) + this.getInitialUnion().getLargoAleta()+this.getUnionLeft().getLargoAleta();
			largoY = this.getSize().getHigh()+ this.getInitialUnion().getLargoAletaH()*2;
			for (int y=0; y<largoY; y++) {
				List<Integer> fila = new ArrayList<>();
				for (int x=0; x<largoX;x++ ) {
					fila.add(1);
				}
				matrizLateralInteriorIzquierda.add(fila);
			}		
			
			Figura figuraLateralInteriorIzquierda = new FiguraImpl(matrizLateralInteriorIzquierda);
			this.addFigura(figuraLateralInteriorIzquierda);//lateral interior izquierda
						
			
			//lateral exterior derecha
			List<List<Integer>> matrizLateralExteriorDerecha = new ArrayList<>();
			largoX = (int)(radioExteriorDerecho*Math.PI/2) +this.getFinalUnion().getLargoAleta(); //corregir largo
			largoY = this.getSize().getHigh()+ this.getInitialUnion().getLargoAletaH()*2;
			for (int y=0; y<largoY; y++) {
				List<Integer> fila = new ArrayList<>();
				for (int x=0; x<largoX;x++ ) {
					fila.add(1);
				}
				matrizLateralExteriorDerecha.add(fila);
			}		
			
			Figura figuraLateralExteriorDerecha = new FiguraImpl(matrizLateralExteriorDerecha);
			this.addFigura(figuraLateralExteriorDerecha);//lateral exterior derecha
			
			//lateral exterior izquierda
			List<List<Integer>> matrizLateralExteriorIzquierda = new ArrayList<>();
			largoX = (int)(radioExteriorIzquierdo*Math.PI/2) +this.getUnionLeft().getLargoAleta(); //corregir largo
			largoY = this.getSize().getHigh()+ this.getInitialUnion().getLargoAletaH()*2;
			for (int y=0; y<largoY; y++) {
				List<Integer> fila = new ArrayList<>();
				for (int x=0; x<largoX;x++ ) {
					fila.add(1);
				}
				matrizLateralExteriorIzquierda.add(fila);
			}		
			
			Figura figuraLateralExteriorIzquierda = new FiguraImpl(matrizLateralExteriorIzquierda);
			this.addFigura(figuraLateralExteriorIzquierda);//lateral exterior izquierda

			//imprimo figuras del pantalon
			out.println("Figuras del pantalón");
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

	public Integer getRadioRight() {
		return radioRight;
	}

	public void setRadioRight(Integer radioRight) {
		this.radioRight = radioRight;
	}
	
	public Integer getRadioLeft() {
		return radioLeft;
	}

	public void setRadioLeft(Integer radioLeft) {
		this.radioLeft = radioLeft;
	}

	public Size getSizeRight() {
		return sizeRight;
	}

	public void setSizeRight(Size sizeRight) {
		this.sizeRight = sizeRight;
	}

	public Size getSizeLeft() {
		return sizeLeft;
	}

	public void setSizeLeft(Size sizeLeft) {
		this.sizeLeft = sizeLeft;
	}

	public Union getUnionLeft() {
		return unionLeft;
	}

	public void setUnionLeft(UnionT unionLeft) {
		Union union = new UnionImpl(unionLeft);
		this.unionLeft = union;
	}

	public String toString() {
		return String.format("Pantalon(%s)", super.toString());
	}


	
}
