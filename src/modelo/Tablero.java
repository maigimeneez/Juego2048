package modelo;

import java.util.Random;

public class Tablero {
    public static final int tamanio = 4;
    private int[][] celdas;
    Random random = new Random();

    public Tablero() {
        celdas = new int[tamanio][tamanio];
        generarCantidadFichas(3);

    }

    //metodo para no tener que poner reiteradamente la funcion agregarFiachaAleatoria y poder definir el numero inicialmente (quizas mencionar en el informe)
    public void generarCantidadFichas(int cant){
        for(int i = 0; i < cant; i++){
            agregarFichaAleatoria();
        }
    }

    //agrrega las fichas iniciales, funciona en tableros mas grandes
    private void agregarFichaAleatoria() {
        //verifico antes si hay espacio vacio
        if (!hayEspacioVacio()) {
            return;
        }

        boolean encontro = false;
        while(!encontro){
            //busco posicion random y genero el valor aleatorio
            int x = random.nextInt(tamanio);      // fila entre 0 y 3
            int y = random.nextInt(tamanio);      // columna entre 0 y 3
            int valor = random.nextInt(3) + 1;    // 1, 2 o 3

            //si esta cvacia entonces guardo el valor random y ademas termino el while
            if(estaVacia(x, y)){
                setValor(x, y, valor);
                encontro = true;
            }
        }
    }

    //verifica si una casilla esta vacia osea si es 0, creo metodo para no crear la verificacion dentro de cada metodo llamando a getValor
    private boolean estaVacia(int fila, int colum){
        if (getValor(fila, colum) == 0){
            return true;
        }
        return false;
    }

    //funcion para ver si dos valores son fucionables
    private boolean esFusionable(int valor1, int valor2) {
        if (valor1 == 1 && valor2 == 2) return true;
        if (valor1 == 2 && valor2 == 1) return true;
        return valor1 == valor2 && valor1 != 0 && valor1 % 3 == 0;
    }

    //
    //FMOVIMIENTOS
    //

    //funcion para mover arriba
    public void moverArriba() {
        for (int columna = 0; columna < tamanio; columna++) {
            // se guarda lo original antes de tocar algo, asi siempre compara al original
            int[] original = new int[tamanio];
            for (int fila = 0; fila < tamanio; fila++) {
                original[fila] = getValor(fila, columna);
            }

            // Comparamos cada fila con su vecina de arriba, SIEMPRE en base al snapshot
            for (int fila = 1; fila < tamanio; fila++) {
                int arriba = original[fila - 1];
                int actual = original[fila];

                if (actual == 0) {
                    continue; // significa que como no hay ninguna ficha sigue el for nomas, porque no hauy que mover
                }
                //si la ficha de arriba es 0 entonces movemos para arriba
                if (arriba == 0) {
                    setValor(fila - 1, columna, actual); //se mueve para arriba
                    setValor(fila, columna, 0);
                }
                //si la ficha de arriba no es 0 entonces vemos si es fuccionable y la fuccionamos
                else if (esFusionable(arriba, actual)) {
                    setValor(fila - 1, columna, arriba + actual); // se fusiona
                    setValor(fila, columna, 0);
                }
                // si no es ninguno de los casos no hace nada
            }
        }
        agregarFichaAleatoria();//despues de cada movimiento agregamos una ficha aleatoria
    }

    //funcion para mover abajo, practicamente misma logica que mover arriba
    public void moverAbajo() {
        for (int columna = 0; columna < tamanio; columna++) {
            // se guarda lo original antes de tocar algo, asi siempre compara al original
            int[] original = new int[tamanio];
            for (int fila = 0; fila < tamanio; fila++) {
                original[fila] = getValor(fila, columna);
            }

            for (int fila = tamanio - 2; fila >= 0; fila--) {
                int abajo = original[fila + 1];
                int actual = original[fila];

                if (actual == 0) {
                    continue;
                }
                if (abajo == 0) {
                    setValor(fila + 1, columna, actual);
                    setValor(fila, columna, 0);
                }

                else if (esFusionable(abajo, actual)) {
                    setValor(fila +1 , columna, abajo + actual);
                    setValor(fila, columna, 0);
                }
           }
        }
        agregarFichaAleatoria();
    }

    //funcion mover izquierda, funciona guardando la fila original y y comparando filas
    public void moverIzquierda() {
        //gaurdo la fila en vez de la columna
        for (int fila = 0; fila < tamanio; fila++) {
            int[] original = new int[tamanio];
            for (int columna = 0; columna < tamanio; columna++) {
                original[columna] = getValor(fila, columna);
            }

            //hacemos las mismas comparaciones
            for (int columna = 1; columna < tamanio; columna++) {
                int izquierda = original[columna - 1];
                int actual = original[columna];

                if (actual == 0) {
                    continue;
                }

                if (izquierda == 0) {
                    setValor(fila, columna - 1, actual);
                    setValor(fila, columna, 0);
                } else if (esFusionable(izquierda, actual)) {
                    setValor(fila, columna - 1, izquierda + actual);
                    setValor(fila, columna, 0);
                }
            }
        }
        agregarFichaAleatoria();
    }

    //funcion mover derecha, mismo funcionamiento inverso a izquierda, empezando desde tamanio-2 hasta 0
    public void moverDerecha() {
        //gaurdo la fila en vez de la columna
        for (int fila = 0; fila < tamanio; fila++) {
            int[] original = new int[tamanio];
            for (int columna = 0; columna < tamanio; columna++) {
                original[columna] = getValor(fila, columna);
            }

            //hacemos las mismas comparaciones
            for (int columna = tamanio - 2 ; columna > 0 ; columna--) {
                int izquierda = original[columna + 1];
                int actual = original[columna];

                if (actual == 0) {
                    continue;
                }

                if (izquierda == 0) {
                    setValor(fila, columna + 1, actual);
                    setValor(fila, columna, 0);
                } else if (esFusionable(izquierda, actual)) {
                    setValor(fila, columna + 1, izquierda + actual);
                    setValor(fila, columna, 0);
                }
            }
        }
        agregarFichaAleatoria();
    }

    //
    //FINAL DEL JUEGO
    //

    //funcion para terminar que se llama constantemente en el presenter, si cumple las dos condiciones termina el juego
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
        return false;//NO hay ninguna combninacion posible
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
}

