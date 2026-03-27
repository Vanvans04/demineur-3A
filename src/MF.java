import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

public class MF extends JFrame implements Observer {
    Jeu jeu;
    JLabel[][] tab;
    Icon flagIcon;
    int nbCases;

    public MF(Jeu j){
        nbCases = 10;
        tab = new JLabel[nbCases][nbCases];
        this.jeu = j;
        URL flagURL = getClass().getResource("/resources/flag.png");
        assert flagURL != null;
        ImageIcon iconImage = new ImageIcon(flagURL);
        Image image = iconImage.getImage().getScaledInstance((jeu.getWindowSize()/nbCases)-nbCases, (jeu.getWindowSize()/nbCases)-nbCases, Image.SCALE_SMOOTH);
        flagIcon = new ImageIcon(image);
        build(TypeJeu.HEXAGONAL);
    }

    @Override
    public void update(Observable o, Object arg) {
        for (int i = 0; i < nbCases; i++) {
            for (int j = 0; j < nbCases; j++) {

                if (jeu.isVisible(i, j)) {
                    if (jeu.isBombe(i, j)) {
                        tab[i][j].setBackground(Color.RED);
                        tab[i][j].setText("B");
                    } else {
                        tab[i][j].setBackground(Color.LIGHT_GRAY);
                        int nbVP = jeu.getNbVP(i, j);
                        if (nbVP > 0) {
                            tab[i][j].setText(String.valueOf(nbVP));
                        }
                    }
                } else if (jeu.isDrapeau(i, j)) {
                    tab[i][j].setIcon(flagIcon);
                }
            }
        }
        this.repaint();
    }

    public void build(TypeJeu typeJeu){
        JPanel panneauPrincipal = new JPanel(new GridBagLayout());
        JPanel panneauJeu = new JPanel();
        int tailleJeu = jeu.getWindowSize() - 50;
        panneauJeu.setPreferredSize(new Dimension(tailleJeu, tailleJeu));
        if(typeJeu.equals(TypeJeu.CARRE)){
            panneauJeu.setLayout(new GridLayout(nbCases,nbCases));
            for(int i=0;i<nbCases;i++){
                for(int j=0;j<nbCases;j++){
                    JLabel jp = new JLabel();
                    jp.setHorizontalAlignment(SwingConstants.CENTER);
                    jp.setVerticalAlignment(SwingConstants.CENTER);
                    jp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    jp.setOpaque(true);
                    tab[i][j] = jp;
                    panneauJeu.add(jp);
                    ajouterEcouteurSouris(jp, i, j);
                }
            }
        }
        else if(typeJeu.equals(TypeJeu.HEXAGONAL)){
            panneauJeu.setLayout(null);
            int largeur = (int)(tailleJeu / (1.0 + (nbCases - 1) * 0.75));
            int hauteur = (int)(tailleJeu / (nbCases + 0.5));
            largeur = largeur - (largeur % 4);
            hauteur = hauteur - (hauteur % 2);

            int largeurExacte = (int) ((nbCases - 1) * (largeur * 0.75) + largeur);
            int hauteurExacte = (nbCases * hauteur) + (hauteur / 2);
            panneauJeu.setPreferredSize(new Dimension(largeurExacte, hauteurExacte));

            for (int i = 0; i < nbCases; i++) {
                for (int j = 0; j < nbCases; j++) {

                    HexagoneLabel jp = new HexagoneLabel();
                    tab[i][j] = jp;
                    panneauJeu.add(jp);

                    int x = j * (largeur * 3 / 4);
                    int y = i * hauteur;
                    if (j % 2 == 1) {
                        y += hauteur / 2;
                    }
                    jp.setBounds(x, y, largeur, hauteur);
                    ajouterEcouteurSouris(jp, i, j);
                }
            }
        }
        panneauPrincipal.add(panneauJeu);
        setContentPane(panneauPrincipal);

        pack();

    }

    private void ajouterEcouteurSouris(JLabel caseJeu, int i, int j){
        caseJeu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)){
                    jeu.setLeft(i, j);
                }
                else if(SwingUtilities.isRightMouseButton(e)){
                    jeu.setRight(i, j);
                }
            }
        });
    }
}
