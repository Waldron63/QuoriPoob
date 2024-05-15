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
    private JLabel[][] casillas;// Matriz de botones para las casillas del tablero
    private int turned;
    private JComboBox<String> tipoMuro;
    private JFileChooser fileChooser;
    private int[] posPlayer1;
    private int[] posPlayer2;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JButton derecha, izquierda, arriba, abajo;


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
        preparePlayer();
        prepareGame();

    }

    private void preparePlayer() {

        JPanel rightPanel = new JPanel(new GridLayout(5, 1));
        JLabel jugador1 = new JLabel("Jugador 1");
        jugador1.setHorizontalAlignment(SwingConstants.CENTER);
        jugador1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        jugador1.setForeground(Color.WHITE);
        rightPanel.add(jugador1);

        JPanel circulo= new JPanel(new FlowLayout(FlowLayout.CENTER));
        Circle turnoPlayer1= new Circle(Color.GREEN);
        turnoPlayer1.setPreferredSize(new Dimension(50, 50));
        circulo.add(turnoPlayer1);
        circulo.setBackground(new Color(115, 10, 25));
        rightPanel.add(circulo);
        rightPanel.setBackground(new Color(115, 10, 25));
        mainPanel.add(rightPanel, BorderLayout.WEST);


        JPanel leftPanel = new JPanel(new GridLayout(5, 1));
        JLabel jugador2 = new JLabel("Jugador 2");
        jugador2.setHorizontalAlignment(SwingConstants.CENTER);
        jugador2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        jugador2.setForeground(Color.WHITE);
        leftPanel.add(jugador2);

        JPanel circulo2= new JPanel(new FlowLayout(FlowLayout.CENTER));
        Circle turnoPlayer2= new Circle(Color.GREEN);
        turnoPlayer2.setPreferredSize(new Dimension(50, 50));
        circulo2.add(turnoPlayer2);
        circulo2.setBackground(new Color(115, 10, 25));
        leftPanel.add(circulo2);
        leftPanel.setBackground(new Color(115, 10, 25));
        mainPanel.add(leftPanel, BorderLayout.EAST);


        JPanel wallsPanel1 = new JPanel(new GridLayout(2, 1));
        JLabel walls = new JLabel("Numero de muros:");
        walls.setForeground(Color.WHITE);
        walls.setHorizontalAlignment(SwingConstants.CENTER);
        walls.setFont(new Font("Times New Roman", Font.BOLD, 15));
        wallsPanel1.add(walls);
        wallsPanel1.setBackground(new Color(115, 10, 25));
        wallsPanel1.setForeground(Color.WHITE);

        //
        JPanel casillas1 = new JPanel(new GridLayout(4, 1));
        JLabel normal1 = new JLabel("Casilla normal:");
        normal1.setForeground(Color.WHITE);
        normal1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        JLabel teleport1 = new JLabel("Casilla teleport:");
        teleport1.setForeground(Color.WHITE);
        teleport1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        JLabel return1 = new JLabel("Casilla retorno:");
        return1.setForeground(Color.WHITE);
        return1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        JLabel doubleTurn1 = new JLabel("Casilla doble turno:");
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

        //
        rightPanel.add(wallsPanel1);
        rightPanel.add(casillas1);

        //
        JPanel wallsPanel2 = new JPanel(new GridLayout(2, 1));
        JLabel walls2 = new JLabel("Numero de muros:");
        walls2.setForeground(Color.WHITE);
        walls2.setFont(new Font("Times New Roman", Font.BOLD, 15));
        walls2.setHorizontalAlignment(SwingConstants.CENTER);
        wallsPanel2.add(walls2);
        wallsPanel2.setBackground(new Color(115, 10, 25));
        wallsPanel2.setForeground(Color.WHITE);

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


        leftPanel.add(wallsPanel2);
        leftPanel.add(casillas2);


        rightPanel.setPreferredSize(new Dimension(200, 0));
        leftPanel.setPreferredSize(new Dimension(200, 0));
    }


    private void prepareGame(){
        JPanel panelJuego = new JPanel(new GridLayout(3, 3));

        JButton izquierda = new JButton("←");
        JButton derecha = new JButton("→");
        JButton arriba= new JButton("↑");
        JButton abajo = new JButton("↓");
        JButton dUderecha= new JButton("↗");
        JButton dUizquierda= new JButton("↖");
        JButton dDderecha= new JButton("↘");
        JButton dDizquierda= new JButton("↙");

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



        JPanel panelAbajoTablero = new JPanel(new BorderLayout());
        panelAbajoTablero.setPreferredSize(new Dimension(width, height / 6));
        panelAbajoTablero.setBackground(Color.GRAY);


        JPanel rellenoIzquierda = new JPanel();
        rellenoIzquierda.setPreferredSize(new Dimension(650, height / 6));
        rellenoIzquierda.setBackground(new Color(115, 10, 25));

        JPanel rellenoDerecha = new JPanel();
        rellenoDerecha.setPreferredSize(new Dimension(200, height / 6));
        rellenoDerecha.setBackground(new Color(115, 10, 25));

        JPanel panelCombo = new JPanel(new BorderLayout());
        panelCombo.setBackground(new Color(115, 10, 25));

        String[] tiposMuro = { "Muro Temporal", "Muro Largo", "Muro Aliado" };
        tipoMuro = new JComboBox<>(tiposMuro);
        tipoMuro.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        tipoMuro.setBackground(Color.WHITE);
        tipoMuro.setPreferredSize(new Dimension(180, 60));

        panelCombo.add(tipoMuro, BorderLayout.WEST);

        JPanel panelCronometro = new JPanel();
        panelCronometro.setBackground(Color.WHITE);
        JLabel labelCronometro = new JLabel("00:00");


        labelCronometro.setFont(new Font("Times New Roman", Font.BOLD, 24));
        panelCronometro.add(labelCronometro);
        panelCronometro.setPreferredSize(new Dimension(180, 50));

        panelCombo.add(panelCronometro, BorderLayout.CENTER);
        rellenoIzquierda.add(panelCombo);


        panelAbajoTablero.add(rellenoIzquierda, BorderLayout.WEST);
        panelAbajoTablero.add(panelJuego, BorderLayout.CENTER);
        panelAbajoTablero.add(rellenoDerecha, BorderLayout.EAST);


        mainPanel.add(panelAbajoTablero, BorderLayout.SOUTH);

        //prepareGameActionListeners();
    }

     /*
    private void prepareGameActionListeners(){
        derecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int[] pos = quorindorDom.move("e");
                    refresh(pos);
                } catch (Exception e0) {
                    System.out.println("Error");
                }
            }
        });

        izquierda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
<<<<<<< HEAD
                    quorindorDom.move("w");
=======
                    int[] pos = quorindorDom.move("w");
                    refresh(pos);
                    //refresh();
>>>>>>> 0f318d067b0d18bbc9f0d403556108797e300f62
                } catch (Exception e0) {
                    System.out.println("Error");
                }
            }
        });

        arriba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int[] pos = quorindorDom.move("n");
                    refresh(pos);
                } catch (Exception e0) {
                    System.out.println(e0.getMessage());
                }
            }
        });

        abajo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int[] pos = quorindorDom.move("s");
                    refresh(pos);
                } catch (Exception e0) {
                    System.out.println("Error");
                }
            }
        });

    }*/





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
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION){
            File selectFile = fileChooser.getSelectedFile();
            if(selectFile != null){
                try{
                    //QuoriPoob g = quorindorDom.open01Archivo(selectFile);
                    //this.quorindorDom = g;
            if (selectFile != null) {
                try {
                    QuoriPoob qp = quorindorDom.openArchivo(selectFile);
                    this.quorindorDom = qp;
                    repaint();
                }catch (Exception e){
                    JOptionPane.showMessageDialog(this, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                    //quorindorDom.save01Archivo(selectFile);
                }catch (Exception e){
                    JOptionPane.showMessageDialog(this, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            if (selectFile != null) {
                try {
                    quorindorDom.saveArchivo(selectFile);
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
     * Metodo para crear el tablero
     */
    private JPanel createTableroPanel(){
        // Crea un nuevo JPanel con un diseño de cuadrícula GridLayout
        JPanel tableroPanel = new JPanel(new GridLayout(filas,columnas));
        casillas = new JLabel[filas][columnas];
        // Itera sobre las filas y columnas para crear y agregar casillas al tablero
        for(int i = 0; i < filas; i++){
            for(int j = 0; j < columnas; j++){
                // Crea una casilla y la agrega al tablero
                casillas[i][j] = createCasilla();
                tableroPanel.add(casillas[i][j]);
            }
        }
        // Establece un borde alrededor del tablero
        tableroPanel.setBorder(BorderFactory.createLineBorder(new Color(255,255,255), 5));

        // Agrega jugadores al tablero en posiciones específicas
        addPlayer(new Circle(Color.RED), 8, 4);
        addPlayer(new Circle(Color.ORANGE), 0, 4);
        // Retorna el panel del tablero configurado
        return tableroPanel;
    }

    /**
     * Metodo para crear una casilla
     */
    private JLabel createCasilla() {
        JLabel casilla = new JLabel();
        casilla.setOpaque(true);
        casilla.setBackground(new Color(210, 180, 140));
        casilla.setBorder(new RoundBorder(new Color(115, 10, 25), 2, 15));
        casilla.setLayout(new BorderLayout());

        // Crea el panel de la casilla que contiene los botones direccionales y el centro
        JPanel casillaPanel = new JPanel(new BorderLayout());
        casillaPanel.setBackground(new Color(210, 180, 140));
        // Crea el panel central que representa el centro de la casilla
        JPanel centerButton = createButtonPanel(new Color(210, 180, 140));
        casillaPanel.add(centerButton, BorderLayout.CENTER);

        // Agrega el panel de la casilla al componente de la casilla
        casilla.add(casillaPanel, BorderLayout.CENTER);

        return casilla;
    }

    /**
     * Metodo para crear un panel de botones bordes en la casilla
     */
    private JPanel createButtonPanel(Color bgColor) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(bgColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder());
        // Crea y agrega los botones direccionales al panel
        JButton upButton = dimensionButton(bgColor);
        JButton downButton = dimensionButton(bgColor);
        JButton leftButton = dimensionButton(bgColor);
        JButton rightButton = dimensionButton(bgColor);

        // Crea un panel central para representar el centro de la casilla
        JPanel centerButton = new JPanel();
        centerButton.setBackground(bgColor);

        // Configura el diseño del panel de botones direccionales y los agrega al panel principal
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(upButton, BorderLayout.NORTH);
        buttonPanel.add(downButton, BorderLayout.SOUTH);
        buttonPanel.add(leftButton, BorderLayout.WEST);
        buttonPanel.add(rightButton, BorderLayout.EAST);
        buttonPanel.add(centerButton, BorderLayout.CENTER);

        return buttonPanel;
    }

    /**
     * Configurar los botones de direcciones
     */
    private JButton dimensionButton(Color bgColor) {
        JButton button = new JButton();
        button.setBackground(bgColor);
        button.setBorder(BorderFactory.createLineBorder(bgColor));
        button.setPreferredSize(new Dimension(8, 8));
        return button;
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
        JLabel casilla = casillas[fila][columna];
        JPanel panel =  (JPanel) casilla.getComponent(0);
        Color obtColor = panel.getBackground();
        celda.setBackground(obtColor);
        celda.add(component);
        panel.add(celda,BorderLayout.CENTER);
        casillas[fila][columna].add(panel);
    }

    private void delPlayer( int fila, int columna){
        JPanel celda = new JPanel(new GridBagLayout());
        JLabel casilla = casillas[fila][columna];
        JPanel panel =  (JPanel) casilla.getComponent(0);
        Color obtColor = panel.getBackground();
        celda.setBackground(obtColor);
        panel.add(celda,BorderLayout.CENTER);
        casillas[fila][columna].add(panel);
    }

    public void updatePlayer1(String nombre, Color color) {
        JPanel rightPanel = (JPanel) mainPanel.getComponent(1);
        JLabel jugador1 = (JLabel) rightPanel.getComponent(0);
        jugador1.setText(nombre);
        Circle player1 = new Circle(color);
        addPlayer(player1, 8, 4);
        posPlayer1 = new int[]{8,4};
        posPlayer1 = new int[]{8, 4};
        quorindorDom.addPlayer(nombre, color);
    }

    public void updatePlayer2(String nombre, Color color) {
        JPanel leftPanel = (JPanel) mainPanel.getComponent(2);
        JLabel jugador2 = (JLabel) leftPanel.getComponent(0);
        jugador2.setText(nombre);
        Circle player2 = new Circle(color);
        addPlayer(player2, 0, 4);
        posPlayer2 = new int[]{0,4};
        posPlayer2 = new int[]{0, 4};
        quorindorDom.addPlayer(nombre, color);
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

    private void refresh(int[] positions){
        int turn = quorindorDom.getTurn();
        // Obtener los circulos
        Circle player1 = (Circle) ((JPanel) mainPanel.getComponent(1)).getComponent(1);
        Circle player2 = (Circle) ((JPanel) mainPanel.getComponent(2)).getComponent(1);
        // turno jugador 1, cambia el color
        if (turn == 1) {
            player1.setColor(Color.WHITE);
            player2.setColor(new Color(115, 10, 25));
        } else { //turno del jugador 2
            player1.setColor(new Color(115, 10, 25));
            player2.setColor(Color.WHITE);
        }

        // Actualizar los colores
        mainPanel.getComponent(1).repaint();
        mainPanel.getComponent(2).repaint();
        //------------------------------------------------
        // Obtener el JPanel centerButton de la casilla actual del jugador
        JLabel casilla = casillas[positions[0]][positions[1]];
        JPanel panel =  (JPanel) casilla.getComponent(0);
        JPanel centro =  (JPanel) panel.getComponent(3);
        if(turn==1){
            JLabel casilla2 = casillas[posPlayer2[0]][posPlayer2[1]];
            JPanel panel2 = (JPanel) casilla2.getComponent(0);
            JPanel jugador = (JPanel) panel2.getComponent(4);
            panel.add(jugador,BorderLayout.CENTER);
            casillas[positions[0]][positions[1]].add(panel);
            panel2.add(centro,BorderLayout.CENTER);
            casillas[posPlayer2[0]][posPlayer2[1]].add(panel2);
        }else{
            JLabel casilla2 = casillas[posPlayer1[0]][posPlayer1[1]];
            JPanel panel2 = (JPanel) casilla2.getComponent(0);
            JPanel jugador = (JPanel) panel2.getComponent(4);
            panel.add(jugador,BorderLayout.CENTER);
            casillas[positions[0]][positions[1]].add(panel);
            panel2.add(centro,BorderLayout.CENTER);
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

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
            repaint();
        }
    }
}
