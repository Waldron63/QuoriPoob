
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * Clase QuantityScreen para crear la ventana de configuración
 */
public class QuantityScreen extends JFrame {
    private JPanel mainPanel; // Panel principal de la ventana
    private JFrame ventana;// Marco de la ventana
    private JComboBox<String> cantidadMuros; // ComboBox para seleccionar la cantidad de muros
    private JComboBox<String> casiTeleport; // ComboBox para seleccionar la cantidad de casillas Teleport
    private JComboBox<String> casiReturn; // ComboBox para seleccionar la cantidad de casillas Return
    private JComboBox<String> casiDouble; // ComboBox para seleccionar la cantidad de casillas Double
    private JButton jugar;  // Botón para iniciar el juego

    /***
     * Constructor de la clase que inicializa los elementos de la interfaz
     */
    public QuantityScreen(){
        prepareElements();
    }

    /***
     * Metodo para preparar los elementos de la interfaz
     */
    private void prepareElements(){
        ventana = new JFrame("QuantityScreen");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width/2;
        int height = screenSize.height/2;
        ventana.setSize(width, height);
        ventana.setLocationRelativeTo(null);
        prepareQuantityScreen();
        prepareQuantityScreenActions();
        ventana.add(mainPanel);
        ventana.setVisible(true);
    }


    /***
     * Metodo para preparar los componentes de la pantalla de Quabtity
     */
    private void prepareQuantityScreen() {
        // Crear el panel principal con un GridBagLayout
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(115, 10, 25));
        GridBagConstraints gbc = new GridBagConstraints();

        // Titulo de la configuración
        JLabel tituloLabel = new JLabel("Configuración de Muros y Casillas");
        tituloLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
        tituloLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(tituloLabel, gbc);

        // Cantidad de Muros
        JLabel modoJuego = new JLabel("Cantidad de Muros:");
        modoJuego.setFont(new Font("Times New Roman", Font.BOLD, 20));
        modoJuego.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(modoJuego, gbc);

        String[] muros = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        cantidadMuros = new JComboBox<>(muros);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(cantidadMuros, gbc);

        // Cantidad de casillas Teleport
        JLabel teleport = new JLabel("Cantidad de casillas Teleport:");
        teleport.setFont(new Font("Times New Roman", Font.BOLD, 20));
        teleport.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(teleport, gbc);

        String[] casTeleport = {"1", "2", "3", "4", "5", "6"};
        casiTeleport = new JComboBox<>(casTeleport);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(casiTeleport, gbc);

        // Cantidad de casillas Return
        JLabel returnn = new JLabel("Cantidad de casillas Return:");
        returnn.setFont(new Font("Times New Roman", Font.BOLD, 20));
        returnn.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(returnn, gbc);

        String[] casReturn = {"1", "2", "3", "4", "5", "6"};
        casiReturn = new JComboBox<>(casReturn);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(casiReturn, gbc);

        // Cantidad de casillas Double
        JLabel doublee = new JLabel("Cantidad de casillas Double:");
        doublee.setFont(new Font("Times New Roman", Font.BOLD, 20));
        doublee.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(doublee, gbc);

        String[] casDouble = {"1", "2", "3", "4", "5", "6"};
        casiDouble = new JComboBox<>(casDouble);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(casiDouble, gbc);

        // Boton para iniciar el juego
        jugar = new JButton("Jugar");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(jugar, gbc);
    }

    /***
     *  Metodo para preparar las acciones de los elementos
     */
    private void prepareQuantityScreenActions(){
    }

    /***
     * Metodo principal para ejecutar la aplicación
     * @param args
     */
    public static void main(String[] args) {
        QuantityScreen ventana = new QuantityScreen();
    }
}
