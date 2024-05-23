
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserGameScreen extends JFrame {
    private JPanel mainPanel; //panel principal con todas las configuraciones
    private JFrame ventanaP; //ventana emergente principal

    private Color color1; //color seleccionado por el jugador 1
    private Color color2; //color seleccionado por el jugador 2
    private JTextField nombreJugador1; //nombre del jugador 1
    private JTextField nombreJugador2; //nombre del jugador 2
    private String difficultySelect; //dificultad que desea aplicar para el juego
    private int[] typeCantWalls; //cantidad de muros especiales seleccionados por el usuario
    private int[] typeCantBoxes; //cantidad de casillas especiales seleccionados por el usuario

    /**
     * Metodo principal para ejecutar esta ventana
     * @param args
     */
    public static void main(String[] args) {
        UserGameScreen ventana = new UserGameScreen("Normal", new int[] {10,0,0,0}, new int[] {27,0,0,0});
    }

    /**
     * Constructor for objects of class
     * @param difSelect, indica la dificultad del juego que selecciono el jugador anteriormente
     */
    public UserGameScreen(String difSelect, int[] newTypeWalls, int[] newTypeBoxes){
        prepareElements();
        prepareUserGameActions();
        difficultySelect = difSelect;
        typeCantBoxes = newTypeBoxes;
        typeCantWalls = newTypeWalls;
    }

    /**
     * prepara los elementos para la interfaz de este panel
     */
    private void prepareElements(){
        ventanaP = new JFrame("Configuracion del Jugador");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.6);
        int height = (int) (screenSize.height * 0.6);
        ventanaP.setSize(width, height);
        ventanaP.setLocationRelativeTo(null);

        prepareConfigUser();
        ventanaP.add(mainPanel);
        ventanaP.setVisible(true);
    }

    /**
     * prepara los elementos para la configyracion de los 2 usuarios
     */
    private void prepareConfigUser(){
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(115, 10, 25));
        GridBagConstraints gbc = new GridBagConstraints();


        JLabel tituloLabel = new JLabel("Configuración de Usuario");
        tituloLabel.setFont(new Font("Times New Roman", Font.BOLD, 55));
        tituloLabel.setForeground(Color.WHITE);
        // Ajustes para centrar el título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa las dos columnas disponibles
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(tituloLabel, gbc);
        //
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Nombre del jugador 1
        JLabel labelNombre1 = new JLabel("Nombre Jugador 1:");
        labelNombre1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        labelNombre1.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(labelNombre1, gbc);
        //
        nombreJugador1 = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(nombreJugador1, gbc);

        // Color del jugador 1
        JLabel labelColor1 = new JLabel("Color Jugador 1:");
        labelColor1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
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

        // Nombre del jugador 2
        JLabel labelNombre2 = new JLabel("Nombre Jugador 2:");
        labelNombre2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        labelNombre2.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(labelNombre2, gbc);
        //
        nombreJugador2 = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(nombreJugador2, gbc);

        // Color del jugador 2
        JLabel labelColor2 = new JLabel("Color Jugador 2:");
        labelColor2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        labelColor2.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(labelColor2, gbc);
        //
        JButton btnColor2 = new JButton("Seleccionar Color");
        btnColor2.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(btnColor2, gbc);

        //Boton para guardar
        JButton guardarButton = new JButton("Guardar");
        guardarButton.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(guardarButton, gbc);
    }

    /**
     * prepara los listener para los colores de los jugadores y el boton guardar
     */
    private void prepareUserGameActions(){
        //Color 1er usuario
        JButton btnColor1 = (JButton) mainPanel.getComponent(4);
        btnColor1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                color1 = JColorChooser.showDialog(null, "Selecciona un color", Color.BLACK);
            }
        });
        //Color 2do jugador
        JButton btnColor2 = (JButton) mainPanel.getComponent(8);
        btnColor2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                color2 = JColorChooser.showDialog(null, "Selecciona un color", Color.RED);
            }
        });
        //guardar
        JButton guardar = (JButton) mainPanel.getComponent(9);
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre1 = nombreJugador1.getText();
                String nombre2 = nombreJugador2.getText();
                Color colorJugador1 = color1;
                Color colorJugador2 = color2;
                if (!nombre1.isEmpty() && !nombre2.isEmpty() && colorJugador1 != null && colorJugador2 != null) {
                    GameScreen gameScreen = new GameScreen(difficultySelect, typeCantBoxes);
                    gameScreen.updatePlayer1(nombre1, colorJugador1);
                    gameScreen.updatePlayer2(nombre2, colorJugador2);
                    gameScreen.setTypesSpecials(typeCantWalls);
                    gameScreen.setVisible(true);
                    ventanaP.dispose();
                }
            }
        });

    }
}
