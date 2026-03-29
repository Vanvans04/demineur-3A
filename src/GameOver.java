import javax.swing.*;
import java.awt.*;

public class GameOver extends JFrame {
    public GameOver() {
        setTitle("Fin de la partie");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JLabel gameOverText = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverText.setForeground(Color.RED);
        gameOverText.setFont(new Font("Arial", Font.BOLD, 25));
        add(gameOverText, BorderLayout.CENTER);
        JPanel panelBoutons = new JPanel();
        JButton restartButton = new JButton("Restart");
        JButton quitButton = new JButton("Quitter");
        quitButton.addActionListener(e -> System.exit(0));
        restartButton.addActionListener(e -> {
            this.dispose();
            Menu.lancerJeu();
        });
        panelBoutons.add(restartButton);
        panelBoutons.add(quitButton);
        add(panelBoutons, BorderLayout.SOUTH);
        setVisible(true);

    }
}
