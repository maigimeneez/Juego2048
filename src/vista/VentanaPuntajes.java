package vista;

import modelo.GestorPuntajes;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaPuntajes extends JFrame {

	private Color colorFondo = new Color(30, 50, 100);
	
	public VentanaPuntajes(JFrame ventanaPadre) {
		setTitle("Mejores Puntajes");
		setSize(400, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        
        /////JPANEL PRINCIPAL
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(colorFondo);

        List<Integer> puntajes = GestorPuntajes.getMejoresPuntajes();
        
        int cantidadFilas = 2 + Math.max(1, puntajes.size());
        
        JPanel panelContenido = new JPanel(new GridLayout(cantidadFilas, 1, 0, 10));
        panelContenido.setBackground(colorFondo);
        
        //JLABEL del titulo		
        JLabel lblTitulo = new JLabel("TOP 10 PUNTAJES", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelContenido.add(lblTitulo);
        
        ////Aca se muestra la lista con puntajes o el mensaje si aun nio hay puntajes
        if (puntajes.isEmpty()) {
            JLabel lblVacio = new JLabel("Aun no hay puntajes registrados", SwingConstants.CENTER);
            lblVacio.setFont(new Font("Arial", Font.ITALIC, 16));
            lblVacio.setForeground(Color.LIGHT_GRAY);
            panelContenido.add(lblVacio);
        } else {
            for (int i = 0; i < puntajes.size(); i++) {
                String textoPuesto = (i + 1) + ".  " + puntajes.get(i) + " pts";
                JLabel lblPuntaje = new JLabel(textoPuesto, SwingConstants.CENTER);
                lblPuntaje.setFont(new Font("Arial", Font.BOLD, 16));
                lblPuntaje.setForeground(Color.WHITE);
                panelContenido.add(lblPuntaje);
            }
        }
        
        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setFocusable(false);
        btnVolver.setPreferredSize(new Dimension(150, 35));
        btnVolver.addActionListener(e -> {
            dispose(); // Cierra esta ventana y vuelve a la anterior
            if (ventanaPadre != null) {
                ventanaPadre.setVisible(true);
            }
        });
        panelContenido.add(btnVolver);
        panelPrincipal.add(panelContenido);
        add(panelPrincipal);
        setVisible(true);
        
	}
	
}
