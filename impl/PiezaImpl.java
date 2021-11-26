package com.ae.proyecto.impl;
import java.util.List;

import com.ae.proyecto.Union;
import com.ae.proyecto.impl.UnionImpl.UnionT;
import com.ae.proyecto.Figura;
import com.ae.proyecto.Pieza;
import com.ae.proyecto.Size;


public abstract class PiezaImpl implements Pieza {
	private Integer Id;
	public enum Forma {RECTANGULAR,CIRCULAR} 
	private Forma forma;
	private Size size;
	private Integer largo;
	//public enum UnionT {PM,CC,F,C,V,T}
	private Union initialUnion;
	private Union finalUnion;
	private Integer cantidad;
	public enum Calibre {VEINTIDOS,VEINTICUATRO,VEINTISEIS}
	private Calibre calibre;	
		
	private List<Figura> pieza;
			
	
	public List<Figura> getPieza(){
		return pieza;
	}
	
	public void setPieza(List<Figura> pieza) {
		this.pieza = pieza;
	}
	
	public void addFigura(Figura figura) {
		this.pieza.add(figura);		
	}

	public Forma getForma() {
		return forma;
	}

	public void setForma(Forma forma) {
		this.forma = forma;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Integer getLargo() {
		return largo;
	}

	public void setLargo(Integer largo) {
		this.largo = largo;
	}

	public Union getInitialUnion() {
		return initialUnion;
	}

	public void setInitialUnion(UnionT initialUnion) {
		Union union = new UnionImpl(initialUnion);
		this.initialUnion = union;
	}

	public Union getFinalUnion() {
		return finalUnion;
	}

	public void setFinalUnion(UnionT finalUnion) {
		Union union = new UnionImpl(finalUnion);
		this.finalUnion = union;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Calibre getCalibre() {
		return calibre;
	}

	public void setCalibre(Calibre calibre) {
		this.calibre = calibre;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String toString() {
		return String.format("Pieza(forma=%s, size=%s, largo=%s, union_ini=%s, union_fin=%s, cant=%s, calibre=%s)", this.forma.toString(), this.size.toString(),
				this.largo, this.initialUnion.toString(), this.finalUnion.toString(), this.cantidad, this.calibre.toString());
	}
}
