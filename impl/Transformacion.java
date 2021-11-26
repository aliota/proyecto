package com.ae.proyecto.impl;

import com.ae.proyecto.impl.UnionImpl.UnionT;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.ae.proyecto.Figura;
import com.ae.proyecto.Size;

public class Transformacion extends PiezaImpl {
	public enum AlineacionW {RI,RD,C,NT}
	public enum AlineacionH {RAR,RAB,C,NT}
	private Forma formaFinal;
	private Size finalSize;
	private AlineacionW lineaW;
	private AlineacionH lineaH;
	
	public Transformacion(Forma formaInicial, Forma formaFinal, Size initialSize, Size finalSize,Integer largo, UnionT initialUnion, UnionT finalUnion, AlineacionW lineaW, AlineacionH lineaH, Integer cantidad, Calibre calibre) {
		this.setId(0);
		this.setForma(formaInicial);
		this.setFormaFinal(formaFinal);
		this.setSize(initialSize);
		this.setFinalSize(finalSize);
		this.setLargo(largo);
		this.setInitialUnion(initialUnion);
		this.setFinalUnion(finalUnion);
		this.setLineaW(lineaW);
		this.setLineaH(lineaH);
		this.setCantidad(cantidad);
		this.setCalibre(calibre);
		this.crearFiguras();
	}
	
