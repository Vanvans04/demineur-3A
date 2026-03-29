import javax.swing.*;
import java.awt.*;

public class EcranFin extends JFrame {
    public EcranFin(TypeJeu typeJeu, JFrame fenetrePrincipale, boolean victoire) {
        setTitle("Fin de la partie");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel texteFin = new JLabel("", SwingConstants.CENTER);
        texteFin.setFont(new Font("Arial", Font.BOLD, 25));
        if (victoire) {
            texteFin.setText("Victoire");
            texteFin.setForeground(Color.GREEN);
        }else {
            texteFin.setText("Game Over");
            texteFin.setForeground(Color.RED);
        }
        add(texteFin, BorderLayout.CENTER);
        JPanel panelBoutons = new JPanel();
        JButton restartButton = new JButton("Relancer");
        JButton quitButton = new JButton("Quitter");
        quitButton.addActionListener(e -> System.exit(0));
        restartButton.addActionListener(e -> {
            if (fenetrePrincipale != null) {
                MF fenetre = (MF) fenetrePrincipale;
                fenetre.relancerPartie();
            }
            this.dispose();
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        });
        panelBoutons.add(restartButton);
        panelBoutons.add(quitButton);
        add(panelBoutons,BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}