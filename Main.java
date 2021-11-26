package com.ae.proyecto;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.*;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.crossover.impl.IntegerSBXCrossover;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.IntegerPolynomialMutation;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.MultithreadedSolutionListEvaluator;

import com.ae.proyecto.impl.Cuello;
import com.ae.proyecto.impl.Curva;
import com.ae.proyecto.impl.Ducto;
import com.ae.proyecto.impl.Garganta;
import com.ae.proyecto.impl.Marco;
import com.ae.proyecto.impl.Pantalon;
import com.ae.proyecto.impl.PiezaImpl;
import com.ae.proyecto.impl.PiezaImpl.Calibre;
import com.ae.proyecto.impl.PiezaImpl.Forma;
import com.ae.proyecto.impl.SizeImpl;
import com.ae.proyecto.impl.Tapa;
import com.ae.proyecto.impl.Transformacion;
import com.ae.proyecto.impl.UnionImpl.UnionT;


/**
 * Class to configure and run a generational genetic algorithm. The target problem is Trazados.
 *
 * @author grupo Q AE 2021
 */
public class Main {

    private final static int POPULATION_SIZE = 50;//100;
    private final static int NUM_ITERATIONS = 1000;//100000; 
    private final static Integer LARGO_CHAPA = 244; 
    private final static Integer ANCHO_CHAPA = 120; 
    private final static double PROBABILIDAD_MUTACION = 0.001; // 0,1 / 0,01 / 0,001
    private final static double PROBABILIDAD_CRUZAMIENTO = 1.0; // 1 / 0,8 / 0,5
    
    private int pedido;
    private int calibre;
    private static int iteraciones = NUM_ITERATIONS;
    private static int poblacion = POPULATION_SIZE;
    private static double mutacion = PROBABILIDAD_MUTACION;
    private static double cruzamiento = PROBABILIDAD_CRUZAMIENTO;
    
    
    
    private static List<com.ae.proyecto.figuras.Figura> readInstanciaFile(String path) {
        List<com.ae.proyecto.figuras.Figura> cortes = new ArrayList<>();

        try {
            for (String line: Files.readAllLines(Paths.get(path))) {
                JSONObject jo = new JSONObject(line);
                String tipo = jo.getString("tipo");
                int cantidad = jo.getInt("cantidad");
                BigDecimal calibre = jo.getBigDecimal("calibre");

                JSONObject dims = jo.getJSONObject("dims");
                com.ae.proyecto.figuras.Figura gf;
                switch (tipo) {
                    case "curva":
                        gf = new com.ae.proyecto.figuras.Curva(
                            dims.getInt("ancho"),
                            dims.getInt("alto"),
                            dims.getInt("radio"),
                            dims.getInt("angulo"),
                            cantidad, calibre
                        );
                        break;
                    case "ducto":
                        gf = new com.ae.proyecto.figuras.Ducto(
                            dims.getInt("ancho"),
                            dims.getInt("alto"),
                            dims.getInt("largo"),
                            cantidad, calibre
                        );
                        break;
                    case "garganta":
                        gf = new com.ae.proyecto.figuras.Garganta(
                                dims.getInt("ancho"),
                                dims.getInt("alto"),
                                dims.getInt("radio"),
                                cantidad, calibre
                        );
                        break;
                    case "transformacion":
                        JSONObject dims_de = dims.getJSONObject("de");
                        JSONObject dims_hasta = dims.getJSONObject("hasta");
                        gf = new com.ae.proyecto.figuras.Transformacion(
                                dims_de.getInt("ancho"),
                                dims_de.getInt("alto"),
                                dims_hasta.getInt("ancho"),
                                dims_hasta.getInt("alto"),
                                dims_hasta.getInt("largo"),
                                cantidad,
                                calibre
                        );

                        break;
                    case "cuello":
                        // El cuello es circular
                        if (dims.has("diametro")) {
                            float diametro = dims.getFloat("diametro");
                            int largo = dims.getInt("largo");
                            gf = new com.ae.proyecto.figuras.CuelloCircular((int)diametro, largo, cantidad, calibre);
                        } else {
                            // El cuello es rectangular
                            gf = new com.ae.proyecto.figuras.CuelloRectangular(
                                    dims.getInt("ancho"),
                                    dims.getInt("alto"),
                                    dims.getInt("largo"),
                                    cantidad,
                                    calibre
                            );
                        }
                        break;
                    case "pantalon":
                        JSONObject inicial = dims.getJSONObject("inicial");
                        JSONObject izq = dims.getJSONObject("izquierda");
                        JSONObject der = dims.getJSONObject("derecha");

                        gf = new com.ae.proyecto.figuras.Pantalon(
                            inicial.getInt("ancho"),
                            inicial.getInt("alto"),
                            cantidad,
                            calibre
                        );
                        ((com.ae.proyecto.figuras.Pantalon)gf).setLadoIzquierdo(
                            izq.getInt("ancho"),
                            izq.getInt("alto"),
                            izq.getInt("radio"),
                            izq.getInt("angulo")
                        );
                        ((com.ae.proyecto.figuras.Pantalon)gf).setLadoDerecho(
                            der.getInt("ancho"),
                            der.getInt("alto"),
                            der.getInt("radio"),
                            der.getInt("angulo")
                        );
                        break;
                    case "tapa":
                        gf = new com.ae.proyecto.figuras.Tapa(dims.getInt("ancho"), dims.getInt("alto"),
                                cantidad, calibre);
                        break;
                    case "marcos":
                        gf = new com.ae.proyecto.figuras.Marco(dims.getInt("ancho"), dims.getInt("alto"),
                                cantidad, calibre);
                        break;
                    default:
                        System.out.println("skip it for now");
                        continue;
                }
                // Set observaciones
                JSONObject obs = jo.getJSONObject("obs");

                String union_inicial = obs.optString("union_inicial");
                String union_final = obs.optString("union_final");
                String direccion = obs.optString("direccion");
                gf.setObservaciones(union_inicial, union_final, direccion);

                System.out.println(gf);
                // Add the figure to the collection
                cortes.add(gf);
            }
        } catch (java.io.IOException e) {
            System.err.println("Archivo "+path+" no encontrado en el path actual.");
            System.exit(-1);
        }
        return cortes;
    }