	public Transformacion(Integer id,Forma formaInicial,Forma formaFinal, Size initialSize,Size finalSize,Integer largo, UnionT initialUnion, UnionT finalUnion, AlineacionW lineaW, AlineacionH lineaH, Integer cantidad, Calibre calibre) {
		this.setId(id);
		this.setForma(formaInicial);
		this.setFormaFinal(formaFinal);
		this.setSize(initialSize);
		this.setFinalSize(finalSize);
		this.setLargo(largo);
		this.setInitialUnion(initialUnion);
		this.setFinalUnion(finalUnion);
		this.setLineaW(lineaW);
		this.setLineaH(lineaH);
		this.setCantidad(cantidad);
		this.setCalibre(calibre);
		this.crearFiguras();
	}
	
	
	public void crearFiguras() {
		try (PrintWriter out = new PrintWriter("transformacion.out")) {    
        	
		    out.println("Transformación: ");
		    
		    List<Figura> transform = new ArrayList<>();
			this.setPieza(transform);
			Integer largoX;
			Integer largoY;
			
			if(this.getForma()==Forma.RECTANGULAR) { 
			
				//la transformación la vamos a formar con 4 figuras: superior, inferior, lateral derecho y lateral izquierdo		
				
				switch(lineaW) {
				case RI,RD,NT:
					switch(lineaH) {
					case RAR,RAB,NT:
						
						//creo figuras superior, derecha, inferior e izquierda
						Integer initialWidth=this.getSize().getWidth();
						Integer finalWidth = this.getFinalSize().getWidth();
						Integer maxWidth = Math.max(initialWidth, finalWidth);
						Integer diferenciaAncho = Math.abs(initialWidth - finalWidth);
						
						Integer initialHigh=this.getSize().getHigh();
						Integer finalHigh = this.getFinalSize().getHigh();
						Integer maxHigh = Math.max(initialHigh, finalHigh);
						Integer diferenciaAlto = Math.abs(initialHigh - finalHigh);
						
						Integer largoSuperior = this.getLargo();
						Integer diagonalSuperior = (int)Math.sqrt(Math.pow((double)largoSuperior, 2.0)+Math.pow((double)diferenciaAncho,2.0));
						
						Integer largoDerecha = this.getLargo();
						Integer diagonalDerecha = (int)Math.sqrt(Math.pow((double)largoDerecha, 2.0)+Math.pow((double)diferenciaAlto,2.0));
						
						Integer largoInferior = diagonalDerecha;
						Integer diagonalInferior = (int)Math.sqrt(Math.pow((double)largoInferior, 2.0)+Math.pow((double)diferenciaAncho,2.0));
						
						Integer largoIzquierda = diagonalSuperior;
						Integer diagonalIzquierda = (int)Math.sqrt(Math.pow((double)largoIzquierda, 2.0)+Math.pow((double)diferenciaAlto,2.0));

						//Figura superior
						List<List<Integer>> matrizSuperior = new ArrayList<>();
						double alfaSuperior =Math.asin(largoSuperior/diagonalSuperior);
						double betaSuperior =Math.PI/2-alfaSuperior; //90°-alfa
						
						largoX = maxWidth+ this.getInitialUnion().getLargoAletaO()*(1+(int)Math.cos(betaSuperior));
						largoY = largoSuperior+this.getInitialUnion().getLargoAleta()+this.getFinalUnion().getLargoAleta();
						
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
						this.addFigura(figuraSuperior);//lado superior 						
						
						//figura inferior 
						List<List<Integer>> matrizInferior = new ArrayList<>();
						double alfaInferior =Math.asin(largoInferior/diagonalInferior);
						double betaInferior =Math.PI/2-alfaInferior; //90°-alfa
						
						largoX = maxWidth+ this.getInitialUnion().getLargoAletaO()*(1+(int)Math.cos(betaInferior));
						largoY = largoInferior+this.getInitialUnion().getLargoAleta()+this.getFinalUnion().getLargoAleta();
						
						for (int y=0; y<largoY; y++) {
							List<Integer> fila = new ArrayList<>();
							for (int x=0; x<largoX;x++ ) {
								
								fila.add(1);
							}
							matrizInferior.add(fila);
						}		
						
						Figura figuraInferior = new FiguraImpl(matrizInferior);
						this.addFigura(figuraInferior);//lado inferior 						
					
						//figura derecha 
						List<List<Integer>> matrizDerecha = new ArrayList<>();
						double alfaDerecha =Math.asin(largoDerecha/diagonalDerecha);
						double betaDerecha =Math.PI/2-alfaDerecha; //90°-alfa
						
						largoX = maxHigh+ this.getInitialUnion().getLargoAletaH()*(1+(int)Math.cos(betaDerecha));
						largoY = largoDerecha+this.getInitialUnion().getLargoAleta()+this.getFinalUnion().getLargoAleta();
						
						for (int y=0; y<largoY; y++) {
							List<Integer> fila = new ArrayList<>();
							for (int x=0; x<largoX;x++ ) {
								
								fila.add(1);
							}
							matrizDerecha.add(fila);
						}		
						
						Figura figuraDerecha = new FiguraImpl(matrizDerecha);
						this.addFigura(figuraDerecha);//lado derecha 						
					
						//figura izquierda 
						List<List<Integer>> matrizIzquierda = new ArrayList<>();
						double alfaIzquierda =Math.asin(largoIzquierda/diagonalIzquierda);
						double betaIzquierda =Math.PI/2-alfaIzquierda; //90°-alfa
						
						largoX = maxHigh+ this.getInitialUnion().getLargoAletaH()*(1+(int)Math.cos(betaIzquierda));
						largoY = largoIzquierda+this.getInitialUnion().getLargoAleta()+this.getFinalUnion().getLargoAleta();
						
						for (int y=0; y<largoY; y++) {
							List<Integer> fila = new ArrayList<>();
							for (int x=0; x<largoX;x++ ) {
								
								fila.add(1);
							}
							matrizIzquierda.add(fila);
						}		
						
						Figura figuraIzquierda = new FiguraImpl(matrizIzquierda);
						this.addFigura(figuraIzquierda);//lado izquierda 						
					
						break;
					default:
						break;
					}				
					
				default:
					break;
				}
				
				//imprimo figuras 
				out.println("Figuras de la transformación");
				for(int i=0;i<this.getPieza().size();i++)	{
					out.println("Figura "+i+" : ");
					for (int y=0; y<this.getPieza().get(i).getSizeY(); y++) {
						for (int x=0; x<getPieza().get(i).getFila(y).size();x++ ) {
							out.print(getPieza().get(i).getFila(y).get(x)+" ");
						}
						out.println();
					}
				}
				
			}else {
				//implementar
			}
		}
		catch (FileNotFoundException e) {
		    System.err.println("Error al crear transformación");
		 }
	}

	
	public Forma getFormaFinal() {
		return formaFinal;
	}

	public void setFormaFinal(Forma formaFinal) {
		this.formaFinal = formaFinal;
	}

	public Size getFinalSize() {
		return finalSize;
	}

	public void setFinalSize(Size finalSize) {
		this.finalSize = finalSize;
	}

	public AlineacionW getLineaW() {
		return lineaW;
	}

	public void setLineaW(AlineacionW lineaW) {
		this.lineaW = lineaW;
	}

	public AlineacionH getLineaH() {
		return lineaH;
	}

	public void setLineaH(AlineacionH lineaH) {
		this.lineaH = lineaH;
	}


	public String toString() {
		String transf = String.format("forma_final=%s, final size=%s, LiW=%s, LiH=%s",
				this.getFormaFinal(), this.getFinalSize(),
				this.getLineaW(), this.getLineaH()
				);
		return String.format("Transformacion(%s, \n  %s)", super.toString(), transf);
	}
	
}
