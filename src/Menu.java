import javax.swing.*;
import java.awt.*;
import java.util.Observer;

public class Menu extends JFrame {
    private JComboBox<String> difficulte;
    public Menu() {
        setTitle("Menu Démineur");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        String[] niveaux = {"Facile", "Moyen", "Difficile"};
        difficulte = new JComboBox<>(niveaux);
        difficulte.setSelectedIndex(1);
        JPanel panelDiff = new JPanel();
        panelDiff.add(new JLabel("Difficulté : "));
        panelDiff.add(difficulte);
        JButton playButtonC = new JButton("Jouer carré");
        JButton playButtonH = new JButton("Jouer hexagonal");
        JButton quitButton = new JButton("Quitter");
        playButtonC.addActionListener(e -> {
            lancerJeu(TypeJeu.CARRE);
            dispose();
        });
        playButtonH.addActionListener(e -> {
           lancerJeu(TypeJeu.HEXAGONAL);
            dispose();
        });
        quitButton.addActionListener(e -> System.exit(0));
        setLayout(new GridLayout(3, 1));
        JPanel panelJeux = new JPanel();
        panelJeux.setLayout(new GridLayout(1, 2));
        panelJeux.add(playButtonC);
        panelJeux.add(playButtonH);
        add(panelDiff);
        add(panelJeux);
        add(quitButton);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void lancerJeu(TypeJeu typeJeu) {
        int nbCases = 0;
        int windowSize = 500;
        double pourcentage = 0;
        int choix = difficulte.getSelectedIndex();
        switch (choix) {
            case 0:
                nbCases = Difficulte.FACILE.getNbCases();
                pourcentage = Difficulte.FACILE.getPourcentageBombes();
                break;
            case 1:
                nbCases = Difficulte.MOYEN.getNbCases();
                pourcentage = Difficulte.MOYEN.getPourcentageBombes();
                break;
            case 2:
                nbCases = Difficulte.DIFFICILE.getNbCases();
                pourcentage = Difficulte.DIFFICILE.getPourcentageBombes();
                break;
        }
        Jeu j = new Jeu(windowSize, nbCases, typeJeu, pourcentage);
        JFrame mf = new MF(j);
        j.init();
        j.addObserver((Observer) mf);
        mf.setSize(windowSize, windowSize);
        mf.setVisible(true);
    }
}