import java.awt.*;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.border.LineBorder;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * Clase Game de la visualizacion del proyecto, la interfaz del tablero y el juego de QuoriPoob
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class GameScreen extends JFrame{
    private int width; //ancho de la vista
    private int height; //largo de la vista
    private Dimension preferredDimention; //dimensiones de la interfaz
    private QuoriPoob quorindorDom; //Instancia de la clase Quorindior
    private JPanel mainPanel; //Panel principal
    private JPanel[][] casillas; //Matriz de botones para las casillas del tablero
    private JComboBox<String> tipoMuro; //ComboBox para seleccionar el tipo de muro
    private JFileChooser fileChooser;  //Selector de archivos para guardar y cargar partidas
    private int[] posPlayer1; //Posición del Jugador 1
    private int[] posPlayer2; //Posición del Jugador 2
    private long countdownTime = 300000; //Tiempo de cuenta regresiva en milisegundos (5 minutos)
    private Timer timer; //Temporizador para la cuenta regresiva
    private ArrayList<Integer> posicionesWallTable; //posiciones de las casillas donde se pondran los muros
    private ArrayList<JButton> buttonWalls; //arreglo con todos los botones que se van a generar muros


    /**
     * Método principal que inicia la aplicación creando una instancia de SquareGUI y haciéndola visible.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan en este caso)
     */
    public static void main(String[] args){
        GameScreen gui = new GameScreen("normal");
        gui.setVisible(true);
    }

    /**
     * Constructor de objetos de la clase GameScreen
     */
    public GameScreen(String difficulty){
        try {
            quorindorDom = new QuoriPoob(9, difficulty);
        }catch (QuoriPoobException e0){
            System.out.println(e0.getMessage());
            Log.record(e0);
        }
        prepareElements();
        prepareActions();
        posicionesWallTable = new ArrayList<>();
        buttonWalls = new ArrayList<>();
    }

    /**
     * Crea y configura el panel del título con el texto especificado.
     * @param title Título del panel
     * @return Panel del título configurado
     */
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

    /**
     * Prepara y configura los elementos visuales de la interfaz gráfica.
     */
    private void prepareElements(){
        setTitle("Quorindor"); // Establece el título de la ventana
        preferredDimention = Toolkit.getDefaultToolkit().getScreenSize();
        width = preferredDimention.width ;
        height = preferredDimention.height;
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
     */
    private void prepareElementsBoard() {
        mainPanel = new JPanel(new BorderLayout());
        JPanel tableroPanel = createTableroPanel();
        mainPanel.add(tableroPanel,BorderLayout.CENTER);
    }


    /**
     * Prepara los elementos relacionados con la información de los jugadores.
     */
    public void prepareElementsPlayers() {
        prepareLeftPlayer();
        prepareRightPlayer();
        prepareGame();
    }

    /**
     * Prepara y configura los elementos visuales y la disposición de el primer jugador
     */
    private void prepareLeftPlayer() {
        //PANEL IZQUIERDO
        JPanel leftPanel = new JPanel(new GridLayout(5, 1));
        JLabel jugador1 = new JLabel("Jugador 1");
        jugador1.setHorizontalAlignment(SwingConstants.CENTER);
        jugador1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        jugador1.setForeground(Color.WHITE);
        leftPanel.add(jugador1);

        //Circulo de panel izquierdo
        JPanel circulo= new JPanel(new FlowLayout(FlowLayout.CENTER));
        Circle turnoPlayer1= new Circle(new Color(9, 135, 26));
        turnoPlayer1.setPreferredSize(new Dimension(50, 50));
        circulo.add(turnoPlayer1);
        circulo.setBackground(new Color(115, 10, 25));
        leftPanel.add(circulo);
        leftPanel.setBackground(new Color(115, 10, 25));

        //Panel para la info de los muros
        JPanel wallsPanel1 = new JPanel(new GridLayout(4, 1));
        JLabel normales = new JLabel("Muro Normal:");
        normales.setForeground(Color.WHITE);
        normales.setFont(new Font("Times New Roman", Font.BOLD, 15));
        JLabel temporales = new JLabel("Muro Temporal:");
        temporales.setForeground(Color.WHITE);
        temporales.setFont(new Font("Times New Roman", Font.BOLD, 15));
        JLabel largos = new JLabel("Muro Largo:");
        largos.setForeground(Color.WHITE);
        largos.setFont(new Font("Times New Roman", Font.BOLD, 15));
        JLabel aliados = new JLabel("Muro Aliado:");
        aliados.setForeground(Color.WHITE);
        aliados.setFont(new Font("Times New Roman", Font.BOLD, 15));
        normales.setHorizontalAlignment(SwingConstants.CENTER);
        temporales.setHorizontalAlignment(SwingConstants.CENTER);
        largos.setHorizontalAlignment(SwingConstants.CENTER);
        aliados.setHorizontalAlignment(SwingConstants.CENTER);

        wallsPanel1.add(normales);
        wallsPanel1.add(temporales);
        wallsPanel1.add(largos);
        wallsPanel1.add(aliados);
        wallsPanel1.setBackground(new Color(115, 10, 25));
        wallsPanel1.setForeground(Color.WHITE);
        leftPanel.add(wallsPanel1);

        //Panel para la informacion de las casillas
        JPanel casillas1 = new JPanel(new GridLayout(4, 1));
        JLabel normal1 = new JLabel("Casilla Normal:");
        normal1.setForeground(Color.WHITE);
        normal1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        JLabel teleport1 = new JLabel("Casilla Teleport:");
        teleport1.setForeground(Color.WHITE);
        teleport1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        JLabel return1 = new JLabel("Casilla Retorno:");
        return1.setForeground(Color.WHITE);
        return1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        JLabel doubleTurn1 = new JLabel("Casilla Doble Turno:");
        doubleTurn1.setForeground(Color.WHITE);
        doubleTurn1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        normal1.setHorizontalAlignment(SwingConstants.CENTER);
        teleport1.setHorizontalAlignment(SwingConstants.CENTER);
        return1.setHorizontalAlignment(SwingConstants.CENTER);
        doubleTurn1.setHorizontalAlignment(SwingConstants.CENTER);

        casillas1.add(normal1);
        casillas1.add(teleport1);
        casillas1.add(return1);
        casillas1.add(doubleTurn1);
        casillas1.setBackground(new Color(115, 10, 25));
        casillas1.setForeground(Color.WHITE);
        leftPanel.add(casillas1);
        leftPanel.setPreferredSize(new Dimension(200, 0));
        mainPanel.add(leftPanel, BorderLayout.WEST);
    }

    /**
     * Prepara y configura los elementos visuales y la disposición de el segundo jugador
     */
    private void prepareRightPlayer(){
        //PANEL DERECHO
        JPanel rightPanel = new JPanel(new GridLayout(5, 1));
        JLabel jugador2 = new JLabel("Jugador 2");
        jugador2.setHorizontalAlignment(SwingConstants.CENTER);
        jugador2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        jugador2.setForeground(Color.WHITE);
        rightPanel.add(jugador2);

        //Circulo de panel derecho
        JPanel circulo2= new JPanel(new FlowLayout(FlowLayout.CENTER));
        Circle turnoPlayer2= new Circle(new Color(9, 135, 26));
        turnoPlayer2.setPreferredSize(new Dimension(50, 50));
        circulo2.add(turnoPlayer2);
        circulo2.setBackground(new Color(115, 10, 25));
        rightPanel.add(circulo2);
        rightPanel.setBackground(new Color(115, 10, 25));

        //Panel para la info de los muros
        JPanel wallsPanel2 = new JPanel(new GridLayout(4, 1));
        JLabel normales = new JLabel("Muro Normal:");
        normales.setForeground(Color.WHITE);
        normales.setFont(new Font("Times New Roman", Font.BOLD, 15));
        JLabel temporales = new JLabel("Muro Temporal:");
        temporales.setForeground(Color.WHITE);
        temporales.setFont(new Font("Times New Roman", Font.BOLD, 15));
        JLabel largos = new JLabel("Muro Largo:");
        largos.setForeground(Color.WHITE);
        largos.setFont(new Font("Times New Roman", Font.BOLD, 15));
        JLabel aliados = new JLabel("Muro Aliado:");
        aliados.setForeground(Color.WHITE);
        aliados.setFont(new Font("Times New Roman", Font.BOLD, 15));
        normales.setHorizontalAlignment(SwingConstants.CENTER);
        temporales.setHorizontalAlignment(SwingConstants.CENTER);
        largos.setHorizontalAlignment(SwingConstants.CENTER);
        aliados.setHorizontalAlignment(SwingConstants.CENTER);

        wallsPanel2.add(normales);
        wallsPanel2.add(temporales);
        wallsPanel2.add(largos);
        wallsPanel2.add(aliados);
        wallsPanel2.setBackground(new Color(115, 10, 25));
        wallsPanel2.setForeground(Color.WHITE);
        rightPanel.add(wallsPanel2);

        //Panel para la informacion de las casillas
        JPanel casillas2 = new JPanel(new GridLayout(4, 1));
        JLabel normal2 = new JLabel("Casilla normal:");
        normal2.setForeground(Color.WHITE);
        normal2.setFont(new Font("Times New Roman", Font.BOLD, 15));
        JLabel teleport2 = new JLabel("Casilla teleport:");
        teleport2.setForeground(Color.WHITE);
        teleport2.setFont(new Font("Times New Roman", Font.BOLD, 15));
        JLabel return2 = new JLabel("Casilla retorno:");
        return2.setForeground(Color.WHITE);
        return2.setFont(new Font("Times New Roman", Font.BOLD, 15));
        JLabel doubleTurn2 = new JLabel("Casilla doble turno:");
        doubleTurn2.setForeground(Color.WHITE);
        doubleTurn2.setFont(new Font("Times New Roman", Font.BOLD, 15));
        normal2.setHorizontalAlignment(SwingConstants.CENTER);
        teleport2.setHorizontalAlignment(SwingConstants.CENTER);
        return2.setHorizontalAlignment(SwingConstants.CENTER);
        doubleTurn2.setHorizontalAlignment(SwingConstants.CENTER);

        casillas2.add(normal2);
        casillas2.add(teleport2);
        casillas2.add(return2);
        casillas2.add(doubleTurn2);
        casillas2.setBackground(new Color(115, 10, 25));
        casillas2.setForeground(Color.WHITE);
        rightPanel.add(casillas2);
        rightPanel.setPreferredSize(new Dimension(200, 0));
        mainPanel.add(rightPanel, BorderLayout.EAST);
    }

    /**
     * Prepara y configura los elementos del juego, incluyendo los botones de movimiento
     * y el comboBox para el tipo de muro.
     */
    private void prepareGame(){
        JPanel panelJuego = new JPanel(new GridLayout(3, 3));
        //Botones para moverse
        JButton izquierda = new JButton("←");
        JButton derecha = new JButton("→");
        JButton arriba= new JButton("↑");
        JButton abajo = new JButton("↓");
        JButton dUderecha= new JButton("↗");
        JButton dUizquierda= new JButton("↖");
        JButton dDderecha= new JButton("↘");
        JButton dDizquierda= new JButton("↙");
        JPanel relleno = new JPanel();
        //colores para los botones
        izquierda.setBackground(Color.WHITE);
        derecha.setBackground(Color.WHITE);
        arriba.setBackground(Color.WHITE);
        abajo.setBackground(Color.WHITE);
        dUderecha.setBackground(Color.WHITE);
        dUizquierda.setBackground(Color.WHITE);
        dDderecha.setBackground(Color.WHITE);
        dDizquierda.setBackground(Color.WHITE);
        relleno.setBackground(new Color(115, 10, 25));
        //anadir los botones al panel
        panelJuego.add(dUizquierda);
        panelJuego.add(arriba);
        panelJuego.add(dUderecha);
        panelJuego.add(izquierda);
        panelJuego.add(relleno);
        panelJuego.add(derecha);
        panelJuego.add(dDizquierda);
        panelJuego.add(abajo);
        panelJuego.add(dDderecha);

        //PANEL DE ABAJO GENERAL
        JPanel panelAbajoTablero = new JPanel(new BorderLayout());
        panelAbajoTablero.setPreferredSize(new Dimension(width, height / 6));
        panelAbajoTablero.setBackground(Color.GRAY);
        //Relleno de izquierda
        JPanel rellenoIzquierda = new JPanel();
        rellenoIzquierda.setPreferredSize(new Dimension(650, height / 6));
        rellenoIzquierda.setBackground(new Color(115, 10, 25));
        //
        JPanel panelRelleno = new JPanel(new BorderLayout());
        panelRelleno.setPreferredSize(new Dimension(250, 50));
        panelRelleno.setBackground(new Color(115, 10, 25));
        rellenoIzquierda.add(panelRelleno, BorderLayout.WEST);
        //Selector de muros
        String[] tiposMuro = { "Muro Normal", "Muro Largo", "Muro Aliado", "Muro Temporal"};
        tipoMuro = new JComboBox<>(tiposMuro);
        tipoMuro.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        tipoMuro.setBackground(Color.WHITE);
        tipoMuro.setPreferredSize(new Dimension(180, 60));
        rellenoIzquierda.add(tipoMuro, BorderLayout.CENTER);
        //Cronometro
        JLabel cronometro = new JLabel("05:00");
        cronometro.setForeground(Color.WHITE);
        cronometro.setFont(new Font("Times New Roman", Font.BOLD, 24));
        cronometro.setPreferredSize(new Dimension(180, 50));
        rellenoIzquierda.add(cronometro, BorderLayout.EAST);
        //Relleno de derecha
        JPanel rellenoDerecha = new JPanel();
        rellenoDerecha.setPreferredSize(new Dimension(200, height / 6));
        rellenoDerecha.setBackground(new Color(115, 10, 25));
        //Anadir los paneles a el PANEL GENERAL CENTRAL
        panelAbajoTablero.add(rellenoIzquierda, BorderLayout.WEST);
        panelAbajoTablero.add(panelJuego, BorderLayout.CENTER);
        panelAbajoTablero.add(rellenoDerecha, BorderLayout.EAST);

        mainPanel.add(panelAbajoTablero, BorderLayout.SOUTH);

        prepareGameActionListeners();
        startTimer();
    }

    /**
     * Inicia el temporizador del juego.
     */
    private void startTimer() {
        //indica si el tiempo se acabo
        if (timer == null) {
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    countdownTime -= 1000;
                    if (countdownTime >= 0) {
                        updateCronometro(countdownTime);
                    } else {
                        timer.stop();
                    }
                }
            });
            timer.start();
        }
    }

    /**
     * Actualiza el cronómetro con el tiempo restante.
     * @param time Tiempo restante en milisegundos
     */
    private void updateCronometro(long time) {
        long minutes = time / 60000;
        long seconds = (time % 60000) / 1000;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        JPanel panelAbajoTablero= (JPanel) mainPanel.getComponent(3);
        JPanel rellenoizquierda = (JPanel) panelAbajoTablero.getComponent(0);
        JLabel cronometro = (JLabel) rellenoizquierda.getComponent(2);
        cronometro.setText(timeString);
    }

    /**
     * Prepara los listeners para los botones de movimiento
     */
    private void prepareGameActionListeners(){
        JPanel panelAbajoTablero= (JPanel) mainPanel.getComponent(3);
        JPanel panelJuego = (JPanel) panelAbajoTablero.getComponent(1);
        //DIAGONAL NORTE OESTE
        JButton dUizquierda = (JButton) panelJuego.getComponent(0);
        dUizquierda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int[] nw = quorindorDom.move("nw");
                    refresh(nw);
                } catch (Exception e0) {
                    JOptionPane.showMessageDialog(null, e0.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
                    Log.record(e0);
                }
            }
        });
        //NORTE
        JButton arriba = (JButton) panelJuego.getComponent(1);
        arriba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int[] n=quorindorDom.move("n");
                    refresh(n);
                } catch (Exception e0) {
                    JOptionPane.showMessageDialog(null, e0.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
                    Log.record(e0);
                }
            }
        });
        //DIAGONAL NORTE ESTE
        JButton dUderecha = (JButton) panelJuego.getComponent(2);
        dUderecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int[] ne =quorindorDom.move("ne");
                    refresh(ne);
                } catch (Exception e0) {
                    JOptionPane.showMessageDialog(null, e0.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
                    Log.record(e0);
                }
            }
        });
        //OESTE
        JButton izquierda = (JButton) panelJuego.getComponent(3);
        izquierda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int[] w=quorindorDom.move("w");
                    refresh(w);
                } catch (Exception e0) {
                    JOptionPane.showMessageDialog(null, e0.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
                    Log.record(e0);
                }
            }
        });
        //ESTE
        JButton derecha = (JButton) panelJuego.getComponent(5);
        derecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int[] ei= quorindorDom.move("e");
                    refresh(ei);
                } catch (Exception e0) {
                    JOptionPane.showMessageDialog(null, e0.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
                    Log.record(e0);
                }
            }
        });
        //DIAGONAL SUR OESTE
        JButton dDizquierda = (JButton) panelJuego.getComponent(6);
        dDizquierda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int [] sw =quorindorDom.move("sw");
                    refresh(sw);
                } catch (Exception e0) {
                    JOptionPane.showMessageDialog(null, e0.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
                    Log.record(e0);
                }
            }
        });
        //SUR
        JButton abajo = (JButton) panelJuego.getComponent(7);
        abajo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int [] s =quorindorDom.move("s");
                    refresh(s);
                } catch (Exception e0) {
                    JOptionPane.showMessageDialog(null, e0.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
                    Log.record(e0);
                }
            }
        });
        //DIAGONAL SUR ESTE
        JButton dDderecha = (JButton) panelJuego.getComponent(8);
        dDderecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int[] se =quorindorDom.move("se");
                    refresh(se);
                } catch (Exception e0) {
                    JOptionPane.showMessageDialog(null, e0.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
                    Log.record(e0);
                    //e0.printStackTrace();
                }
            }
        });
    }

    /**
     * Metodo para crear el tablero
     */
    private JPanel createTableroPanel(){
        // Crea un nuevo JPanel con un diseño de cuadrícula GridLayout
        int longTable = quorindorDom.getSizeTable();
        String[][] tableroDom = quorindorDom.getBoard();
        JPanel tableroPanel = new JPanel(new GridLayout(longTable, longTable));
        casillas = new JPanel[longTable][longTable];
        // Itera sobre las filas y columnas para crear y agregar casillas al tablero
        for(int i = 0; i < longTable; i++){
            for(int j = 0; j < longTable; j++){
                // Crea una casilla y la agrega al tablero
                casillas[i][j] = createCasilla(i,j,tableroDom[i][j]);

                tableroPanel.add(casillas[i][j]);
            }
        }
        // Establece un borde alrededor del tablero
        tableroPanel.setBorder(BorderFactory.createLineBorder(new Color(255,255,255), 5));
        // Retorna el panel del tablero configurado
        return tableroPanel;
    }

    /**
     * Metodo para crear una casilla
     */
    private JPanel createCasilla(int i,int j,String tipoCasilla) {
        JPanel casilla = new JPanel(new BorderLayout());
        casilla.setOpaque(true);
        switch (tipoCasilla) {
            case "Normal":
                casilla.setBackground(new Color(210, 180, 140));
                break;
            case "Teletransportador":
                casilla.setBackground(new Color(217, 133, 59));
                break;
            case "Regresar":
                casilla.setBackground(new Color(255, 239, 0));
                break;
            case "Doble":
                casilla.setBackground(new Color(220, 20, 60));
            default:
                casilla.setBackground(new Color(210, 180, 140));
                break;
        }
        casilla.setBorder(new RoundBorder(new Color(115, 10, 25), 2, 15));
        casilla.setLayout(new BorderLayout());
        // Crea el panel central que representa el centro de la casilla
        JPanel centerButton = createButtonPanel(new Color(210, 180, 140),i,j);

        casilla.add(centerButton, BorderLayout.CENTER);
        return casilla;
    }

    /**
     * Metodo para crear un panel de botones bordes en la casilla
     * @param bgColor color del fondo para los botones
     * @return Jpanel la casilla con los botones laterales
     */
    private JPanel createButtonPanel(Color bgColor,int i,int j) {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(bgColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder());
        // Crea y agrega los botones direccionales al panel
        JButton upButton = dimensionButton(bgColor);
        JButton downButton = dimensionButton(bgColor);
        JButton leftButton = dimensionButton(bgColor);
        JButton rightButton = dimensionButton(bgColor);
        // Crea un panel central para representar el centro de la casilla
        JPanel center = new JPanel();
        center.setBackground(bgColor);

        // Agrega el MouseListener a cada botón para detectar el clic del usuario
        addMouseListenerToButton(upButton,i,j);
        addMouseListenerToButton(downButton,i,j);
        addMouseListenerToButton(leftButton,i,j);
        addMouseListenerToButton(rightButton,i,j);

        // Configura el diseño del panel de botones direccionales y los agrega al panel principal
        buttonPanel.add(upButton, BorderLayout.NORTH);
        buttonPanel.add(downButton, BorderLayout.SOUTH);
        buttonPanel.add(leftButton, BorderLayout.WEST);
        buttonPanel.add(rightButton, BorderLayout.EAST);
        buttonPanel.add(center, BorderLayout.CENTER);

        return buttonPanel;
    }

    /**
     * Agrega un MouseListener al botón para detectar el clic y establecerlo como el botón seleccionado
     * @param button JButton al cual se le agrega el MouseListener
     */
    private void addMouseListenerToButton(JButton button,int i,int j) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                actualMuro(button,i ,j);
            }
        });
    }


    /**
     * Configurar los botones de direcciones
     * @param bgColor color del fondo para los botones
     * @return JButton las dimeniones de los botones
     */
    private JButton dimensionButton(Color bgColor) {
        JButton button = new JButton();
        button.setBackground(bgColor);
        button.setBorder(BorderFactory.createLineBorder(bgColor));
        button.setPreferredSize(new Dimension(8, 8));
        return button;
    }

    private Color colorWall(String tipoMuro){
        switch (tipoMuro){
            case "Muro Normal":
                return new Color(101, 67, 33);
            case "Muro Largo":
                return new Color(101, 67, 33);
            case "Muro Aliado":
                return quorindorDom.getPlayerColor(quorindorDom.getTurn());
            case "Muro Temporal":
                return Color.WHITE;
            default:
                return new Color(210, 180, 140);
        }
    }

    private void actualMuro( JButton button,int i, int j) {
        String tipoMuroSeleccionado = (String) tipoMuro.getSelectedItem();
        button.setBackground(colorWall(tipoMuroSeleccionado));
        JPanel casilla = casillas[i][j];
        JPanel positions = (JPanel) casilla.getComponent(0);
        int posX=-1;
        int posY=-1;
        for(int k = 0; k < 4; k++){
            JButton boton = (JButton) positions.getComponent(k);
            if(boton == button){
                switch(k) {
                    case 0://arriba
                        posX = i - 1;
                        posY = j;
                        break;
                    case 1://abajo
                        posX = i + 1;
                        posY = j;
                        break;
                    case 2://izquierda
                        posX = i;
                        posY = j - 1;
                        break;
                    case 3://derecha
                        posX = i;
                        posY = j + 1;
                        break;
                }
            }
        }
        posicionesWallTable.add(i);
        posicionesWallTable.add(j);
        posicionesWallTable.add(posX);
        posicionesWallTable.add(posY);
        buttonWalls.add(button);
        if(!tipoMuroSeleccionado.equals("Muro Largo") && posicionesWallTable.size()== 8){
            try {
                quorindorDom.addWall(tipoMuroSeleccionado, posicionesWallTable);
                buttonWalls.clear();
                int turn = quorindorDom.getTurn();
                if (turn == 1) {
                    refresh(posPlayer1);
                } else {
                    refresh(posPlayer2);
                }
            }catch (QuoriPoobException e0){
                JOptionPane.showMessageDialog(this, e0.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
                Log.record(e0);
                for(JButton boton : buttonWalls) {
                    boton.setBackground(colorWall(""));
                }
                posicionesWallTable.clear();
                buttonWalls.clear();
            }
        } else if (tipoMuroSeleccionado.equals("Muro Largo") && posicionesWallTable.size()== 12 ) {
            try {
                quorindorDom.addWall(tipoMuroSeleccionado, posicionesWallTable);
                buttonWalls.clear();
                int turn = quorindorDom.getTurn();
                if (turn == 1) {
                    refresh(posPlayer1);
                } else {
                    refresh(posPlayer2);
                }
            }catch (QuoriPoobException e0){
                JOptionPane.showMessageDialog(this, e0.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
                Log.record(e0);
                for(JButton boton : buttonWalls) {
                    boton.setBackground(colorWall(""));
                }
                posicionesWallTable.clear();
                buttonWalls.clear();
            }
        }
    }


    /**
     * Agrega un componente circulo a una celda específica del tablero.
     * @param component El componente a agregar circulo
     * @param fila La fila de la celda en la matriz de botones del tablero
     * @param columna La columna de la celda en la matriz de botones del tablero
     */
    private void addPlayer(Component component, int fila, int columna) {
        JPanel casilla = casillas[fila][columna];
        JPanel casillaButton = (JPanel) casilla.getComponent(0);
        JPanel center = (JPanel) casillaButton.getComponent(4);
        center.add(component);
    }

    /**
     * Actualiza la información del Jugador 1.
     * @param nombre Nombre del Jugador 1
     * @param color Color del Jugador 1
     */
    public void updatePlayer1(String nombre, Color color) {
        JPanel rightPanel = (JPanel) mainPanel.getComponent(1);
        JLabel jugador1 = (JLabel) rightPanel.getComponent(0);
        jugador1.setText(nombre);
        Circle player1 = new Circle(color);
        addPlayer(player1, 8, 4);
        posPlayer1 = new int[]{8,4};
        try {
            quorindorDom.addPlayer(nombre, color);
        }catch (QuoriPoobException e0){
            JOptionPane.showMessageDialog(this, e0.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            Log.record(e0);
        }
    }

    /**
     * Actualiza la información del Jugador 2.
     * @param nombre Nombre del Jugador 2
     * @param color Color del Jugador 2
     */
    public void updatePlayer2(String nombre, Color color) {
        JPanel leftPanel = (JPanel) mainPanel.getComponent(2);
        JLabel jugador2 = (JLabel) leftPanel.getComponent(0);
        jugador2.setText(nombre);
        Circle player2 = new Circle(color);
        addPlayer(player2, 0, 4);
        posPlayer2 = new int[]{0,4};
        try {
            quorindorDom.addPlayer(nombre, color);
        }catch (QuoriPoobException e0){
            JOptionPane.showMessageDialog(this, e0.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            Log.record(e0);
        }
    }

    /**
     * Actualiza la información de la maquina
     * @param difficulty Dificultad de la maquina
     */
    public void updateMachine(String difficulty) {
        JPanel leftPanel = (JPanel) mainPanel.getComponent(2);
        JLabel jugador2 = (JLabel) leftPanel.getComponent(0);
        jugador2.setText("Robot");
        Circle player2 = new Circle(Color.BLACK);
        addPlayer(player2, 0, 4);
        posPlayer2 = new int[]{0,4};
        try {
            quorindorDom.addMachine(difficulty);
        }catch (QuoriPoobException e0){
            JOptionPane.showMessageDialog(this, e0.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            Log.record(e0);
        }
    }

    /**
     * Refresca el tablero con las posiciones de los jugadores actualizadas.
     * @param positions Posiciones de los jugadores
     */
    private void refresh(int[] positions){
        posicionesWallTable.clear();
        for (JButton boton : buttonWalls){
            boton.setBackground(colorWall(""));
        }
        int turn = quorindorDom.getTurn();
        // Obtener los circulos
        Circle player1 = (Circle) ((JPanel) ((JPanel) mainPanel.getComponent(1)).getComponent(1)).getComponent(0);
        Circle player2 = (Circle) ((JPanel) ((JPanel) mainPanel.getComponent(2)).getComponent(1)).getComponent(0);
        // turno jugador 1, cambia el color
        if (turn == 1) {
            player1.setColor(new Color(210, 180, 140));
            player2.setColor(new Color(115, 10, 25));
        } else { //turno del jugador 2
            player1.setColor(new Color(115, 10, 25));
            player2.setColor(new Color(210, 180, 140));
        }
        // Obtener el JPanel centerButton de la casilla actual del jugador
        JPanel casilla = casillas[positions[0]][positions[1]];
        JPanel casillaButton = (JPanel) casilla.getComponent(0);
        JPanel center = (JPanel) casillaButton.getComponent(4);
        //si el turno es del jugador 1, indica que se acaba de mover el jugador 2
        if(turn==1) {
            JPanel casilla2 = casillas[posPlayer2[0]][posPlayer2[1]];
            JPanel casillaButton2 = (JPanel) casilla2.getComponent(0);
            JPanel jugador = (JPanel) casillaButton2.getComponent(4);
            casillaButton.add(jugador, BorderLayout.CENTER);
            casillaButton2.add(center, BorderLayout.CENTER);
            posPlayer2 = positions;
        }else{//si el turno es del jugador 2, indica que se acaba de mover el jugador 1
            JPanel casilla2 = casillas[posPlayer1[0]][posPlayer1[1]];
            JPanel casillaButton2 = (JPanel) casilla2.getComponent(0);
            JPanel jugador = (JPanel) casillaButton2.getComponent(4);
            casillaButton.add(jugador, BorderLayout.CENTER);
            casillaButton2.add(center, BorderLayout.CENTER);
            posPlayer1 = positions;
        }
        mainPanel.repaint();
        int winner = quorindorDom.playerWin();
        //revisa si el jugador 1 o el jugador 2 gano
        if (winner != 0){
            JOptionPane.showMessageDialog(this, "El jugador "+winner+" ha ganado!",
                    "Ganador",JOptionPane.INFORMATION_MESSAGE);
            playerWinner();
        }
    }

    /**
     * Refresca el tablero con las posiciones de los jugadores actualizadas.
     */
    private void refresh(){
        prepareElements();
        prepareActions();
        posicionesWallTable = new ArrayList<>();
        buttonWalls = new ArrayList<>();
        int turn = quorindorDom.getTurn();
        // Obtener los circulos
        Circle player1 = (Circle) ((JPanel) ((JPanel) mainPanel.getComponent(1)).getComponent(1)).getComponent(0);
        Circle player2 = (Circle) ((JPanel) ((JPanel) mainPanel.getComponent(2)).getComponent(1)).getComponent(0);
        // turno jugador 1, cambia el color
        if (turn == 1) {
            player1.setColor(new Color(210, 180, 140));
            player2.setColor(new Color(115, 10, 25));
        } else { //turno del jugador 2
            player1.setColor(new Color(115, 10, 25));
            player2.setColor(new Color(210, 180, 140));
        }
        //jugador 1
        String namePlayer1 = quorindorDom.getPlayerName(1);
        Color colorPlayer1 = quorindorDom.getPlayerColor(1);
        int[] positions1 = quorindorDom.getPlayerPositions(1);
        posPlayer1 = positions1;
        JPanel rightPanel = (JPanel) mainPanel.getComponent(1);
        JLabel jugador1 = (JLabel) rightPanel.getComponent(0);
        jugador1.setText(namePlayer1);
        Circle movePlayer1 = new Circle(colorPlayer1);
        addPlayer(movePlayer1, positions1[0], positions1[1]);

        //jugador 2
        String namePlayer2 = quorindorDom.getPlayerName(2);
        Color colorPlayer2 = quorindorDom.getPlayerColor(2);
        int[] positions2 = quorindorDom.getPlayerPositions(2);
        posPlayer2 = positions2;
        JPanel leftPanel = (JPanel) mainPanel.getComponent(2);
        JLabel jugador2 = (JLabel) rightPanel.getComponent(0);
        jugador1.setText(namePlayer2);
        Circle movePlayer2 = new Circle(colorPlayer2);
        addPlayer(movePlayer2, positions2[0], positions2[1]);
        //muros
        mainPanel.repaint();
    }


    /**
     * ventana emergente que le indica al usuario que desea hacer despues de haber ganado
     */
    private void playerWinner(){
        Object[] options = {"reiniciar", "configurar", "salir"};
        int resp = JOptionPane.showOptionDialog(this, "deseas volver a jugar?", "ganador",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
        //si desea jugar la partida con la misma configuracion
        if (resp == 0){
            resetBoard();
        }else if(resp == 1){ //si quiere actualizar la configuracion
            setVisible(false);
            MainScreen.main(new String[] {});
        }else{ //si desea salir del juego
            exitOptions();
        }
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
     * Prepara y configura las acciones para los elementos del menú de la interfaz gráfica.
     */
    private void prepareActionsMenu(){
        fileChooser = new JFileChooser();
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
        exitMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                exitOptions();
            }

        });
    }

    /**
     * Abre un cuadro de diálogo para seleccionar un archivo y muestra un mensaje
     * indicando que la funcionalidad está en construcción.
     */
    private void openFile() {
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION){
            File selectFile = fileChooser.getSelectedFile();
            if(selectFile != null){
                try{
                    QuoriPoob g = quorindorDom.openArchivo(selectFile);
                    this.quorindorDom = g;
                    refresh();
                }catch (Exception e){
                    JOptionPane.showMessageDialog(this, "Error: "+e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    Log.record(e);
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
        if (result == JFileChooser.APPROVE_OPTION){
            File selectFile = fileChooser.getSelectedFile();
            if(selectFile != null){
                try{
                    quorindorDom.saveArchivo(selectFile);
                }catch (Exception e){
                    JOptionPane.showMessageDialog(this, "Error: "+e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    Log.record(e);
                }
            }
        }
    }

    /**
     * Reinicia el tablero del juego y muestra un mensaje indicando que la operación fue exitosa.
     */
    private void resetBoard() {
        getContentPane().remove(mainPanel);
        Color color1 = quorindorDom.getPlayerColor(1);
        Color color2 = quorindorDom.getPlayerColor(2);
        String nombreJugador1 = quorindorDom.getPlayerName(1);
        String nombreJugador2 = quorindorDom.getPlayerName(2);
        String difficulty = quorindorDom.getDifficult();
        try{
            quorindorDom = new QuoriPoob(9, difficulty);
        } catch(Exception e0){
            System.out.println(e0.getMessage());
            Log.record(e0);
        }
        prepareElements();
        prepareActions();
        updatePlayer1(nombreJugador1,color1);
        updatePlayer2(nombreJugador2,color2);
        // Validar y repintar
        validate();
        repaint();
        JOptionPane.showMessageDialog(this, "Tablero reiniciado exitosamente.");
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

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
            repaint();
        }
    }
}