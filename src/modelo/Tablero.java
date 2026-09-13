package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tablero {
   public static final int tamanio = 4;
   private int[][] celdas;
   Random random = new Random();
   private int proximaFicha;
   private int[][] celdasAnteriores;
   private int proximaFichaAnterior;
   private boolean puedoDeshacer = false;
  
   public Tablero() {
       celdas = new int[tamanio][tamanio];
       generarProximaFicha();
       generarCantidadFichas(3);
   }
   private void generarProximaFicha() {
   	proximaFicha = random.nextInt(3) + 1;
   }
   public void generarCantidadFichas(int cant){
       for(int i = 0; i < cant; i++){
           agregarFichaAleatoria();
       }
   }
   private boolean hayCeldasVacias() {
	    for (int fila = 0; fila < tamanio; fila++) {
	        for (int columna = 0; columna < tamanio; columna++) {
	            if (estaVacia(fila, columna)) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
   //agrrega las fichas iniciales, funciona en tableros mas grandes
   private void agregarFichaAleatoria() {
	   
	   if (!hayCeldasVacias()) {
	        return; 
	   }
       boolean encontro = false;
       while(!encontro){
           //busco posicion random y genero el valor aleatorio
           int x = random.nextInt(tamanio);      // fila entre 0 y 3
           int y = random.nextInt(tamanio);      // columna entre 0 y 3
           //si esta cvacia entonces guardo el valor random y ademas termino el while
           if(estaVacia(x, y)){
               setValor(x, y, proximaFicha);
               encontro = true;
           }
       }
       generarProximaFicha();
   }
   //verifica si una casilla esta vacia osea si es 0, creo metodo para no crear la verificacion dentro de cada metodo llamando a getValor
   private boolean estaVacia(int fila, int colum){
       if (getValor(fila, colum) == 0){
           return true;
       }
       return false;
   }
   private void agregarFichaEnBordeFila(int fila) {
       List<Integer> columnasLibres = new ArrayList<>();
       for (int columna = 0; columna < tamanio; columna++) {
           if (estaVacia(fila, columna)) {
               columnasLibres.add(columna);
           }
       }
       if (columnasLibres.isEmpty()) {
           return;
       }
       int columna = columnasLibres.get(random.nextInt(columnasLibres.size()));
       setValor(fila, columna, proximaFicha);
       generarProximaFicha();
   }
   private void agregarFichaEnBordeColumna(int columna) {
       List<Integer> filasLibres = new ArrayList<>();
       for (int fila = 0; fila < tamanio; fila++) {
           if (estaVacia(fila, columna)) {
               filasLibres.add(fila);
           }
       }
       if (filasLibres.isEmpty()) {
           return;
       }
       int fila = filasLibres.get(random.nextInt(filasLibres.size()));
       setValor(fila, columna, proximaFicha);
       generarProximaFicha();
   }
   //funcion para ver si dos valores son fucionables
   private boolean esFusionable(int valor1, int valor2) {
       if (valor1 == 1 && valor2 == 2) return true;
       if (valor1 == 2 && valor2 == 1) return true;
       return valor1 == valor2 && valor1 != 0 && valor1 % 3 == 0;
   }
   private int[][] clonarMatriz(int[][] original) {
	    int[][] copia = new int[tamanio][tamanio];
	    for (int i = 0; i < tamanio; i++) {
	        System.arraycopy(original[i], 0, copia[i], 0, tamanio);
	    }
	    return copia;
   }
   
   private void guardarEstado() {
	    celdasAnteriores = clonarMatriz(celdas);
	    proximaFichaAnterior = proximaFicha;
	    puedoDeshacer = true;
   }
   public boolean deshacer() {
	    if (!puedoDeshacer) return false;
	    
	    celdas = clonarMatriz(celdasAnteriores);
	    proximaFicha = proximaFichaAnterior;
	    puedoDeshacer = false; // Evita deshacer múltiples veces seguidas si solo quieres 1 paso
	    return true;
   }
    //funcion para mover arriba
    public void moverArriba() {
        guardarEstado();
        for (int columna = 0; columna < tamanio; columna++) {
            boolean[] yaFusiono = new boolean[tamanio]; // marca celdas destino ya usadas en esta jugada
            for (int fila = 1; fila < tamanio; fila++) {
                int actual = getValor(fila, columna);
                if (actual == 0) {
                    continue; // no hay ficha, no hay nada que mover
                }
                int arriba = getValor(fila - 1, columna);
                if (arriba == 0) {
                    setValor(fila - 1, columna, actual); // se mueve para arriba
                    setValor(fila, columna, 0);
                } else if (!yaFusiono[fila - 1] && esFusionable(arriba, actual)) {
                    setValor(fila - 1, columna, arriba + actual); // se fusiona
                    setValor(fila, columna, 0);
                    yaFusiono[fila - 1] = true; // esta celda ya recibió una fusión en esta jugada
                }
                // si no es ninguno de los casos no hace nada
            }
        }
        agregarFichaEnBordeFila(tamanio - 1);
    }

    //funcion para mover abajo
    public void moverAbajo() {
        guardarEstado();
        for (int columna = 0; columna < tamanio; columna++) {
            boolean[] yaFusiono = new boolean[tamanio];
            for (int fila = tamanio - 2; fila >= 0; fila--) {
                int actual = getValor(fila, columna);
                if (actual == 0) {
                    continue;
                }
                int abajo = getValor(fila + 1, columna);
                if (abajo == 0) {
                    setValor(fila + 1, columna, actual);
                    setValor(fila, columna, 0);
                } else if (!yaFusiono[fila + 1] && esFusionable(abajo, actual)) {
                    setValor(fila + 1, columna, abajo + actual);
                    setValor(fila, columna, 0);
                    yaFusiono[fila + 1] = true;
                }
            }
        }
        agregarFichaEnBordeFila(0);
    }

    //funcion para mover izquierda
    public void moverIzquierda() {
        guardarEstado();
        for (int fila = 0; fila < tamanio; fila++) {
            boolean[] yaFusiono = new boolean[tamanio];
            for (int columna = 1; columna < tamanio; columna++) {
                int actual = getValor(fila, columna);
                if (actual == 0) {
                    continue;
                }
                int izquierda = getValor(fila, columna - 1);
                if (izquierda == 0) {
                    setValor(fila, columna - 1, actual);
                    setValor(fila, columna, 0);
                } else if (!yaFusiono[columna - 1] && esFusionable(izquierda, actual)) {
                    setValor(fila, columna - 1, izquierda + actual);
                    setValor(fila, columna, 0);
                    yaFusiono[columna - 1] = true;
                }
            }
        }
        agregarFichaEnBordeColumna(tamanio - 1);
    }

    //funcion para mover derecha
    public void moverDerecha() {
        guardarEstado();
        for (int fila = 0; fila < tamanio; fila++) {
            boolean[] yaFusiono = new boolean[tamanio];
            for (int columna = tamanio - 2; columna >= 0; columna--) {
                int actual = getValor(fila, columna);
                if (actual == 0) {
                    continue;
                }
                int derecha = getValor(fila, columna + 1);
                if (derecha == 0) {
                    setValor(fila, columna + 1, actual);
                    setValor(fila, columna, 0);
                } else if (!yaFusiono[columna + 1] && esFusionable(derecha, actual)) {
                    setValor(fila, columna + 1, derecha + actual);
                    setValor(fila, columna, 0);
                    yaFusiono[columna + 1] = true;
                }
            }
        }
        agregarFichaEnBordeColumna(0);
    }
   public boolean estaTerminado() {
       return !hayEspacioVacio() && !hayFusionPosible();
   }

   //verificamos cada casilla con la de abajo y derecha, no hace falta izquierda y arriba porque comparariamos dos veces
   public boolean hayFusionPosible() {
       for (int fila = 0; fila < tamanio; fila++) {
           for (int columna = 0; columna < tamanio; columna++) {
               int actual = getValor(fila, columna);

               //comparamos derecha
               if (columna + 1 < tamanio) {
                   int derecha = getValor(fila, columna + 1);
                   if (esFusionable(actual, derecha)) {
                       return true;//si se puede fuccionar entonces true
                   }
               }

               // comparamos abajo
               if (fila + 1 < tamanio) {
                   int abajo = getValor(fila + 1, columna);
                   if (esFusionable(actual, abajo)) {
                       return true;//si se puede fuccionar entonces true
                   }
               }
           }
       }
       return false;
   }
 //funcion para saber si en toda la matriz hay un espacio en 0(una de las condiciones para verificar si el juego termino)
   public boolean hayEspacioVacio() {
       //recorremos toda la matriz para buscar casilla
       for (int fila = 0; fila < tamanio; fila++) {
           for (int columna = 0; columna < tamanio; columna++) {
               if (estaVacia(fila, columna)) {
                   return true; //si encontro una entonces retorno true
               }
           }
       }
       return false; //si no fslse(no hay celdas libres)
   }
   //METODO PARA REINICIAR EK JUEGO (reiniciar el tablero*)
   public void reiniciarJuego() {
   	for(int fila = 0; fila < tamanio; fila++) {
   		for(int columna = 0; columna < tamanio; columna++) {
   			celdas[fila][columna] = 0;
   		}
   	}
   	generarCantidadFichas(3);
   }
   
   //
   //PUNTAJE
   //

   //calcula el valor de la ficha elevando nivel a 3
   private int puntosDeFicha(int valor) {
       if (valor < 3) return 0;

       int nivel = 0;
       int v = valor;

       while (v > 3) {
           v = v / 2;//primero dividimos el valor en 2 hasta llegar a un numero menor a 3
           nivel++;//el nivel es la potencia
       }

       return (int) Math.pow(3, nivel);//por ultimo hacemos la potencia de 3 elevado nivel
   }

   //basicamente recorro la matriz y voy llamando puntos de ficha y sumandolos
   public int getPuntaje() {
       int total = 0;
       for (int fila = 0; fila < tamanio; fila++) {
           for (int columna = 0; columna < tamanio; columna++) {
               total += puntosDeFicha(getValor(fila, columna));
           }
       }
       return total;
   }
   public int getValor(int fila, int columna) {
       return celdas[fila][columna];
   }
   public void setValor(int fila, int columna, int valor){
       celdas[fila][columna] = valor;
   }
   public int[][] getCeldas() {
       return celdas;
   }
	public int getProximaFicha() {
		return proximaFicha;
	}
}
