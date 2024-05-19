import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase GameMode para crear la ventana de modo de juego
 */
public class GameMode extends JFrame {
    private JPanel mainPanel;  // Panel principal de la ventana
    private JFrame ventana;// Marco de la ventana
    private JComboBox<String> modoJugador; // ComboBox para seleccionar el modo de juego
    private JComboBox<String> tipoJuego; // ComboBox para seleccionar el tipo de juego
    private JButton guardar; // Boton para guardar la configuración

    /***
     * Metodo principal para ejecutar la aplicación
     * @param args
     */
    public static void main(String[] args) {
        GameMode ventana = new GameMode();
    }

    /***
     * Constructor de la clase que inicializa los elementos de la interfaz
     */
    public GameMode(){
        prepareElements();
    }

    /***
     * Metodo para preparar los elementos de la interfaz
     */
    private void prepareElements(){
        ventana = new JFrame("Modo de Juego");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width/2;
        int height = screenSize.height/2;
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
    private void prepareGameMode(){
        // Crear el panel principal con un GridBagLayout
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(115, 10, 25));
        GridBagConstraints gbc = new GridBagConstraints();

        // Título del modo de juego
        JLabel tituloLabel = new JLabel("Modo de Juego");
        tituloLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
        tituloLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa las dos columnas disponibles
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(tituloLabel, gbc);

        // Etiqueta y ComboBox para seleccionar el modo de juego
        JLabel modoJuego = new JLabel("Tipo de Juego:");
        modoJuego.setFont(new Font("Times New Roman", Font.BOLD, 20));
        modoJuego.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(modoJuego, gbc);

        String[] modos = {"Jugador vs Jugador", "Jugador vs Maquina"};
        modoJugador = new JComboBox<>(modos);
        gbc.gridx = 1;
        mainPanel.add(modoJugador, gbc);

        // Etiqueta y ComboBox para seleccionar el tipo de juego
        JLabel modoJuego1 = new JLabel("Dificultad de Juego:");
        modoJuego1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        modoJuego1.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(modoJuego1, gbc);

        String[] tipos = {"Normal", "ContraReloj","Cronometrado"};
        tipoJuego = new JComboBox<>(tipos);
        gbc.gridx = 1;
        mainPanel.add(tipoJuego, gbc);

        // Boton para guardar la configuración
        guardar = new JButton("Continuar");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(guardar, gbc);
    }

    /***
     * Metodo para preparar las acciones de los elementos
     */
    private void prepareGameModeActions(){
        JButton continuar = (JButton) mainPanel.getComponent(5);
        continuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modoSelect = (String) modoJugador.getSelectedItem();
                String difSelect = (String) tipoJuego.getSelectedItem();
                ventana.dispose();
                if (modoSelect == "Jugador vs Jugador"){
                    UserGameScreen ugs = new UserGameScreen(difSelect);
                }else{
                    MachineGameScreen mgs = new MachineGameScreen(difSelect);
                }
            }
        });
    }
}