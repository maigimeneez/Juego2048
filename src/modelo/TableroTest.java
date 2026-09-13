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