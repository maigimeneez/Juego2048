package vista;

import paquete.Main;
import javax.swing.*;
import java.awt.*;

public class VentanaInicio extends JFrame {

	private Color colorFondo = new Color(30, 50, 100);
	
	public VentanaInicio(){
		setTitle("Threes!");
		setSize(400, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
		
        // JPanel principal 
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(colorFondo);

        //JPanel del menu
        JPanel panelMenu = new JPanel(new GridLayout(4, 1, 0, 20));
        panelMenu.setBackground(colorFondo);

        /////// JLabel del titulo
        JLabel lblTitulo = new JLabel("Threes!", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 42));
        lblTitulo.setForeground(Color.WHITE);
        panelMenu.add(lblTitulo);

        ///////Boton "Jugar"
        JButton botonJugar = crearBotonGeneral("Jugar");
        botonJugar.addActionListener(e -> {
            dispose();//para cierrar la ventana del menú
            Main.iniciarJuego();// Aca se instancia/inicia el juego, llamando al metodo iniciarJuego del main
        
        });
        panelMenu.add(botonJugar);

        /////// Boton "mejores puntajes"
        JButton botonPuntajes = crearBotonGeneral("Mejores Puntajes");
        botonPuntajes.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Proximamente");
        });
        panelMenu.add(botonPuntajes);

        //Boton "Salir"
        JButton botonSalir = crearBotonGeneral("Salir");
        botonSalir.addActionListener(e -> Main.salirDelJuego());
        panelMenu.add(botonSalir);
        
        //para agregar el panel del menu al panel prinmcipal
        panelPrincipal.add(panelMenu);
        add(panelPrincipal);
        setVisible(true);
        
	}
	
	// Metodo aux para dar un un disenio pro defecto a los botones
    private JButton crearBotonGeneral(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setFocusable(false);
        boton.setPreferredSize(new Dimension(200, 40));
        return boton;
    }
	
}
