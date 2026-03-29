import javax.swing.*;
import java.awt.*;
import java.util.Observer;

public class Menu extends JFrame {
    public Menu() {
        setTitle("Menu Démineur");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JButton playButton = new JButton("Jouer");
        JButton quitButton = new JButton("Quitter");
        playButton.addActionListener(e -> {
            lancerJeu();
            dispose();
        });
        quitButton.addActionListener(e -> System.exit(0));
        setLayout(new GridLayout(2, 1));
        add(playButton);
        add(quitButton);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void lancerJeu() {
        int nbCases = 10;
        int windowSize = 500;

        Jeu j = new Jeu(windowSize, nbCases);
        JFrame mf = new MF(j);
        j.init();
        j.addObserver((java.util.Observer) mf);

        mf.setSize(windowSize, windowSize);
        mf.setVisible(true);
    }
}