    public static void solveProblem(List<Figura> figuras,Integer largoChapa, Integer anchoChapa) {
     try (PrintWriter out = new PrintWriter("solution.out")) { 
    	 out.println("Starting solving problem ... ");
        Algorithm<IntegerSolution> algorithm;
      
        Trazados problem = new Trazados(figuras, largoChapa, anchoChapa);
        out.println("Problema: " + problem.getName());
        out.println("Tamaño del cromosoma: " + problem.getNumberOfVariables());
        out.println();
        out.println("Lower Bounds: ");
        //out.println("Id_Figura: "+problem.getLowerBound(0));
        out.println("X: "+problem.getLowerBound(0));
        out.println("Y: "+problem.getLowerBound(1));
        out.println("Id Giro: "+problem.getLowerBound(2));
        out.println("Id Chapa: "+problem.getLowerBound(3));
        out.println("Lado: "+problem.getLowerBound(4));
        out.println();
        out.println("Upper Bounds: ");
        //out.println("Id_Figura: "+problem.getUpperBound(0));
        out.println("X: "+problem.getUpperBound(0));
        out.println("Y: "+problem.getUpperBound(1));
        out.println("Id Giro: "+problem.getUpperBound(2));
        out.println("Id Chapa: "+problem.getUpperBound(3));
        out.println("Lado: "+problem.getUpperBound(4));
        out.println();
                

        CrossoverOperator<IntegerSolution> crossover =
                new IntegerSBXCrossover(cruzamiento,20.0); // ACA
        MutationOperator<IntegerSolution> mutation =
                new IntegerPolynomialMutation(mutacion, 20.0) ;// ACA 1.0 / problem.getNumberOfVariables()
        SelectionOperator<List<IntegerSolution>, IntegerSolution> selection = new BinaryTournamentSelection<IntegerSolution>() ;

        SolutionListEvaluator<IntegerSolution> evaluator = new MultithreadedSolutionListEvaluator<IntegerSolution>(6);
        algorithm = new GeneticAlgorithmBuilder<IntegerSolution>(problem, crossover, mutation)
                .setPopulationSize(poblacion)     //
                .setMaxEvaluations(iteraciones)   //
                .setSelectionOperator(selection)
                .setSolutionListEvaluator(evaluator)
                .build() ;

        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
            .execute() ;

        IntegerSolution solution = algorithm.getResult();
        problem.setCoberturaSolution(solution);

        long computingTime = algorithmRunner.getComputingTime() ;
        
        out.println("Total execution time: " + computingTime + "ms");
        out.println("Solucion: " + solution.getVariables());
        out.println("Detalle: ");
               
        for (int i=0; i < solution.getNumberOfVariables(); i+=5)
        	out.println("Id Figura="+i/5 + " X,Y=(" + solution.getVariable(i)+ "," + solution.getVariable(i + 1)+ ") Id Giro(0=0,1=90,2=180,3=270)=" + solution.getVariable(i + 2)+ " Id Chapa=" + solution.getVariable(i + 3)+ " Lado(0=A,1=B)=" + solution.getVariable(i + 4));
        	
        out.println("Cobertura Total: " + problem.getCoberturaTotal());
        out.println("Cobertura Actual: " + problem.getCoberturaActual());
        out.println("Cantidad de chapas usadas: " + problem.getCantidadChapas());
        out.println("Fitness: " + solution.getObjective(0)) ;
       
        JMetalLogger.logger.info("Solucion fue escrita a: solution.out");
        JMetalLogger.logger.info("Total execution time: " + computingTime/1000 + "s");
        JMetalLogger.logger.info("Cobertura Total: " + problem.getCoberturaTotal());
        JMetalLogger.logger.info("Cobertura Actual: " + problem.getCoberturaActual());
        JMetalLogger.logger.info("Cantidad de chapas usadas: " + problem.getCantidadChapas());
        JMetalLogger.logger.info("Fitness: " + solution.getObjective(0)) ;
        JMetalLogger.logger.info("Iteraciones: " + iteraciones) ;
        JMetalLogger.logger.info("Poblaci�n: " + poblacion) ;
        JMetalLogger.logger.info("Probabilidad de Mutaci�n: " + mutacion) ;
        JMetalLogger.logger.info("Probabilidad de Cruzamiento: " + cruzamiento) ;
        
     }catch (FileNotFoundException e) {
            System.err.println("Error alescribir archivo 'solution.out'");
        }
    } // fin solve

