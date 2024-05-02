package Presentation;

import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.border.LineBorder;
import java.awt.geom.RoundRectangle2D;


public class QuorindorGUI extends JFrame{
    private static final int width = 700; //ancho de la vista
    private static final int height = 700; //largo de la vista
    private static final Dimension preferredDimention = new Dimension(width, height);
    private Quorindor quorindorDom;
    private JPanel mainPanel;
    private JPanel tableroPanel;
    private int filas; //filas de la matriz
    private int columnas;
    private JLabel[][] casillas;

    private QuorindorGUI(){
        quorindorDom = new Quorindor(9);
        filas =9;
        columnas =9;
        prepareElements();
        //prepareActions();
    }

    /**
     * Método principal que inicia la aplicación creando una instancia de SquareGUI y haciéndola visible.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan en este caso)
     */
    public static void main(String[] args){
        QuorindorGUI gui = new QuorindorGUI();
        gui.setVisible(true);
    }

    /**
     * Prepara y configura los elementos visuales de la interfaz gráfica.
     */
    private void prepareElements(){
        setTitle("Quorindior");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);
        prepareElementsMenu();
        prepareElementsBoard();
        prepareElementsPlayers();

        getContentPane().add(mainPanel);


    }

    /**
     * Prepara y configura los elementos del menú de la interfaz gráfica.
     */
    private void prepareElementsMenu(){
        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);
        JMenu fileMenu = new JMenu("Menu");
        menu.add(fileMenu);
        JMenuItem newMenuItem = new JMenuItem("Nuevo");
        JMenuItem openMenuItem = new JMenuItem("Abrir");
        JMenuItem saveMenuItem = new JMenuItem("Guardar");
        JMenuItem reiniciarMenuItem = new JMenuItem("Reiniciar");
        JMenuItem exitMenuItem = new JMenuItem("Salir");
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(reiniciarMenuItem);
        fileMenu.add(exitMenuItem);
    }

    /**
     * Prepara y configura los elementos relacionados con el tablero de juego.
     * Esto incluye la creación de botones para cada celda del tablero.
     */
    private void prepareElementsBoard() {
        mainPanel = new JPanel(new BorderLayout());
        tableroPanel = createTableroPanel();
        mainPanel.add(tableroPanel,BorderLayout.CENTER);
    }

    /**
     * Prepara los elementos relacionados con la información de los jugadores.
     */

    public void prepareElementsPlayers() {
        //Jugador1
        JPanel rightPanel = new JPanel(new GridLayout(5, 1));
        JLabel jugador1 = new JLabel("Jugador 1");
        jugador1.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel jugador1Color = new JLabel("Color: ... ");
        jugador1Color.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel jugador1Estado = new JLabel("Estado: ...");
        jugador1Estado.setHorizontalAlignment(SwingConstants.CENTER);
        //jugador1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rightPanel.add(jugador1);
        rightPanel.add(jugador1Color);
        rightPanel.add(jugador1Estado);
        rightPanel.setBackground(new Color(255, 204, 153));
        mainPanel.add(rightPanel, BorderLayout.WEST);

        //Jugador 2
        JPanel leftPanel = new JPanel(new GridLayout(5, 1));
        JLabel jugador2 = new JLabel("Jugador 2");
        jugador2.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel jugador2Color = new JLabel("Color: ... ");
        jugador2Color.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel jugador2Estado = new JLabel("Estado: ...");
        jugador2Estado.setHorizontalAlignment(SwingConstants.CENTER);
        //jugador1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        leftPanel.add(jugador2);
        leftPanel.add(jugador2Color);
        leftPanel.add(jugador2Estado);
        leftPanel.setBackground(new Color(255, 204, 153));
        mainPanel.add(leftPanel, BorderLayout.EAST);

        // Adjust the width according to your needs
        rightPanel.setPreferredSize(new Dimension(150,0));
        leftPanel.setPreferredSize(new Dimension(150,0));
    }

    private JPanel createTableroPanel(){
        JPanel tableroPanel = new JPanel(new GridLayout(filas,columnas));
        casillas = new JLabel[filas][columnas];
        for(int i = 0; i < filas; i++){
            for(int j = 0; j < columnas; j++){
                casillas[i][j] = new JLabel();
                casillas[i][j].setOpaque(true);
                casillas[i][j].setBackground(new Color(175, 132, 98));
                casillas[i][j].setBorder(new RoundBorder(Color.WHITE, 2,15));
                tableroPanel.add(casillas[i][j]);
            }
        }
        tableroPanel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0), 5));
        return tableroPanel;
    }


    // Clase RoundBorder para bordes redondos
    private class RoundBorder extends LineBorder {
        private int radio;

        public RoundBorder(Color color, int thickness, int radio) {
            super(color, thickness);
            this.radio = radio;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(lineColor);
            g2d.setStroke(new BasicStroke(thickness));
            g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radio, radio));
            g2d.dispose();
        }
    }
}
