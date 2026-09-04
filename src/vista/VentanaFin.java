package vista;

import javax.swing.*;
import java.awt.*;

public class VentanaFin extends JDialog {

    private boolean reiniciar = false;

    //lugar donde se muestra el cartel
    public VentanaFin(Frame padre, int puntajeFinal) {
        super(padre, "Game Over", true);
        setUndecorated(true); 
        setSize(320, 260);
        setLocationRelativeTo(padre);

        Color fondoModal = new Color(28, 30, 58);
        Color tarjetaColor = new Color(36, 40, 72);
        Color colorTextoSecundario = new Color(150, 160, 190);
        Color colorBotonPpal = new Color(247, 93, 114);

        JPanel contenido = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(fondoModal);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                g2.setColor(new Color(247, 93, 114, 100));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 24, 24);
                g2.dispose();
            }
        };
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);
        contenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //frase que dice juego terminado
        JLabel lblTitulo = new JLabel("¡JUEGO TERMINADO!");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(colorBotonPpal);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        //frase que dice puntaje final
        JLabel lblSub = new JLabel("PUNTAJE FINAL");
        lblSub.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblSub.setForeground(colorTextoSecundario);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // muestra el puntaje final
        JLabel lblPuntaje = new JLabel(String.valueOf(puntajeFinal));
        lblPuntaje.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblPuntaje.setForeground(Color.WHITE);
        lblPuntaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        //boton para reiniciar juego
        JButton btnReiniciar = new JButton("¡Jugar de nuevo!");
        btnReiniciar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnReiniciar.setForeground(Color.WHITE);
        btnReiniciar.setBackground(colorBotonPpal);
        btnReiniciar.setFocusPainted(false);
        btnReiniciar.setBorderPainted(false);
        btnReiniciar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReiniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnReiniciar.setMaximumSize(new Dimension(200, 40));
        btnReiniciar.addActionListener(e -> {
            reiniciar = true;
            dispose();
        });
        
        //boton para salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnSalir.setForeground(colorTextoSecundario);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setFocusPainted(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.addActionListener(e -> {
            reiniciar = false;
            dispose();
        });

        contenido.add(lblTitulo);
        contenido.add(Box.createVerticalStrut(15));
        contenido.add(lblSub);
        contenido.add(lblPuntaje);
        contenido.add(Box.createVerticalStrut(20));
        contenido.add(btnReiniciar);
        contenido.add(Box.createVerticalStrut(5));
        contenido.add(btnSalir);

        setContentPane(contenido);
        setBackground(new Color(0, 0, 0, 0)); 
    }

    public boolean debeReiniciar() {
        return reiniciar;
    }
}