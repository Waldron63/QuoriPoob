
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameType extends JFrame {
    private JPanel mainPanel;
    private JFrame ventana;
    private JComboBox<String> tipoJuego;
    private JButton jugar;
    public GameType(){
        prepareElements();
    }

    private void prepareElements(){
        ventana = new JFrame("Tipo de Juego");
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

    private void prepareGameMode(){
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(115, 10, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel tituloLabel = new JLabel("Tipo de Juego");
        tituloLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
        tituloLabel.setForeground(Color.WHITE);

        // Ajustes para centrar el título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa las dos columnas disponibles
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(tituloLabel, gbc);


        JLabel modoJuego = new JLabel("Tipo de Juego:");
        modoJuego.setFont(new Font("Times New Roman", Font.BOLD, 20));
        modoJuego.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(modoJuego, gbc);

        String[] tipos = {"Normal", "ContraReloj","Cronometrado"};
        tipoJuego = new JComboBox<>(tipos);
        gbc.gridx = 1;
        mainPanel.add(tipoJuego, gbc);

        jugar = new JButton("Jugar");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(jugar, gbc);
    }

    private void prepareGameModeActions(){
    }

    public static void main(String[] args) {
        GameType ventana = new GameType();
    }
}
