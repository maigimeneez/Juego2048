package vista;

import presenter.PresenterJuego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class VentanaJuego extends JFrame implements VistaTablero {

    private CeldaGrafica[][] celdas = new CeldaGrafica[4][4];
    private JLabel labelPuntaje;
    private CeldaGrafica celdaProxima;
    private PresenterJuego presenter;

    // PALETA DE COLORES (Inspirada en estética Dark/Neon)
    private static final Color COLOR_FONDO = new Color(24, 25, 48);          // Fondo general azul noche
    private static final Color COLOR_MARCO = new Color(36, 40, 72);          // Contenedores oscuros
    private static final Color COLOR_CELDA_VACIA = new Color(48, 54, 94);   // Hueco del tablero
    
    private static final Color COLOR_FICHA_1 = new Color(79, 168, 246);     // Celeste vibrante
    private static final Color COLOR_FICHA_2 = new Color(247, 93, 114);     // Rojo / Coral
    private static final Color COLOR_FICHA_3 = new Color(245, 245, 248);    // Blanco cálido
    private static final Color COLOR_FICHA_ALTA = new Color(255, 196, 45);   // Amarillo / Dorado (6, 12, etc.)

    public VentanaJuego() {
        setTitle("Threes!");
        setSize(520, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(COLOR_FONDO);

        // --- 1. CABECERA (Puntuación + Próxima Ficha) ---
        JPanel panelCabecera = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelCabecera.setOpaque(false);

        // Tarjeta de Puntaje
        PanelRedondeado tarjetaPuntaje = new PanelRedondeado(COLOR_MARCO, 16);
        tarjetaPuntaje.setLayout(new BoxLayout(tarjetaPuntaje, BoxLayout.Y_AXIS));
        tarjetaPuntaje.setPreferredSize(new Dimension(160, 65));
        tarjetaPuntaje.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JLabel tituloPuntaje = new JLabel("PUNTUACIÓN");
        tituloPuntaje.setFont(new Font("Segoe UI", Font.BOLD, 11));
        tituloPuntaje.setForeground(new Color(150, 160, 190));
        tituloPuntaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        labelPuntaje = new JLabel("0");
        labelPuntaje.setFont(new Font("Segoe UI", Font.BOLD, 22));
        labelPuntaje.setForeground(Color.WHITE);
        labelPuntaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        tarjetaPuntaje.add(tituloPuntaje);
        tarjetaPuntaje.add(labelPuntaje);

        // Tarjeta de Próxima Ficha
        PanelRedondeado tarjetaSiguiente = new PanelRedondeado(COLOR_MARCO, 16);
        tarjetaSiguiente.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 8));
        tarjetaSiguiente.setPreferredSize(new Dimension(160, 65));

        JLabel tituloSiguiente = new JLabel("SIGUIENTE:");
        tituloSiguiente.setFont(new Font("Segoe UI", Font.BOLD, 11));
        tituloSiguiente.setForeground(new Color(150, 160, 190));

        celdaProxima = new CeldaGrafica(12);
        celdaProxima.setPreferredSize(new Dimension(45, 45));

        tarjetaSiguiente.add(tituloSiguiente);
        tarjetaSiguiente.add(celdaProxima);

        panelCabecera.add(tarjetaPuntaje);
        panelCabecera.add(tarjetaSiguiente);
        add(panelCabecera, BorderLayout.NORTH);

        // --- 2. TABLERO PRINCIPAL ---
        PanelRedondeado marcoTablero = new PanelRedondeado(COLOR_MARCO, 24);
        marcoTablero.setLayout(new GridLayout(4, 4, 10, 10));
        marcoTablero.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        marcoTablero.setPreferredSize(new Dimension(400, 400));

        for (int fila = 0; fila < 4; fila++) {
            for (int columna = 0; columna < 4; columna++) {
                CeldaGrafica celda = new CeldaGrafica(16);
                celdas[fila][columna] = celda;
                marcoTablero.add(celda);
            }
        }

        JPanel contenedorCentral = new JPanel(new GridBagLayout());
        contenedorCentral.setOpaque(false);
        contenedorCentral.add(marcoTablero);
        add(contenedorCentral, BorderLayout.CENTER);

        // --- 3. PIE DE PÁGINA (Instrucciones) ---
        JLabel labelInfo = new JLabel("Flechas: Mover  |  Z: Deshacer", SwingConstants.CENTER);
        labelInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelInfo.setForeground(new Color(120, 130, 160));
        labelInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(labelInfo, BorderLayout.SOUTH);

        // --- CAPTURA DE TECLADO ---
        setFocusable(true);
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (presenter == null) return;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> presenter.onFlechaArriba();
                    case KeyEvent.VK_DOWN -> presenter.onFlechaAbajo();
                    case KeyEvent.VK_LEFT -> presenter.onFlechaIzquierda();
                    case KeyEvent.VK_RIGHT -> presenter.onFlechaDerecha();
                    case KeyEvent.VK_Z -> presenter.onDeshacer();
                }
            }
            @Override public void keyReleased(KeyEvent e) {}
            @Override public void keyTyped(KeyEvent e) {}
        });

        setVisible(true);
        requestFocusInWindow();
    }

    public void setPresenter(PresenterJuego presenter) {
        this.presenter = presenter;
    }

    @Override
    public void mostrarTablero(int[][] valores) {
        for (int fila = 0; fila < 4; fila++) {
            for (int columna = 0; columna < 4; columna++) {
                actualizarCelda(celdas[fila][columna], valores[fila][columna]);
            }
        }
    }

    @Override
    public void mostrarProximaFicha(int valor) {
        actualizarCelda(celdaProxima, valor);
    }

    private void actualizarCelda(CeldaGrafica celda, int valor) {
        if (valor == 0) {
            celda.setText("");
            celda.setColorBackground(COLOR_CELDA_VACIA);
        } else {
            celda.setText(String.valueOf(valor));
            if (valor == 1) {
                celda.setColorBackground(COLOR_FICHA_1);
                celda.setForeground(Color.WHITE);
            } else if (valor == 2) {
                celda.setColorBackground(COLOR_FICHA_2);
                celda.setForeground(Color.WHITE);
            } else if (valor == 3) {
                celda.setColorBackground(COLOR_FICHA_3);
                celda.setForeground(new Color(30, 30, 50));
            } else {
                celda.setColorBackground(COLOR_FICHA_ALTA);
                celda.setForeground(new Color(30, 30, 50));
            }
        }
    }

    @Override
    public void mostrarPuntaje(int puntaje) {
        labelPuntaje.setText(String.valueOf(puntaje));
    }

    @Override
    public void mostrarFinDeJuego(int puntajeFinal) {
        JOptionPane.showMessageDialog(this, "¡Juego terminado!\nPuntaje final: " + puntajeFinal);
    }

    // =========================================================================
    // COMPONENTES PERSONALIZADOS CON BORDES REDONDEADOS Y ANTI-ALIASING
    // =========================================================================

    private static class PanelRedondeado extends JPanel {
        private final int radio;
        private Color colorFondo;

        public PanelRedondeado(Color colorFondo, int radio) {
            this.colorFondo = colorFondo;
            this.radio = radio;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(colorFondo);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class CeldaGrafica extends JLabel {
        private final int radio;
        private Color colorBackground = COLOR_CELDA_VACIA;

        public CeldaGrafica(int radio) {
            super("", SwingConstants.CENTER);
            this.radio = radio;
            setFont(new Font("Segoe UI", Font.BOLD, 22));
            setOpaque(false);
        }

        public void setColorBackground(Color c) {
            this.colorBackground = c;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(colorBackground);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}