package presenter;

import modelo.Tablero;
import vista.VistaTablero;

public class PresenterJuego {
    private Tablero tablero;
    private VistaTablero vista;
    private int puntaje;

    public PresenterJuego(VistaTablero vista) {
        this.vista = vista;
        this.tablero = new Tablero();
        this.puntaje = 0;
    }

    public void iniciarJuego() {
        actualizarVista();
    }
    
    public void onDeshacer() {
        if (tablero.deshacer()) {
            actualizarVista();
        }
    }

    public void onFlechaArriba() {
        tablero.moverArriba();
        actualizarVista();
    }

    public void onFlechaAbajo() {
        tablero.moverAbajo();
        actualizarVista();
    }

    public void onFlechaIzquierda() {
        tablero.moverIzquierda();
        actualizarVista();
    }

    public void onFlechaDerecha() {
        tablero.moverDerecha();
        actualizarVista();
    }
    public void reiniciarJuego() {
    	tablero.reiniciarJuego();
    	actualizarVista();
    }


    private void actualizarVista() {
        vista.mostrarTablero(tablero.getCeldas());
        vista.mostrarProximaFicha(tablero.getProximaFicha());
        vista.mostrarPuntaje(tablero.getPuntaje());
        if (tablero.estaTerminado()) {
            vista.mostrarFinDeJuego(tablero.getPuntaje());
        }
    }
}




