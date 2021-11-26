package com.ae.proyecto;

import org.uma.jmetal.problem.integerproblem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.util.JMetalLogger;

import java.util.*;


/**
 * Modified by grupo AE on 28/09/21.
 * Single objective problem for testing integer encoding.
 * Objective: minimizing the qty of sheets
 */
@SuppressWarnings("serial")
public class Trazados extends AbstractIntegerProblem {
 
  private static final int LARGO_REP_FIGURA = 5; // Tamaño de alelo 
  //private static final int MAXINT = 100000;
  
  private Integer largoChapa;  
  private Integer anchoChapa;
  private List<Figura> figuras;
  private Object mutex = new Object();
  private Object mutexArea = new Object();
  private int coberturaTotal;  //area total en cm²
  private int coberturaActual;  //area actual cubierta en cm²
  private int numFigsWithOverlap;
  
  private int cantidadChapasUsadas;
     
    public Trazados() {
    this(null,0,0);
  }

  /** Constructor */
  public Trazados(List<Figura> figuras, Integer largoChapa, Integer anchoChapa)  {
	this.largoChapa = largoChapa; 
	this.anchoChapa = anchoChapa;
	this.figuras = figuras;
	setNumberOfVariables(LARGO_REP_FIGURA*figuras.size()); // cantidad de variables del cromosoma: 
	setNumberOfObjectives(1);
    setName("Trazados");
    setCoberturaTotal();
    coberturaActual=0;
    cantidadChapasUsadas=0;
	this.numFigsWithOverlap = 0;
    this.initVariableBoundries();	  
  }//fin constuctor

  public int getCoberturaTotal() {
	  return this.coberturaTotal;
  }
  
  public int getCoberturaActual() {
	  return this.coberturaActual;
  }
  
  public int getCantidadChapas() {
	  return this.cantidadChapasUsadas;
  }
  
  public int getPorcentajeUsoChapas() {
	  return this.cantidadChapasUsadas;
  }

  private void initVariableBoundries() {
	  List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	  List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

	  for(int i=0;i<figuras.size()*LARGO_REP_FIGURA;i+=LARGO_REP_FIGURA) {
		  int id_Figura = i / LARGO_REP_FIGURA;
		  int largoFigura = figuras.get(id_Figura).getSizeX();
		  int anchoFigura = figuras.get(id_Figura).getSizeY();

		  //X (0..largoChapa-largoFigura)
		  lowerLimit.add(0);
		  upperLimit.add(largoChapa-Math.min(largoFigura, anchoFigura));

		  // Y (0..anchoChapa-anchoFigura)
		  lowerLimit.add(0);
		  upperLimit.add(anchoChapa-Math.min(largoFigura, anchoFigura));

		  // id_giro(0..3) 0=0, 1=90
		  lowerLimit.add(0);
		  upperLimit.add(1);

		  // id_Chapa(0..figuras.size()-1)
		  lowerLimit.add(0);
		  upperLimit.add(figuras.size()-1);

		  // lado(0-1) 0=A, 1=B
		  lowerLimit.add(0);
		  upperLimit.add(1);
	  }
	  setVariableBounds(lowerLimit, upperLimit);
  }

  public void setCoberturaTotal() {
	  int area = 0;
	  
	  // Calculamos la cobertura total en cm²
	  Figura fig;
	  for (int i=0; i < figuras.size(); i++) {
		  fig = figuras.get(i);
		  area += fig.getArea();
	  }
	  
	  this.coberturaTotal = area;
	  JMetalLogger.logger.info("Area total a cubrir: " + this.coberturaTotal+" cm²");
	  JMetalLogger.logger.info("Area de cada chapa: " + this.largoChapa*this.anchoChapa+" cm²");
	  Double chapasMin = ((double)coberturaTotal/(double)(this.largoChapa*this.anchoChapa));
	  JMetalLogger.logger.info("Cantidad minima de chapas: " + Math.ceil(chapasMin));
	  JMetalLogger.logger.info("Cantidad maxima de chapas: " + figuras.size() );
  }