    private static UnionT getUnionType(String union_t) {
        switch (union_t) {
            case "PM":
                return UnionT.PM;
            case "CC":
                return UnionT.CC;
            case "C":
                return UnionT.C;
            case "V":
                return UnionT.V;
            case "T":
                return UnionT.T;
            case "F":
                return UnionT.F;
            default:
                return null;
        }
    }

    private static void parseDireccion(Transformacion t, String direccion) {
        /* Options:
        "céntrica"
        "céntrica, céntrica"
        "céntrica-recta arriba"
        "r der- r arriba"
        "r izq- r arriba"
        "recta arriba"
        "recta der, recta abajo"
        "recta derecha-recta arriba"
        "recta izq, recta abajo"
        "recta izquierda"
        "recta izquierda-recta arriba"
         */
        if (direccion.isEmpty()) {
            t.setLineaW(Transformacion.AlineacionW.NT);
            t.setLineaH(Transformacion.AlineacionH.NT);
        } else if (direccion.contains(",") || direccion.contains("-")) {
            String[] result = direccion.split("[,-]");
            String first = result[0].trim().toLowerCase(Locale.ROOT);
            String second = result[1].trim().toLowerCase(Locale.ROOT);
            if (first.contains("izq")) {
                t.setLineaW(Transformacion.AlineacionW.RI);
            } else if (first.contains("der")) {
                t.setLineaW(Transformacion.AlineacionW.RD);
            } else if (first.contains("ntrica")) {
                t.setLineaW(Transformacion.AlineacionW.C);
            }

            if (second.isEmpty()) {
                t.setLineaH(Transformacion.AlineacionH.NT);
            } else if (second.contains("arriba")) {
                t.setLineaH(Transformacion.AlineacionH.RAR);
            } else if (second.contains("abajo")) {
                t.setLineaH(Transformacion.AlineacionH.RAB);
            } else if (second.contains("ntrica")) {
                t.setLineaH(Transformacion.AlineacionH.C);
            }
        }
    }

