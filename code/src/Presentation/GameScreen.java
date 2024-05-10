
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
public class GameScreen extends JFrame{
    private static final int width = 700; //ancho de la vista
    private static final int height = 700; //largo de la vista
    private static final Dimension preferredDimention = new Dimension(width, height);
    private QuoriPoob quorindorDom; // Instancia de la clase Quorindior
    private JPanel mainPanel; // Panel principal
    private JPanel tableroPanel; // Panel del tablero
    private int filas; //filas de la matriz tablero
    private int columnas; // Columnas de la matriz tablero
    private JButton[][] casillas;// Matriz de botones para las casillas del tablero
    private int turned;

    /**
     * Constructor for objects of class GameScreen
     */
    public GameScreen(){
        quorindorDom = new QuoriPoob(9, "normal");
        filas =9;
        columnas =9;
        prepareElements();
        prepareActions();
    }

    /**
     * Método principal que inicia la aplicación creando una instancia de SquareGUI y haciéndola visible.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan en este caso)
     */
    public static void main(String[] args){
        GameScreen gui = new GameScreen();
        gui.setVisible(true);

    }

    /**
     * Prepara y configura los elementos visuales de la interfaz gráfica.
     */
    private void prepareElements(){
        setTitle("Quorindior"); // Establece el título de la ventana
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Obtiene el tamaño de la pantalla
        // calcula ancho y alto de la ventana
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        // Establece el tamaño de la ventana y lo centra
        setSize(width, height);
        setLocationRelativeTo(null);

        prepareElementsMenu();
        prepareElementsBoard();
        prepareElementsPlayers();
        // Agrega el panel principal al contenedor de la ventana
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
        mainPanel.add(tableroPanel,BorderLayout.CENTER);

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


        JPanel panelJuego = new JPanel(new GridLayout(2, 3));

        JButton izquierda = new JButton("←");
        JButton derecha = new JButton("→");
        JButton arriba= new JButton("↑");
        JButton abajo = new JButton("↓");

        JPanel relleno = new JPanel();
        JPanel relleno2 = new JPanel();

        izquierda.setBackground(Color.WHITE);
        derecha.setBackground(Color.WHITE);
        arriba.setBackground(Color.WHITE);
        abajo.setBackground(Color.WHITE);
        relleno.setBackground(new Color(115, 10, 25));
        relleno2.setBackground(new Color(115, 10, 25));

        panelJuego.add(relleno);
        panelJuego.add(arriba);
        panelJuego.add(relleno2);
        panelJuego.add(izquierda);
        panelJuego.add(abajo);
        panelJuego.add(derecha);


        leftPanel.add(panelJuego, BorderLayout.EAST);
        mainPanel.add(leftPanel, BorderLayout.EAST);

        rightPanel.setPreferredSize(new Dimension(150,0));
        leftPanel.setPreferredSize(new Dimension(150,0));
        //panelJuego.setPreferredSize(new Dimension(1,50);


        derecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    quorindorDom.move("e");
                }catch(Exception e0) {
                    System.out.println("Error");
                }
            }
        });

        izquierda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    quorindorDom.move("w");
                }catch(Exception e0) {
                    System.out.println("Error");
                }
            }
        });

        arriba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    quorindorDom.move("n");
                }catch(Exception e0) {
                    System.out.println(e0.getMessage());
                }
            }
        });

        abajo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    quorindorDom.move("s");
                }catch(Exception e0) {
                    System.out.println("Error");
                }
            }
        });

    }


    /**
     * Prepara y configura las acciones para los elementos del menú de la interfaz gráfica.
     */
    private void prepareActionsMenu(){
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
                saveFile();
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
        exitMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                exitOptions();
            }

        });
    }

    /**
     * Muestra un cuadro de diálogo para confirmar la salida de la aplicación.
     * Cierra la aplicación si el usuario confirma la salida.
     */
    private void exitOptions(){
        int j = JOptionPane.showConfirmDialog(this,"Desea salir de la aplicacion", "Confirmar salida",JOptionPane.YES_NO_OPTION);
        if(j==0){
            System.exit(0);
        }
    }

    /**
     * Abre un cuadro de diálogo para seleccionar un archivo y muestra un mensaje
     * indicando que la funcionalidad está en construcción.
     */
    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "En construcción. Archivo abierto: " + selectedFile.getName(), "Abrir", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Abre un cuadro de diálogo para seleccionar la ubicación de guardado y muestra un mensaje
     * indicando que la funcionalidad está en construcción.
     */
    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "En construcción. Archivo guardado:" + selectedFile.getName(), "Guardar", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Reinicia el tablero del juego y muestra un mensaje indicando que la operación fue exitosa.
     */
    private void resetBoard() {
        getContentPane().remove(mainPanel);
        turned = 0;
        try{
            quorindorDom = new QuoriPoob(9, "normal");
        } catch(Exception ignore){}
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
    private void prepareActions(){
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent event){
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
    private JPanel createTableroPanel(){
        // Crea un nuevo JPanel con un diseño de cuadrícula GridLayout
        JPanel tableroPanel = new JPanel(new GridLayout(filas,columnas));
        // Inicializa la matriz de botones para representar las casillas del tablero
        casillas = new JButton[filas][columnas];
        // Itera sobre las filas y columnas para agregar botones a cada celda del tablero
        for(int i = 0; i < filas; i++){
            for(int j = 0; j < columnas; j++){
                // Crea un nuevo JButton para representar una casilla
                casillas[i][j] = new JButton();
                // Establece la opacidad del botón como verdadero y el fondo del boton con color especifico
                casillas[i][j].setOpaque(true);
                casillas[i][j].setBackground(new Color(210,180, 140));
                // Establece el borde del botón como redondeado utilizando la clase RoundBorder
                casillas[i][j].setBorder(new RoundBorder(new Color(115, 10, 25), 2,15));
                // Agrega el botón al panel del tablero
                tableroPanel.add(casillas[i][j]);
            }
        }
        // Establece un borde alrededor del panel del tablero
        tableroPanel.setBorder(BorderFactory.createLineBorder(new Color(255,255,255), 5));
        //casillas[4][4].setBorder(new WallBorder());
        // Crea un círculo para representar al jugador 1-2 y lo agrega a una celda específica del tablero
        Circle player1 = new Circle(Color.RED);
        addPlayer(player1,8,4);
        Circle player2 = new Circle(Color.ORANGE);
        addPlayer(player2,0,4);
        // Retorna el panel del tablero configurado
        return tableroPanel;
    }

    /**
     * Agrega un componente circulo a una celda específica del tablero.
     *
     * @param component El componente a agregar circulo
     * @param fila La fila de la celda en la matriz de botones del tablero
     * @param columna La columna de la celda en la matriz de botones del tablero
    //* @param casillas La matriz de botones del tablero
     */
    private void addPlayer(Component component, int fila, int columna) {
        JPanel celda = new JPanel(new GridBagLayout());
        celda.setBackground(new Color(210,180, 140));
        celda.add(component);
        casillas[fila][columna].setLayout(new BorderLayout());
        casillas[fila][columna].add(celda, BorderLayout.CENTER);
    }

    public void updatePlayer1(String nombre, Color color) {
        JPanel rightPanel = (JPanel) mainPanel.getComponent(1);
        JLabel jugador1 = (JLabel) rightPanel.getComponent(0);
        jugador1.setText(nombre);
        Circle player1 = new Circle(color);
        addPlayer(player1, 8, 4);
        quorindorDom.addPlayer(nombre, color);
    }

    public void updatePlayer2(String nombre, Color color) {
        JPanel leftPanel = (JPanel) mainPanel.getComponent(2);
        JLabel jugador2 = (JLabel) leftPanel.getComponent(0);
        jugador2.setText(nombre);
        Circle player2 = new Circle(color);
        addPlayer(player2, 0, 4);
        quorindorDom.addPlayer(nombre, color);
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
     * Clase interna WallBorder para crear bordes de paredes.
     */
    private class WallBorder extends LineBorder {
        public WallBorder() {
            super(Color.BLACK, 5);
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