  ///////////////////////////////////////////////////////////////////////
  //seteo de estructura auxiliar
  public void setCoberturaSolution(IntegerSolution solution) {
	  // inicializo chapas
	  List<List<List<Integer>>> chapas = Collections.synchronizedList(new ArrayList<>());
	  List<Integer> figurasCorregir = Collections.synchronizedList(new ArrayList<>());

	  // Por cada figura creo una chapa.
	  for(int i=0;i<figuras.size();i++) {
		  List<List<Integer>> chapa = new ArrayList<>();
		  for(int y=0;y<anchoChapa;y++) {
			  chapa.add(new ArrayList<Integer>(Collections.nCopies(largoChapa, 0)));
		  }
		  chapas.add(i,chapa);
	  }

	  this.numFigsWithOverlap = 0;

	  for(int i=0; i < solution.getNumberOfVariables(); i+=LARGO_REP_FIGURA) {
		  int id_Figura = i / LARGO_REP_FIGURA;
		  int valueX = solution.getVariable(i);  //X(0..largoChapa-1)
		  int valueY = solution.getVariable(i + 1); //Y(0..anchoChapa-1)
		  int id_Giro = solution.getVariable(i + 2); //id_Giro(0..3) 0=0, 1=90
		  int id_Chapa = solution.getVariable(i + 3); //id_Chapa(0..figuras.size()-1)
		  int lado = solution.getVariable(i + 4);  //lado(0-1) 0=A, 1=B

		  synchronized (mutex) {
			  Figura fig = figuras.get(id_Figura);
			  // si rota
			  if (id_Giro == 1)
				  fig = fig.girarFigura();
			  if (lado == 1)
				  fig = fig.invertirFigura();

			  List<List<Integer>> chapa = chapas.get(id_Chapa);    //copio chapa con id_Chapa en variable chapa

			  if (fig.getSizeY() > anchoChapa || fig.getSizeX() > largoChapa) {
				  // la figura no entra en la chapa.
				  // procedo a rotar
				  fig = fig.girarFigura();
				  if (fig.getSizeY() > anchoChapa || fig.getSizeX() > largoChapa)
					  throw new RuntimeException("No existe solucion, figura no entra en la chapa.");
				  solution.setVariable(id_Figura * LARGO_REP_FIGURA + 2, 1); // roto figura
			  }

			  Integer size_Y = fig.getSizeY(), size_X = fig.getSizeX();

			  int numFigsWithOverlap = 0;
			  boolean overlap = false;

			  for (int y = 0; y < size_Y; y++) {
				  for (int x = 0; x < size_X; x++) {
					  int figv = fig.getFila(y).get(x);
					  if (figv == 0)
						  continue;
					  if (y + valueY < anchoChapa && x + valueX < largoChapa) {
						  if (figv == 1 && chapa.get(y + valueY).get(x + valueX) == 1)
							  overlap = true;
						  chapa.get(valueY + y).set(x + valueX, figv);
					  }
				  }
			  }

			  if (overlap)
				  this.numFigsWithOverlap += 1;
		  }
	  }

	  synchronized (mutexArea) {
		  // Calculamos la cobertura de la solucion en cm²
		  int areaChapa = 0, cantidad = 0;
		  int coberturaChapa;

		  for (int y = 0; y < chapas.size(); y++) {
			  coberturaChapa = chapas.get(y)
					  .stream()
					  .mapToInt(s -> s.stream().mapToInt(Integer::intValue).sum())
					  .sum();

			  if (coberturaChapa > 0)
				  cantidad++;
			  areaChapa += coberturaChapa;
		  }
		  this.coberturaActual = areaChapa; // cargo cobertura actual
		  this.cantidadChapasUsadas = cantidad; // cargo chapas usadas
	  }
  	} // fin set cobertura
  
	/** Evaluate() method */
	@Override
	public void evaluate(IntegerSolution solution) {
		setCoberturaSolution(solution);
		//minimizo zonas sin cobertura

		solution.setObjective(0,(coberturaTotal - coberturaActual) + cantidadChapasUsadas * 100 + this.numFigsWithOverlap*100);
    }
}