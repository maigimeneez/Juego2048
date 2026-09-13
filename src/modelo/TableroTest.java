package modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TableroTest {

    private Tablero tablero;

    @BeforeEach
    void setUp() {
        tablero = new Tablero();
        limpiarTablero(tablero);
    }

    private void limpiarTablero(Tablero t) {
        for (int fila = 0; fila < Tablero.tamanio; fila++) {
            for (int columna = 0; columna < Tablero.tamanio; columna++) {
                t.setValor(fila, columna, 0);
            }
        }
    }

    private void cargarFila(Tablero t, int fila, int... valores) {
        for (int columna = 0; columna < valores.length; columna++) {
            t.setValor(fila, columna, valores[columna]);
        }
    }

    private void cargarTableroCompleto(Tablero t, int[][] valores) {
        for (int fila = 0; fila < Tablero.tamanio; fila++) {
            for (int columna = 0; columna < Tablero.tamanio; columna++) {
                t.setValor(fila, columna, valores[fila][columna]);
            }
        }
    }

    private int contarFichas(Tablero t) {
        int cantidad = 0;
        for (int fila = 0; fila < Tablero.tamanio; fila++) {
            for (int columna = 0; columna < Tablero.tamanio; columna++) {
                if (t.getValor(fila, columna) != 0) cantidad++;
            }
        }
        return cantidad;
    }
    private void cargarColumna(Tablero t, int columna, int... valores) {
        for (int fila = 0; fila < valores.length; fila++) {
            t.setValor(fila, columna, valores[fila]);
        }
    }

    private int contarFichasEnFila(Tablero t, int fila) {
        int cantidad = 0;
        for (int columna = 0; columna < Tablero.tamanio; columna++) {
            if (t.getValor(fila, columna) != 0) cantidad++;
        }
        return cantidad;
    }

    private int contarFichasEnColumna(Tablero t, int columna) {
        int cantidad = 0;
        for (int fila = 0; fila < Tablero.tamanio; fila++) {
            if (t.getValor(fila, columna) != 0) cantidad++;
        }
        return cantidad;
    }

    private int[][] snapshot(Tablero t) {
        int[][] copia = new int[Tablero.tamanio][Tablero.tamanio];
        for (int fila = 0; fila < Tablero.tamanio; fila++) {
            for (int columna = 0; columna < Tablero.tamanio; columna++) {
                copia[fila][columna] = t.getValor(fila, columna);
            }
        }
        return copia;
    }

    private void assertTableroIgual(int[][] esperado, Tablero actual) {
        for (int fila = 0; fila < Tablero.tamanio; fila++) {
            for (int columna = 0; columna < Tablero.tamanio; columna++) {
                assertEquals(esperado[fila][columna], actual.getValor(fila, columna),
                        "Difiere en fila " + fila + ", columna " + columna);
            }
        }
    }

   
    @Test
    void unoYDosSeFusionanEnTres() {
        cargarFila(tablero, 0, 1, 2, 0, 0);
        tablero.moverIzquierda();
        assertEquals(3, tablero.getValor(0, 0));
    }

    @Test
    void dosYUnoSeFusionanEnTres() {
        cargarFila(tablero, 0, 2, 1, 0, 0);
        tablero.moverIzquierda();
        assertEquals(3, tablero.getValor(0, 0));
    }

    @Test
    void unoYUnoNoSeFusionan() {
        cargarFila(tablero, 0, 1, 1, 0, 0);
        tablero.moverIzquierda();
        assertEquals(1, tablero.getValor(0, 0));
        assertEquals(1, tablero.getValor(0, 1));
    }

    @Test
    void dosYDosNoSeFusionan() {
        cargarFila(tablero, 0, 2, 2, 0, 0);
        tablero.moverIzquierda();
        assertEquals(2, tablero.getValor(0, 0));
        assertEquals(2, tablero.getValor(0, 1));
    }

    @Test
    void tresYTresSeFusionanEnSeis() {
        cargarFila(tablero, 0, 3, 3, 0, 0);
        tablero.moverIzquierda();
        assertEquals(6, tablero.getValor(0, 0));
    }

    @Test
    void seisYSeisSeFusionanEnDoce() {
        cargarFila(tablero, 0, 6, 6, 0, 0);
        tablero.moverIzquierda();
        assertEquals(12, tablero.getValor(0, 0));
    }

    @Test
    void tresYSeisNoSeFusionan() {
        cargarFila(tablero, 0, 3, 6, 0, 0);
        tablero.moverIzquierda();
        // Distintos valores: no se fusionan, y como no hay lugar tampoco se mueven
        assertEquals(3, tablero.getValor(0, 0));
        assertEquals(6, tablero.getValor(0, 1));
    }

    @Test
    void soloSeFusionaUnParPorMovimientoAunqueHayaTresIguales() {
        cargarFila(tablero, 0, 3, 3, 3, 0);
        tablero.moverIzquierda();
        assertEquals(6, tablero.getValor(0, 0));
        assertEquals(3, tablero.getValor(0, 1));
    }


    @Test
    void fichaSeDeslizaUnaSolaCeldaPorMovimiento() {
        cargarFila(tablero, 0, 0, 0, 0, 3);
        tablero.moverIzquierda();
        assertEquals(3, tablero.getValor(0, 2));
    }

    @Test
    void fichaBloqueadaPorValorDistintoNoSeMueve() {
        cargarFila(tablero, 0, 6, 3, 0, 0);
        tablero.moverIzquierda();
        assertEquals(6, tablero.getValor(0, 0));
        assertEquals(3, tablero.getValor(0, 1));
    }


    @Test
    void noEstaTerminadoSiHayCeldaVacia() {
        cargarFila(tablero, 0, 1, 3, 1, 3);
        cargarFila(tablero, 1, 3, 1, 3, 1);
        cargarFila(tablero, 2, 1, 3, 1, 3);
        assertFalse(tablero.estaTerminado());
    }

    @Test
    void noEstaTerminadoSiElTableroEstaLlenoPeroHayFusionPosible() {
        int[][] valores = {
            {3, 3, 1, 3},
            {3, 1, 3, 1},
            {1, 3, 1, 3},
            {3, 1, 3, 1}
        };
        cargarTableroCompleto(tablero, valores);
        assertTrue(tablero.hayFusionPosible());
        assertFalse(tablero.estaTerminado());
    }

    @Test
    void estaTerminadoSiElTableroEstaLlenoYNoHayFusionPosible() {
        int[][] valores = {
            {1, 3, 1, 3},
            {3, 1, 3, 1},
            {1, 3, 1, 3},
            {3, 1, 3, 1}
        };
        cargarTableroCompleto(tablero, valores);
        assertFalse(tablero.hayFusionPosible());
        assertTrue(tablero.estaTerminado());
    }

   
    @Test
    void tableroVacioSumaCero() {
        assertEquals(0, tablero.getPuntaje());
    }

    @Test
    void fichasUnoYDosNoSuman() {
        cargarFila(tablero, 0, 1, 2, 0, 0);
        assertEquals(0, tablero.getPuntaje());
    }

    @Test
    void proximaFichaSiempreEsUnoDosOTres() {
        int valor = tablero.getProximaFicha();
        assertTrue(valor >= 1 && valor <= 3);
    }
    @Test
    void alMoverArribaLaFichaNuevaApareceEnLaFilaInferior() {
        cargarFila(tablero, 0, 3, 3, 3, 3); // fila de arriba llena, nada para mover/fusionar
        cargarFila(tablero, 1, 0, 0, 0, 0);
        cargarFila(tablero, 2, 0, 0, 0, 0);
        cargarFila(tablero, 3, 0, 0, 0, 0);
 
        tablero.moverArriba();
 
        assertEquals(1, contarFichasEnFila(tablero, 3));
        assertEquals(0, contarFichasEnFila(tablero, 1));
        assertEquals(0, contarFichasEnFila(tablero, 2));
    }
 
    @Test
    void alMoverAbajoLaFichaNuevaApareceEnLaFilaSuperior() {
        cargarFila(tablero, 0, 0, 0, 0, 0);
        cargarFila(tablero, 1, 0, 0, 0, 0);
        cargarFila(tablero, 2, 0, 0, 0, 0);
        cargarFila(tablero, 3, 3, 3, 3, 3); // fila de abajo llena, nada para mover/fusionar
 
        tablero.moverAbajo();
 
        assertEquals(1, contarFichasEnFila(tablero, 0));
        assertEquals(0, contarFichasEnFila(tablero, 1));
        assertEquals(0, contarFichasEnFila(tablero, 2));
    }
 
    @Test
    void alMoverIzquierdaLaFichaNuevaApareceEnColumnaDerecha() {
        cargarColumna(tablero, 0, 3, 3, 3, 3); // columna izquierda llena
        cargarColumna(tablero, 1, 0, 0, 0, 0);
        cargarColumna(tablero, 2, 0, 0, 0, 0);
        cargarColumna(tablero, 3, 0, 0, 0, 0);
 
        tablero.moverIzquierda();
 
        assertEquals(1, contarFichasEnColumna(tablero, 3));
        assertEquals(0, contarFichasEnColumna(tablero, 1));
        assertEquals(0, contarFichasEnColumna(tablero, 2));
    }
 
    @Test
    void alMoverDerechaLaFichaNuevaApareceEnColumnaIzquierda() {
        cargarColumna(tablero, 0, 0, 0, 0, 0);
        cargarColumna(tablero, 1, 0, 0, 0, 0);
        cargarColumna(tablero, 2, 0, 0, 0, 0);
        cargarColumna(tablero, 3, 3, 3, 3, 3); // columna derecha llena
 
        tablero.moverDerecha();
 
        assertEquals(1, contarFichasEnColumna(tablero, 0));
        assertEquals(0, contarFichasEnColumna(tablero, 1));
        assertEquals(0, contarFichasEnColumna(tablero, 2));
    }
 
    @Test
    void siElBordeOpuestoEstaCompletamenteLlenoNoApareceFichaNueva() {
        int[][] valores = {
            {1, 3, 1, 3},
            {3, 1, 3, 1},
            {1, 3, 1, 3},
            {3, 1, 3, 1}
        };
        cargarTableroCompleto(tablero, valores);
        int[][] antes = snapshot(tablero);
 
        tablero.moverArriba();
 
        assertTableroIgual(antes, tablero);
    }
 

    @Test
    void reiniciarJuegoDejaExactamenteTresFichas() {
        tablero.reiniciarJuego();
        assertEquals(3, contarFichas(tablero));
    }

    
    @Test
    void deshacerSinMovimientosPreviosNoHaceNada() {
        assertFalse(tablero.deshacer());
    }

    @Test
    void deshacerRestauraElTableroAnterior() {
        cargarFila(tablero, 0, 1, 2, 0, 0);

        tablero.moverIzquierda();

        boolean sePudoDeshacer = tablero.deshacer();

        assertTrue(sePudoDeshacer);
        assertEquals(1, tablero.getValor(0, 0));
        assertEquals(2, tablero.getValor(0, 1));
    }

    @Test
    void noSePuedeDeshacerDosVecesSeguidas() {
        cargarFila(tablero, 0, 1, 2, 0, 0);
        tablero.moverIzquierda();

        assertTrue(tablero.deshacer());
        assertFalse(tablero.deshacer());
    }
}