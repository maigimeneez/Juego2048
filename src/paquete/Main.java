package paquete;

import presenter.PresenterJuego;
import vista.VentanaJuego;

public class Main {
    public static void main(String[] args) {
        VentanaJuego ventana = new VentanaJuego();
        PresenterJuego presenter = new PresenterJuego(ventana);
        ventana.setPresenter(presenter);
        presenter.iniciarJuego();
    }
}