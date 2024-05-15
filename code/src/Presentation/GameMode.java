
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMode extends JFrame {
    private JPanel mainPanel;
    private JFrame ventana;
    private JComboBox<String> modoJugador;
    private JComboBox<String> maquina;
    private JButton guardar;
    public GameMode(){
        prepareElements();
    }

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

    private void prepareGameMode(){
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(115, 10, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel tituloLabel = new JLabel("Modo de Juego");
        tituloLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
        tituloLabel.setForeground(Color.WHITE);

        // Ajustes para centrar el título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa las dos columnas disponibles
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(tituloLabel, gbc);


        JLabel modoJuego = new JLabel("Modo de Juego:");
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

        JLabel tipoMaquina = new JLabel("Tipo de Maquina:");
        tipoMaquina.setFont(new Font("Times New Roman", Font.BOLD, 20));
        tipoMaquina.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(tipoMaquina, gbc);

        String[] tiposMaquina = {"Principiante", "Intermedio", "Avanzado"};
        maquina = new JComboBox<>(tiposMaquina);
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(maquina, gbc);

        guardar = new JButton("Guardar");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(guardar, gbc);
    }

    private void prepareGameModeActions(){
    }

    public static void main(String[] args) {
        GameMode ventana = new GameMode();
    }
}
