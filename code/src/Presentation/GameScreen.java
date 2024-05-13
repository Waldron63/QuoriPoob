
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.border.LineBorder;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Ellipse2D;


/**
 * Clase Game de la visualizacion del proyecto, la interfaz del tablero y el juego de QuoriPoob
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class GameScreen extends JFrame {
    private static final int width = 700; //ancho de la vista
    private static final int height = 700; //largo de la vista
    private static final Dimension preferredDimention = new Dimension(width, height);
    private QuoriPoob quorindorDom; // Instancia de la clase Quorindior
    private JPanel mainPanel; // Panel principal
    private JPanel tableroPanel; // Panel del tablero
    private int filas; //filas de la matriz tablero
    private int columnas; // Columnas de la matriz tablero
    private JLabel[][] casillas;// Matriz de botones para las casillas del tablero
    private int turned;
    private JComboBox<String> tipoMuro;
    private JFileChooser fileChooser;
    private int[] posPlayer1;
    private int[] posPlayer2;

    /**
     * Constructor for objects of class GameScreen
     */
    public GameScreen() {
        quorindorDom = new QuoriPoob(9, "normal");
        filas = 9;
        columnas = 9;
        prepareElements();
        prepareActions();
    }

    /**
     * Método principal que inicia la aplicación creando una instancia de SquareGUI y haciéndola visible.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan en este caso)
     */
    public static void main(String[] args) {
        GameScreen gui = new GameScreen();
        gui.setVisible(true);

    }

    /**
     * Prepara y configura los elementos visuales de la interfaz gráfica.
     */
    private void prepareElements() {
        setTitle("Quorindior"); // Establece el título de la ventana
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        // Establece el tamaño de la ventana y lo centra
        setSize(width, height);
        setLocationRelativeTo(null);

        prepareElementsMenu();
        JPanel titlePanel = createTitlePanel("QuoriPoob");
        getContentPane().add(titlePanel, BorderLayout.NORTH);
        prepareElementsBoard();
        prepareElementsPlayers();
        // Agrega el panel principal al contenedor de la ventana
        getContentPane().add(mainPanel);
    }

    /**
     * Prepara y configura los elementos del menú de la interfaz gráfica.
     */
    private void prepareElementsMenu() {
        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);
        JMenu fileMenu = new JMenu("Menu");
        menu.add(fileMenu);
        JMenuItem openMenuItem = new JMenuItem("Abrir");
        JMenuItem saveMenuItem = new JMenuItem("Guardar");
        JMenuItem reiniciarMenuItem = new JMenuItem("Reiniciar");
        JMenuItem exitMenuItem = new JMenuItem("Salir");
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
        mainPanel.add(tableroPanel, BorderLayout.CENTER);

    }


    /**
     * Prepara los elementos relacionados con la información de los jugadores.
     */
    public void prepareElementsPlayers() {
        // Panel Jugador1
        JPanel rightPanel = new JPanel(new GridLayout(5, 1));
        JLabel jugador1 = new JLabel("Jugador 1");
        jugador1.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel jugador1Estado = new JLabel("Estado: ...");
        jugador1Estado.setHorizontalAlignment(SwingConstants.CENTER);
        //jugador1.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Establece fuentes y colores para los componentes
        jugador1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        jugador1.setForeground(Color.WHITE);
        jugador1Estado.setFont(new Font("Times New Roman", Font.BOLD, 16));
        jugador1Estado.setForeground(Color.WHITE);

        // Agrega los componentes al panel derecho
        rightPanel.add(jugador1);
        rightPanel.add(jugador1Estado);
        rightPanel.setBackground(new Color(115, 10, 25));
        mainPanel.add(rightPanel, BorderLayout.WEST);


        //Jugador 2
        JPanel leftPanel = new JPanel(new GridLayout(5, 1));
        JLabel jugador2 = new JLabel("Jugador 2");
        jugador2.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel jugador2Estado = new JLabel("Estado: ...");
        jugador2Estado.setHorizontalAlignment(SwingConstants.CENTER);
        //jugador1.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Establece fuentes y colores para los componentes
        jugador2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        jugador2.setForeground(Color.WHITE);

        jugador2Estado.setFont(new Font("Times New Roman", Font.BOLD, 16));
        jugador2Estado.setForeground(Color.WHITE);


        // Agrega los componentes al panel izquierdo
        leftPanel.add(jugador2);
        leftPanel.add(jugador2Estado);
        leftPanel.setBackground(new Color(115, 10, 25));


        JPanel panelJuego = new JPanel(new GridLayout(3, 3));

        JButton izquierda = new JButton("←");
        JButton derecha = new JButton("→");
        JButton arriba = new JButton("↑");
        JButton abajo = new JButton("↓");
        JButton dUderecha = new JButton("↗");
        JButton dUizquierda = new JButton("↖");
        JButton dDderecha = new JButton("↘");
        JButton dDizquierda = new JButton("↙");

        JPanel relleno = new JPanel();


        izquierda.setBackground(Color.WHITE);
        derecha.setBackground(Color.WHITE);
        arriba.setBackground(Color.WHITE);
        abajo.setBackground(Color.WHITE);
        dUderecha.setBackground(Color.WHITE);
        dUizquierda.setBackground(Color.WHITE);
        dDderecha.setBackground(Color.WHITE);
        dDizquierda.setBackground(Color.WHITE);
        relleno.setBackground(new Color(115, 10, 25));

        panelJuego.add(dUizquierda);
        panelJuego.add(arriba);
        panelJuego.add(dUderecha);
        panelJuego.add(izquierda);
        panelJuego.add(relleno);
        panelJuego.add(derecha);
        panelJuego.add(dDizquierda);
        panelJuego.add(abajo);
        panelJuego.add(dDderecha);


        leftPanel.add(panelJuego, BorderLayout.EAST);
        mainPanel.add(leftPanel, BorderLayout.EAST);

        rightPanel.setPreferredSize(new Dimension(180, 0));
        leftPanel.setPreferredSize(new Dimension(180, 0));

        String[] tiposMuro = {"Muro Temporal", "Muro Largo", "Muro Aliado"};
        tipoMuro = new JComboBox<>(tiposMuro);
        tipoMuro.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        tipoMuro.setBackground(Color.WHITE);
        tipoMuro.setBounds(109, 76, 89, 23);
        rightPanel.add(tipoMuro, BorderLayout.SOUTH);


        derecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    quorindorDom.move("e");
                } catch (Exception e0) {
                    System.out.println("Error");
                }
            }
        });

        izquierda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    quorindorDom.move("w");
                    //refresh();
                } catch (Exception e0) {
                    System.out.println("Error");
                }
            }
        });

        arriba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    quorindorDom.move("n");
                } catch (Exception e0) {
                    System.out.println(e0.getMessage());
                }
            }
        });

        abajo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    quorindorDom.move("s");
                } catch (Exception e0) {
                    System.out.println("Error");
                }
            }
        });

    }


    /**
     * Prepara y configura las acciones para los elementos del menú de la interfaz gráfica.
     */
    private void prepareActionsMenu() {
        //Abrir
        JMenuItem loadMenuItem = getJMenuBar().getMenu(0).getItem(0);
        loadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        // Guardar
        JMenuItem saveMenuItem = getJMenuBar().getMenu(0).getItem(1);
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionSave();
            }
        });

        // Reiniciar
        JMenuItem restartItem = getJMenuBar().getMenu(0).getItem(2);
        restartItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBoard();
            }
        });

        //salir menu
        JMenuItem exitMenuItem = getJMenuBar().getMenu(0).getItem(3);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                exitOptions();
            }

        });
    }

    /**
     * Muestra un cuadro de diálogo para confirmar la salida de la aplicación.
     * Cierra la aplicación si el usuario confirma la salida.
     */
    private void exitOptions() {
        int j = JOptionPane.showConfirmDialog(this, "Desea salir de la aplicacion", "Confirmar salida", JOptionPane.YES_NO_OPTION);
        if (j == 0) {
            System.exit(0);
        }
    }

    /**
     * Abre un cuadro de diálogo para seleccionar un archivo y muestra un mensaje
     * indicando que la funcionalidad está en construcción.
     */
    private void openFile() {
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectFile = fileChooser.getSelectedFile();
            if (selectFile != null) {
                try {
                    //QuoriPoob g = quorindorDom.open01Archivo(selectFile);
                    //this.quorindorDom = g;
                    repaint();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Abre un cuadro de diálogo para seleccionar la ubicación de guardado y muestra un mensaje
     * indicando que la funcionalidad está en construcción.
     */
    private void optionSave() {
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectFile = fileChooser.getSelectedFile();
            if (selectFile != null) {
                try {
                    //quorindorDom.save01Archivo(selectFile);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Reinicia el tablero del juego y muestra un mensaje indicando que la operación fue exitosa.
     */
    private void resetBoard() {
        getContentPane().remove(mainPanel);
        turned = 0;
        try {
            quorindorDom = new QuoriPoob(9, "normal");
        } catch (Exception ignore) {
        }
        prepareElements();
        prepareActions();
        // Validar y repintar
        validate();
        repaint();
        JOptionPane.showMessageDialog(this, "Tablero reiniciado exitosamente.");
    }

    /**
     * Prepara y configura las acciones de los elementos de la interfaz gráfica.
     * Esto incluye acciones para los elementos del menú y los botones del tablero.
     */
    private void prepareActions() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                exitOptions();
            }

        });

        prepareActionsMenu();
    }

    /**
     * Crea y configura el panel del tablero con los botones para cada celda.
     *
     * @return El panel del tablero configurado
     */
    private JPanel createTableroPanel() {
        // Crea un nuevo JPanel con un diseño de cuadrícula GridLayout
        JPanel tableroPanel = new JPanel(new GridLayout(filas, columnas));
        casillas = new JLabel[filas][columnas];
        // Itera sobre las filas y columnas para agregar botones a cada celda del tablero
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                casillas[i][j] = new JLabel();
                // Establece la opacidad del botón como verdadero y el fondo del boton con color especifico
                casillas[i][j].setOpaque(true);
                casillas[i][j].setBackground(new Color(210, 180, 140));

                // Establece el borde del botón como redondeado utilizando la clase RoundBorder
                casillas[i][j].setBorder(new RoundBorder(new Color(115, 10, 25), 2, 15));

                // Agrega el botón al panel del tablero
                tableroPanel.add(casillas[i][j]);

                // Agregar botones alrededor de cada casilla
                JButton upButton = new JButton();  // Botón superior
                JButton downButton = new JButton();  // Botón inferior
                JButton leftButton = new JButton();  // Botón izquierdo
                JButton rightButton = new JButton();  // Botón derecho

                //Color
                upButton.setBackground(new Color(210, 180, 140));
                downButton.setBackground(new Color(210, 180, 140));
                leftButton.setBackground(new Color(210, 180, 140));
                rightButton.setBackground(new Color(210, 180, 140));

                // Color borde
                upButton.setBorder(BorderFactory.createLineBorder(new Color(210, 180, 140)));
                downButton.setBorder(BorderFactory.createLineBorder(new Color(210, 180, 140)));
                leftButton.setBorder(BorderFactory.createLineBorder(new Color(210, 180, 140)));
                rightButton.setBorder(BorderFactory.createLineBorder(new Color(210, 180, 140)));

                //Tamaño de los botones alrededor de cada casilla
                Dimension buttonSize = new Dimension(8, 8);
                upButton.setPreferredSize(buttonSize);
                downButton.setPreferredSize(buttonSize);
                leftButton.setPreferredSize(buttonSize);
                rightButton.setPreferredSize(buttonSize);


                //Boton centro
                JPanel centerButton = new JPanel();
                centerButton.setBackground(new Color(210, 180, 140));
                centerButton.setBorder(BorderFactory.createEmptyBorder());
                JPanel centerPanel = new JPanel(new BorderLayout());
                centerPanel.add(centerButton, BorderLayout.CENTER);

                // Agregar los botones al panel de la casilla actual
                JPanel casillaPanel = new JPanel(new BorderLayout());
                casillaPanel.setBackground(new Color(210, 180, 140));

                casillaPanel.add(upButton, BorderLayout.NORTH);
                casillaPanel.add(downButton, BorderLayout.SOUTH);
                casillaPanel.add(leftButton, BorderLayout.WEST);
                casillaPanel.add(rightButton, BorderLayout.EAST);
                casillaPanel.add(centerPanel, BorderLayout.CENTER);

                // Agregar el panel de la casilla a la celda del tablero
                casillas[i][j].setLayout(new BorderLayout());
                casillas[i][j].add(casillaPanel, BorderLayout.CENTER);
            }
        }
        tableroPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 5));

        // Crea un círculo para representar al jugador 1-2 y lo agrega a una celda específica del tablero
        Circle player1 = new Circle(Color.RED);
        addPlayer(player1, 8, 4);
        Circle player2 = new Circle(Color.ORANGE);
        addPlayer(player2, 0, 4);
        // Retorna el panel del tablero configurado
        return tableroPanel;
    }

    /**
     * Agrega un componente circulo a una celda específica del tablero.
     *
     * @param component El componente a agregar circulo
     * @param fila      La fila de la celda en la matriz de botones del tablero
     * @param columna   La columna de la celda en la matriz de botones del tablero
     *                  //* @param casillas La matriz de botones del tablero
     */
    private void addPlayer(Component component, int fila, int columna) {
        JPanel celda = new JPanel(new GridBagLayout());
        JLabel casilla = casillas[fila][columna];
        JPanel panel = (JPanel) casilla.getComponent(0);
        Color obtColor = panel.getBackground();
        celda.setBackground(obtColor);
        celda.add(component);
        panel.add(celda, BorderLayout.CENTER);
        casillas[fila][columna].add(panel);
    }

    private void delPlayer(int fila, int columna) {
        JPanel celda = new JPanel(new GridBagLayout());
        JLabel casilla = casillas[fila][columna];
        JPanel panel = (JPanel) casilla.getComponent(0);
        Color obtColor = panel.getBackground();
        celda.setBackground(obtColor);
        panel.add(celda, BorderLayout.CENTER);
        casillas[fila][columna].add(panel);
    }

    public void updatePlayer1(String nombre, Color color) {
        JPanel rightPanel = (JPanel) mainPanel.getComponent(1);
        JLabel jugador1 = (JLabel) rightPanel.getComponent(0);
        jugador1.setText(nombre);
        Circle player1 = new Circle(color);
        addPlayer(player1, 8, 4);
        posPlayer1 = new int[]{8, 4};
    }

    public void updatePlayer2(String nombre, Color color) {
        JPanel leftPanel = (JPanel) mainPanel.getComponent(2);
        JLabel jugador2 = (JLabel) leftPanel.getComponent(0);
        jugador2.setText(nombre);
        Circle player2 = new Circle(color);
        addPlayer(player2, 0, 4);
        posPlayer2 = new int[]{0, 4};
    }

    private JPanel createTitlePanel(String title) {
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.setBackground(new Color(115, 10, 25));
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return titlePanel;
    }

    private void refresh(int[] positions) {
        int turn = quorindorDom.getTurn();
        //Refresh de turno panel
        JLabel casilla = casillas[positions[0]][positions[1]];
        JPanel panel = (JPanel) casilla.getComponent(0);
        JPanel centro = (JPanel) panel.getComponent(4);
        if (turn == 1) {
            JLabel casilla2 = casillas[posPlayer2[0]][posPlayer2[1]];
            JPanel panel2 = (JPanel) casilla2.getComponent(0);
            JPanel jugador = (JPanel) panel2.getComponent(4);
            panel.add(jugador, BorderLayout.CENTER);
            casillas[positions[0]][positions[1]].add(panel);
            panel2.add(centro, BorderLayout.CENTER);
            casillas[posPlayer2[0]][posPlayer2[1]].add(panel2);
        } else {
            JLabel casilla2 = casillas[posPlayer1[0]][posPlayer1[1]];
            JPanel panel2 = (JPanel) casilla2.getComponent(0);
            JPanel jugador = (JPanel) panel2.getComponent(4);
            panel.add(jugador, BorderLayout.CENTER);
            casillas[positions[0]][positions[1]].add(panel);
            panel2.add(centro, BorderLayout.CENTER);
            casillas[posPlayer1[0]][posPlayer1[1]].add(panel2);
        }
    }

    /**
     * Clase interna RoundBorder para crear bordes redondos.
     */
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

    /**
     * Clase interna Circle para representar un círculo en la interfaz gráfica.
     */
    public class Circle extends JPanel {
        private Color color;

        public Circle(Color color) {
            this.color = color;
            setPreferredSize(new Dimension(30, 30));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.fill(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
            g2d.dispose();
        }
    }
}