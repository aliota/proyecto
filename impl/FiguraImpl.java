package com.ae.proyecto.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import com.ae.proyecto.Figura;


public class FiguraImpl implements Figura {
	private Integer id;
	private List<List<Integer>> figura;
	
	public FiguraImpl() {
		figura = Collections.synchronizedList(new ArrayList<>());
	}
	
	public FiguraImpl(List<List<Integer>> matriz) {
		figura = matriz;
	}
	
	public FiguraImpl(List<List<Integer>> matriz, Integer id) {
		figura = matriz;
		this.id = id;
	}
		
	public List<List<Integer>> getFigura(){
		return figura;
		
	}
	public void setFigura(List<List<Integer>> figura) {
		this.figura = figura;
	}

	/* Invertir figura horizontal */
	@Override
	public Figura invertirFigura() {
		// TODO Auto-generated method stub
		List<List<Integer>> invertida = Collections.synchronizedList(new ArrayList<>());
		for (List<Integer> list: this.figura) {
			List<Integer> copy = (ArrayList<Integer>) ((ArrayList<Integer>) list).clone();
			Collections.reverse(copy);
			invertida.add(copy);
		}
		return new FiguraImpl(invertida);
	}

	@Override
	/* Rota la figura 90 grados - sentido horario */
	public synchronized Figura girarFigura() {
		List<List<Integer>> rotada = Collections.synchronizedList(new ArrayList<>());
		int M = this.figura.size();
		int N = this.figura.get(0).size();
		try {
			for (int k = 0; k < N; k++) {
				List<Integer> a = new ArrayList<>();
				for (int i = M - 1; i >= 0; i--) {
					a.add(this.figura.get(i).get(k));
				}
				rotada.add(a);
			}
			return new FiguraImpl(rotada);

		} catch (IndexOutOfBoundsException ex) {
			System.out.println("Figura que falla");
			System.out.println(String.format(" size: %s x %s", this.figura.size(), this.figura.get(0).size()));
			throw ex;
		}
		
	}

	public Integer getId() {
		return id;
	}

	public void setId_Parte(Integer id) {
		this.id = id;
	}

	public Integer getSizeY() {
		return figura.size();
	}

	public Integer getSizeX() {
		return this.getFila(0).size();
	}

	@Override
	public List<Integer> getFila(Integer index) {
		return this.getFigura().get(index);
	}

	public double getArea() {
		int suma = 0;
		for (List<Integer> lista: this.figura) {
			for (Integer i: lista) {
				suma += i;
			}
		}
		return suma;
	}
		
}
