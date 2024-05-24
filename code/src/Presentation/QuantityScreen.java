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
    private JComboBox<String> cantidadMurosLargo; //ComboBox para seleccionar la cantidad de muros largos
    private JComboBox<String> cantidadMurosAliados; //ComboBox para seleccionar la cantidad de muros aliados
    private JComboBox<String> cantidadMurosTemporales; //ComboBox para seleccionar la cantidad de muros temporales
    private JComboBox<String> casiTeleport; //ComboBox para seleccionar la cantidad de casillas Teleport
    private JComboBox<String> casiReturn; //ComboBox para seleccionar la cantidad de casillas Return
    private JComboBox<String> casiDouble; //ComboBox para seleccionar la cantidad de casillas Double

    /***
     * Metodo principal para ejecutar la aplicación
     * @param args
     */
    public static void main(String[] args) {
        QuantityScreen ventana = new QuantityScreen();
    }

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
        int width = (int) (screenSize.width * 0.6);
        int height = (int) (screenSize.height * 0.6);
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

        // Cantidad de Muros largo
        JLabel modoJuego = new JLabel("Cantidad de Muro Largo:");
        modoJuego.setFont(new Font("Times New Roman", Font.BOLD, 20));
        modoJuego.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(modoJuego, gbc);

        String[] muros = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        cantidadMurosLargo = new JComboBox<>(muros);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(cantidadMurosLargo, gbc);

        // Cantidad de Muros aliados
        JLabel modoJuego1 = new JLabel("Cantidad de Muro Aliado:");
        modoJuego1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        modoJuego1.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(modoJuego1, gbc);

        String[] muros1 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        cantidadMurosAliados = new JComboBox<>(muros1);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(cantidadMurosAliados, gbc);

        // Cantidad de Muros temporal
        JLabel modoJuego2 = new JLabel("Cantidad de Muro Temporal:");
        modoJuego2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        modoJuego2.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(modoJuego2, gbc);

        String[] muros2 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        cantidadMurosTemporales = new JComboBox<>(muros2);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(cantidadMurosTemporales, gbc);

        // Cantidad de casillas Teleport
        JLabel teleport = new JLabel("Cantidad de casillas Teleport:");
        teleport.setFont(new Font("Times New Roman", Font.BOLD, 20));
        teleport.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(teleport, gbc);

        String[] casTeleport = {"0", "0", "1", "2", "3", "4", "5", "6","7", "8", "9"};
        casiTeleport = new JComboBox<>(casTeleport);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(casiTeleport, gbc);

        // Cantidad de casillas Return
        JLabel returnn = new JLabel("Cantidad de casillas Return:");
        returnn.setFont(new Font("Times New Roman", Font.BOLD, 20));
        returnn.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(returnn, gbc);

        String[] casReturn = {"0", "1", "2", "3", "4", "5", "6","7", "8", "9"};
        casiReturn = new JComboBox<>(casReturn);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(casiReturn, gbc);

        // Cantidad de casillas Double
        JLabel doublee = new JLabel("Cantidad de casillas Double:");
        doublee.setFont(new Font("Times New Roman", Font.BOLD, 20));
        doublee.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(doublee, gbc);

        String[] casDouble = {"0", "1", "2", "3", "4", "5", "6","7", "8", "9"};
        casiDouble = new JComboBox<>(casDouble);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(casiDouble, gbc);

        // Boton para iniciar el juego
        JButton continuar = new JButton("Continuar");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(continuar, gbc);
    }

    /***
     *  Metodo para preparar las acciones de los elementos
     */
    private void prepareQuantityScreenActions(){
        JButton continuar = (JButton) mainPanel.getComponent(13);
        continuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int mLargo = Integer.parseInt((String) cantidadMurosLargo.getSelectedItem());
                int mAliado = Integer.parseInt((String) cantidadMurosAliados.getSelectedItem());
                int mTemporal = Integer.parseInt((String) cantidadMurosTemporales.getSelectedItem());
                int cDoble = Integer.parseInt((String) casiDouble.getSelectedItem());
                int cRetorno = Integer.parseInt((String) casiReturn.getSelectedItem());
                int cTeleport = Integer.parseInt((String) casiTeleport.getSelectedItem());
                if (mLargo+mAliado+mTemporal <= 10 && cDoble+cRetorno+cTeleport <= 27){
                    int[] cantTypeWalls = new int[] {10 - (mLargo+mAliado+mTemporal), mTemporal, mLargo, mAliado};
                    int[] cantTypeBoxes = new int[] {cTeleport, cRetorno, cDoble};
                    ventana.dispose();
                    GameMode gm = new GameMode(cantTypeWalls, cantTypeBoxes);
                }else{
                    JOptionPane.showMessageDialog(null,"Debes colocar un maximo de 10 muros" +
                            " especiales y 27 casillas especiales", "parametros", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}
