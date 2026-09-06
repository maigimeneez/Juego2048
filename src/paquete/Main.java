package paquete;

import presenter.PresenterJuego;
import vista.VentanaJuego;
import vista.VentanaInicio;

public class Main {
	
    public static void main(String[] args) {
    	new VentanaInicio(); //Al ejecutar el programa el main ahora inicia directo en el menu
    	}
    
    public static void iniciarJuego() { //Y pase el main de instanciar el juego anterior en este metodo, que se ejecuta desde VentanaInicio cuando se clickea en "Jugar" 
    	VentanaJuego ventana = new VentanaJuego();
        PresenterJuego presenter = new PresenterJuego(ventana);
        ventana.setPresenter(presenter);
       presenter.iniciarJuego();
    }
    	
}