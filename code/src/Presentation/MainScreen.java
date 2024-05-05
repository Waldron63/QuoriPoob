//package Presentation;
//import Presentation.*;
//import Domain.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.ImageIcon;


/**
 * Clase Main de la visualizacion del proyecto, la interfaz inicial de QuoriPoob
 *
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class MainScreen extends JFrame{
    private JPanel mainPanel;
    private JFrame ventanaP;
    private QuoriPoob quorindior;

    /**
     * Constructor for objects of class MainScreen
     */
    public MainScreen()
    {
        super("QuoriPoob");
        prepareElements();
    }

    /**
     * Prepara y configura los elementos visuales de la interfaz gráfica.
     */
    private void prepareElements(){
        ventanaP = new JFrame("QuoriPoob");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width/2;
        int height = screenSize.height/2;
        ventanaP.setSize(width, height);
        ventanaP.setLocationRelativeTo(null);

        prepareMainMenu();
        ventanaP.add(mainPanel);
        ventanaP.setVisible(true);
    }

    /**
     * Prepara y configura los elementos visuales de el menu principal
     */
    private void prepareMainMenu(){
        mainPanel = new JPanel(new GridBagLayout());


        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel tituloLabel = new JLabel("QuoriPoob");
        tituloLabel.setFont(new Font("Times New Roman", Font.BOLD, 80));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        mainPanel.add(tituloLabel, constraints);

        //JUGAR
        JButton jugar = new JButton("Jugar");
        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(jugar,constraints);

        //SALIR
        JButton salir = new JButton("Salir del juego");
        constraints.gridx = 0;
        constraints.gridy = 3;
        mainPanel.add(salir, constraints);


        jugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaP.dispose();
                GameScreen.main(new String[] {});
                //pantallaOpciones = new ConfigScreen();
            }
        });

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

    /**
     * Metodo main para empezar a correr el sistema
     */
    public static void main(String[] args) {
        MainScreen mainScreen = new MainScreen();
    }
}