package paquete;

import javax.swing.JFrame;

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
    	
    public static void salirDelJuego() {
    	System.exit(0);
    }
    
    //metodo para volver hacia el menu desde la ventana del juego/tablero
    public static void volverAlMenu(JFrame ventanaActual) {
    	if (ventanaActual != null) {
            ventanaActual.dispose();
        }
        new VentanaInicio();
    
    }
    
}