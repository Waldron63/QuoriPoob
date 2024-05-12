
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserGameScreen extends JFrame {
    private JPanel mainPanel;
    private JFrame ventanaP;
    private QuoriPoob quorindior;

    private Color color1;
    private Color color2;
    private JTextField nombreJugador1;
    private JTextField nombreJugador2;

    /**
     * Constructor for objects of class
     */
    public UserGameScreen(){
        prepareElements();
    }

    private void prepareElements(){
        ventanaP = new JFrame("Configuracion del Jugador");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width/2;
        int height = screenSize.height/2;
        ventanaP.setSize(width, height);
        ventanaP.setLocationRelativeTo(null);

        prepareConfigUser();
        ventanaP.add(mainPanel);
        ventanaP.setVisible(true);
    }

    private void prepareConfigUser(){
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(115, 10, 25));
        GridBagConstraints gbc = new GridBagConstraints();


        JLabel tituloLabel = new JLabel("Configuración de Usuario");
        tituloLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
        tituloLabel.setForeground(Color.WHITE);
        // Ajustes para centrar el título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa las dos columnas disponibles
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(tituloLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Nombre del jugador 1
        JLabel labelNombre1 = new JLabel("Nombre Jugador 1:");
        labelNombre1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        labelNombre1.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(labelNombre1, gbc);


        nombreJugador1 = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(nombreJugador1, gbc);

        // Color del jugador 1
        JLabel labelColor1 = new JLabel("Color Jugador 1:");
        labelColor1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        labelColor1.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(labelColor1, gbc);

        JButton btnColor1 = new JButton("Seleccionar Color");
        btnColor1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                color1 = JColorChooser.showDialog(null, "Selecciona un color", Color.BLACK);
            }
        });
        btnColor1.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(btnColor1, gbc);

        // Nombre del jugador 2
        JLabel labelNombre2 = new JLabel("Nombre Jugador 2:");
        labelNombre2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        labelNombre2.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(labelNombre2, gbc);

        nombreJugador2 = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(nombreJugador2, gbc);

        // Color del jugador 2
        JLabel labelColor2 = new JLabel("Color Jugador 2:");
        labelColor2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        labelColor2.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(labelColor2, gbc);
        JButton btnColor2 = new JButton("Seleccionar Color");
        btnColor2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                color2 = JColorChooser.showDialog(null, "Selecciona un color", Color.RED);
            }
        });
        btnColor2.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(btnColor2, gbc);

        JButton guardarButton = new JButton("Guardar");
        guardarButton.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(guardarButton, gbc);

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre1 = nombreJugador1.getText();
                String nombre2 = nombreJugador2.getText();
                Color colorJugador1 = color1;
                Color colorJugador2 = color2;
                if (!nombre1.isEmpty() && !nombre2.isEmpty() && colorJugador1 != null && colorJugador2 != null) {
                    GameScreen gameScreen = new GameScreen();
                    gameScreen.updatePlayer1(nombre1, colorJugador1);
                    gameScreen.updatePlayer2(nombre2, colorJugador2);
                    gameScreen.setVisible(true);
                }
                ventanaP.dispose();
            }
        });
    }

    public static void main(String[] args) {
        UserGameScreen ventana = new UserGameScreen();
    }
}
