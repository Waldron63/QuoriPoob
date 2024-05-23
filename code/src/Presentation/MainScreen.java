//package Presentation;
//import Presentation.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


/**
 * Clase Main de la visualizacion del proyecto, la interfaz inicial de QuoriPoob
 *
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class MainScreen extends JFrame{
    private JPanel mainPanel; //Panel que tiene toda la visualizacion
    private JFrame ventanaP; //ventana emergente principal

    /**
     * Metodo main para empezar a correr el sistema
     */
    public static void main(String[] args) {
        MainScreen mainScreen = new MainScreen();
    }

    /**
     * Constructor for objects of class MainScreen
     */
    public MainScreen(){
        super("QuoriPoob");
        prepareElements();
        prepareMenuActions();
    }

    /**
     * Prepara y configura los elementos visuales de la interfaz gráfica.
     */
    private void prepareElements(){
        ventanaP = new JFrame("QuoriPoob");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.6);
        int height = (int) (screenSize.height * 0.6);
        ventanaP.setSize(width, height);
        ventanaP.setLocationRelativeTo(null);

        prepareMainMenu();
        ventanaP.add(mainPanel);
        ventanaP.setVisible(true);
    }

    /**
     * Prepara y configura los elementos visuales de el menu principal
     */
    private void prepareMainMenu() {
        //MAINPANEL
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(115, 10, 25));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        //TITULO
        JLabel tituloLabel = new JLabel("QuoriPoob");
        tituloLabel.setFont(new Font("Times New Roman", Font.BOLD, 80));
        tituloLabel.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        mainPanel.add(tituloLabel, constraints);

        //JUGAR
        JButton jugar = new JButton("Jugar");
        jugar.setBackground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(jugar, constraints);

        //SALIR
        JButton salir = new JButton("Salir del juego");
        salir.setBackground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 3;
        mainPanel.add(salir, constraints);
    }

    /**
     * Listener de los botones del main: jugar y salir
     */
    private void prepareMenuActions(){
        //jugar
        JButton jugar = (JButton) mainPanel.getComponent(1);
        jugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaP.dispose();
                QuantityScreen qs = new QuantityScreen();
            }
        });

        //salir
        JButton salir = (JButton) mainPanel.getComponent(2);
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitGame();
            }
        });
    }

    /**
     * Configura el boton de salir del menu principal
     */
    private void exitGame(){
        int option = JOptionPane.showConfirmDialog(this,"Seguro que deseas salir?","Cerrar Juego",JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }
}
