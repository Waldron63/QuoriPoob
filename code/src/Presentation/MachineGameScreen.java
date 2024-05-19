
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * Clase principal para crear la ventana de configuración del juego
 */
public class MachineGameScreen extends JFrame {
    private JPanel mainPanel; // Panel principal de la ventana
    private JFrame ventana; // Marco de la ventana
    private JComboBox<String> maquina; //ComboBox para seleccionar el tipo de máquina
    private JButton jugar; // Botón para guardar la configuración
    private Color color1; // Color seleccionado para el jugador
    private JTextField nombreJugador1; // Campo de texto para el nombre del jugador
    private String difficultySelect;

    /**
     * Metodo principal para ejecutar la aplicación
     * @param args
     */
    public static void main(String[] args) {
        MachineGameScreen ventana = new MachineGameScreen("Normal");
    }

    /***
     *  Constructor de la clase que inicializa los elementos de la interfaz
     */
    public MachineGameScreen(String difSelect) {
        prepareElements();
        difficultySelect = difSelect;
    }

    /***
     * Metodo para preparar los elementos de la interfaz
     */
    private void prepareElements() {
        ventana = new JFrame("Configuracion de Juego");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        ventana.setSize(width, height);
        ventana.setLocationRelativeTo(null);
        prepareGameMode();
        prepareGameModeActions();
        ventana.add(mainPanel);
        ventana.setVisible(true);
    }

    /***
     * Metodo para preparar los componentes del modo de juego
     */
    private void prepareGameMode() {
        // Crear el panel principal con un GridBagLayout
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(115, 10, 25));
        GridBagConstraints gbc = new GridBagConstraints();
       // Titulo de la configuracion del juego
        JLabel tituloLabel = new JLabel("Configuración de Juego");
        tituloLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
        tituloLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(tituloLabel, gbc);

        // Nombre del jugador 1
        JLabel labelNombre1 = new JLabel("Nombre del Jugador:");
        labelNombre1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        labelNombre1.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(labelNombre1, gbc);

        nombreJugador1 = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(nombreJugador1, gbc);

        // Color del jugador 1
        JLabel labelColor1 = new JLabel("Color del Jugador :");
        labelColor1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        labelColor1.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(labelColor1, gbc);
        //
        JButton btnColor1 = new JButton("Seleccionar Color");
        btnColor1.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(btnColor1, gbc);

        // Tipo de Juego
        JLabel modoJuego = new JLabel("Tipo de Máquina:");
        modoJuego.setFont(new Font("Times New Roman", Font.BOLD, 20));
        modoJuego.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(modoJuego, gbc);
        //
        String[] tiposMaquina = {"Principiante", "Intermedio", "Avanzado"};
        maquina = new JComboBox<>(tiposMaquina);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(maquina, gbc);

        // Botón para guardar la configuración
        jugar = new JButton("Guardar");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(jugar, gbc);
    }

    /***
     * Metodo para preparar las acciones de los elementos
     */
    private void prepareGameModeActions() {
        //color jugador
        JButton btnColor1 = (JButton) mainPanel.getComponent(4);
        btnColor1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                color1 = JColorChooser.showDialog(null, "Selecciona un color", Color.BLACK);
            }
        });
        //boton jugar
        JButton jugar = (JButton) mainPanel.getComponent(7);
        jugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre1 = nombreJugador1.getText();
                Color colorJugador1 = color1;
                String typeMachine = (String) maquina.getSelectedItem();
                if (!nombre1.isEmpty() && colorJugador1 != null) {
                    GameScreen gameScreen = new GameScreen(difficultySelect);
                    gameScreen.updatePlayer1(nombre1, colorJugador1);
                    gameScreen.updateMachine(typeMachine);
                    gameScreen.setVisible(true);
                    ventana.dispose();
                }
            }
        });
    }
}