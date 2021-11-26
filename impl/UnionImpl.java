package com.ae.proyecto.impl;

import com.ae.proyecto.Union;


public class UnionImpl implements Union{
		
	private final static int LARGO_ALETA_F = 0;
	private final static int LARGO_ALETA_PM = 2;
	private final static int LARGO_ALETA_C = 6;
	private final static int LARGO_ALETA_V = 4;
	private final static int LARGO_ALETA_CC = 1;
	private final static int LARGO_ALETA_T = 1;
	private final static int LARGO_ALETA_H = 3;
	private final static int LARGO_ALETA_O = 1;
	
	public static enum UnionL {H,O}
	public static enum UnionT {
		PM, // Pestana Marco
		CC, // Chaucha y Corredera
		F,  //
		C,  // Clinch
		V,  // Virola
		T   // Tapa
	}
	
	public UnionT union;
	
	public UnionImpl()
	{
		this.union = UnionT.PM;
	}
	
	public UnionImpl(UnionT union)
	{
		this.union = union;
	}
	
	public UnionT getUnion() {
		return union;
	}

	public void setUnion(UnionT union) {
		this.union = union;
	}

	public Integer getLargoAleta(){
		Integer aleta=0;
		switch (union){
			case PM:
				aleta=LARGO_ALETA_PM;
				break;
			case CC:
				aleta=LARGO_ALETA_CC;
				break;
			case F:
				aleta=LARGO_ALETA_F;
				break;
			case C:
				aleta=LARGO_ALETA_C;
				break;
			case V:
				aleta=LARGO_ALETA_V;
				break;
			case T:
				aleta=LARGO_ALETA_T;
				break;				
			default:
				break;
		}
		return aleta;
	} 
	
	public Integer getLargoAletaH() {
		return UnionImpl.LARGO_ALETA_H;
	}
	public Integer getLargoAletaO() {
		return UnionImpl.LARGO_ALETA_O;
	}

	public String toString() {
		return this.union.toString();
	}
}