    private static List<PiezaImpl> convertCortesToPedido(List<com.ae.proyecto.figuras.Figura> cortes) {
        List<PiezaImpl> pedido = new ArrayList<>();

        for (com.ae.proyecto.figuras.Figura fig: cortes) {
            Calibre calibre = Calibre.VEINTISEIS;
            switch (fig.getCalibre().toString()) {
                case "0.4":
                    calibre = Calibre.VEINTISEIS;
                    break;
                case "0.5":
                    calibre = Calibre.VEINTICUATRO;
                    break;
                case "0.6":
                    calibre = Calibre.VEINTIDOS;
            }
            Size size = new SizeImpl(fig.getAncho(), fig.getAlto());
            Integer cantidad = fig.getCantidad();

            UnionT initialUnion = getUnionType(fig.getUnionInicial());
            UnionT finalUnion = getUnionType(fig.getUnionFinal());

            if (initialUnion == null) {
                if (fig instanceof com.ae.proyecto.figuras.CuelloRectangular ||
                    fig instanceof com.ae.proyecto.figuras.CuelloCircular ||
                    fig instanceof com.ae.proyecto.figuras.Garganta)
                    initialUnion = UnionT.C;
                else
                    initialUnion = UnionT.PM;
                finalUnion = UnionT.PM;
            } else if (finalUnion == null) {
                finalUnion = initialUnion != null ? initialUnion : UnionT.PM;
            }

            PiezaImpl pieza = null;

            if (fig instanceof com.ae.proyecto.figuras.Ducto) {
                Integer largo = ((com.ae.proyecto.figuras.Ducto) fig).getLargo();
                pieza = new Ducto(Forma.RECTANGULAR, size, largo, initialUnion, finalUnion, cantidad, calibre);
            } else if (fig instanceof com.ae.proyecto.figuras.Curva) {
                com.ae.proyecto.figuras.Curva c = (com.ae.proyecto.figuras.Curva)fig;
                pieza = new Curva(Forma.RECTANGULAR, size, c.getRadio(), (double)c.getAngulo(), initialUnion, finalUnion,
                        cantidad, calibre);
            } else if (fig instanceof com.ae.proyecto.figuras.Garganta) {
                com.ae.proyecto.figuras.Garganta g = (com.ae.proyecto.figuras.Garganta)fig;
	            pieza = new Garganta(Forma.RECTANGULAR, size, g.getRadio(), initialUnion, finalUnion,
                        cantidad, calibre);
            } else if (fig instanceof com.ae.proyecto.figuras.Marco) {
                com.ae.proyecto.figuras.Marco m = (com.ae.proyecto.figuras.Marco)fig;
                // Marco no lleva Union Inicial ni Union Final.
                pieza = new Marco(size, cantidad, calibre);
            } else if (fig instanceof com.ae.proyecto.figuras.Tapa) {
                com.ae.proyecto.figuras.Tapa t = (com.ae.proyecto.figuras.Tapa)fig;
                // Tapa no lleva Union Inicial ni Union Final.
                pieza = new Tapa(size, cantidad, calibre);
            } else if (fig instanceof com.ae.proyecto.figuras.CuelloRectangular) {
                com.ae.proyecto.figuras.CuelloRectangular cr = (com.ae.proyecto.figuras.CuelloRectangular)fig;
                pieza = new Cuello(Forma.RECTANGULAR, size, cr.getLargo(), initialUnion, finalUnion,
                        cantidad, calibre);
            } else if (fig instanceof com.ae.proyecto.figuras.CuelloCircular) {
                com.ae.proyecto.figuras.CuelloCircular cr = (com.ae.proyecto.figuras.CuelloCircular)fig;
                pieza = new Cuello(Forma.CIRCULAR, size, cr.getLargo(), initialUnion, finalUnion,
                        cantidad, calibre);
            } else if (fig instanceof com.ae.proyecto.figuras.Pantalon) {
                com.ae.proyecto.figuras.Pantalon pant = (com.ae.proyecto.figuras.Pantalon)fig;
                Size sizeLeft = new SizeImpl(pant.getLadoIzqAncho(), pant.getLadoIzqAlto());
                Size sizeRight = new SizeImpl(pant.getLadoDerAncho(), pant.getLadoDerAlto());

                pieza = new Pantalon(size, sizeRight, pant.getLadoDerRadio(),
                        sizeLeft, pant.getLadoIzqRadio(),
                        initialUnion, finalUnion, finalUnion, cantidad, calibre);
            } else if (fig instanceof com.ae.proyecto.figuras.Transformacion) {
                com.ae.proyecto.figuras.Transformacion trans = (com.ae.proyecto.figuras.Transformacion)fig;

                Size sizeDe = new SizeImpl(trans.getDeAncho(), trans.getDeAlto());
                Size sizeHasta = new SizeImpl(trans.getHastaAncho(), trans.getHastaAlto());

                // TODO: Parsear el campo direccion para ver si encontramos
                // - recta izq -> recta arriba, etc
                pieza = new Transformacion(Forma.RECTANGULAR, Forma.RECTANGULAR,
                        sizeDe, sizeHasta, trans.getHastaLargo(),
                        initialUnion, finalUnion,

                        // Default to no transforma
                        Transformacion.AlineacionW.NT, Transformacion.AlineacionH.NT,
                        cantidad, calibre);
                parseDireccion((Transformacion) pieza, trans.getDireccion());
            }
            if (pieza != null)
                pedido.add(pieza);
        }

        return pedido;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Trazados: start");
        if (args.length != 0) { //==
            System.err.println("error: program <directory | instancia.in>");
            System.exit(1);
        } else {
        	System.out.println(" * argumentos: " + args.length);
        	String path="instancias/0355.in"; // = args[0];
        	String numIteraciones = "1000"; // = args[1];
        	String numPoblacion = "30"; //= args[2];
        	String probMutacion = "0.0001"; //= args[3];
        	String probCruzamiento = "1.0"; //= args[4];
        	
        	iteraciones = Integer.parseInt(numIteraciones); 
        	poblacion = Integer.parseInt(numPoblacion); //= args[2];
        	mutacion = Double.parseDouble(probMutacion); //= args[3];
        	cruzamiento = Double.parseDouble(probCruzamiento); //= args[4];
        	
        	
        	
        	
        	
        	System.out.println(" * file: " + path);
            if (Files.isDirectory(Paths.get(path))) {  
                // read all files and process them
            } else {
                // is just a file
                System.out.println(" * file: " + path);
                System.out.println(" * parameters");
                System.out.println("    * size: "+ LARGO_CHAPA +" cm x "+ ANCHO_CHAPA +" cm");
                System.out.println("    * area: "+ LARGO_CHAPA * ANCHO_CHAPA + " cm²");

                List<com.ae.proyecto.figuras.Figura> cortes = readInstanciaFile(path);

                List<PiezaImpl> pedido = convertCortesToPedido(cortes);

                System.out.println("DEBUG: Piezas convertidas ----\\|/");
                for (Pieza p: pedido) {
                    System.out.println(p.toString());
                }
                System.out.println("DEBUG: Piezas convertidas ----/|\\");

                // Pasar a otra lista de figuras
                List<Figura> pedido_figuras_22 = new ArrayList<>();
                List<Figura> pedido_figuras_24 = new ArrayList<>();
                List<Figura> pedido_figuras_26 = new ArrayList<>();

                for (PiezaImpl p: pedido) {
                    Calibre c = p.getCalibre();
                    for (int i=0; i < p.getCantidad(); i++) {
                        switch (c) {
                            case VEINTIDOS:
                                pedido_figuras_22.addAll(p.getPieza());
                                break;
                            case VEINTICUATRO:
                                pedido_figuras_24.addAll(p.getPieza());
                                break;
                            case VEINTISEIS:
                                pedido_figuras_26.addAll(p.getPieza());
                                break;
                        }
                    }
                }

                System.out.println("DEBUG: Piezas x Calibre chapa");
                System.out.println("  Calibre 22 piezas: " + pedido_figuras_22.size());
                System.out.println("  Calibre 24 piezas: " + pedido_figuras_24.size());
                System.out.println("  Calibre 26 piezas: " + pedido_figuras_26.size());

                try (PrintWriter out = new PrintWriter("result.out")) {
                    //paso figuras a algoritmo
                    solveProblem(pedido_figuras_24, LARGO_CHAPA, ANCHO_CHAPA);
                }
                catch (FileNotFoundException e) {
                    System.err.println("Error al escribir archivo");
                }
            }

        }
    }
}