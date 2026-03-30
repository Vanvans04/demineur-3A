import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

public class MF extends JFrame implements Observer {
    private Jeu jeu;
    private JLabel[][] tab;
    private Icon flagIcon;
    private Icon bombeIcon;
    private int nbCases;
    private Image imageDrapeau;
    private Image imageMine;
    private Image[] imagesNombres = new Image[8];
    private ImageIcon[] iconesNombres = new ImageIcon[8];
    private Timer timer;
    private int tempsEcoule;
    private JLabel labelTimer;

    public MF(Jeu j){
        this.nbCases = j.getNbCases();
        this.jeu = j;
        tab = new JLabel[nbCases][nbCases];
        URL flagURL = getClass().getResource("/resources/flag.png");
        URL mineURL = getClass().getResource("/resources/mine.png");
        assert flagURL != null;
        assert mineURL != null;
        imageDrapeau = new ImageIcon(flagURL).getImage();
        imageMine = new ImageIcon(mineURL).getImage();
        flagIcon = new ImageIcon();
        bombeIcon = new ImageIcon();
        for (int i = 1; i <= 8; i++) {
            URL urlNombre = getClass().getResource("/resources/" + i + ".png");
            if (urlNombre != null) {
                imagesNombres[i - 1] = new ImageIcon(urlNombre).getImage();
                iconesNombres[i - 1] = new ImageIcon();
            }
        }
        flagIcon = new ImageIcon();
        bombeIcon = new ImageIcon();
        build(jeu.getTypeJeu());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!timer.isRunning() && !jeu.isPartiePerdue() && !jeu.isPartieGagnee() && tempsEcoule == 0) {
            if (jeu.isVisible(0, 0) || arg != null) {
                timer.start();
            }
        }
        for (int i = 0; i < nbCases; i++) {
            for (int j = 0; j < nbCases; j++) {

                if (jeu.isVisible(i, j)) {
                    if (jeu.isBombe(i, j)) {
                        tab[i][j].setBackground(Color.LIGHT_GRAY);
                        tab[i][j].setIcon(bombeIcon);
                    } else {
                        tab[i][j].setBackground(Color.LIGHT_GRAY);
                        int nbVP = jeu.getNbVP(i, j);
                        if (nbVP > 0) {
                            if (iconesNombres[nbVP - 1] != null && iconesNombres[nbVP - 1].getImage() != null) {
                                tab[i][j].setIcon(iconesNombres[nbVP - 1]);
                                tab[i][j].setText("");
                            }
                        }
                    }
                } else if (jeu.isDrapeau(i, j)) {
                    tab[i][j].setIcon(flagIcon);
                } else {
                    tab[i][j].setIcon(null);
                }
            }
        }
        this.repaint();
        if(jeu.isPartiePerdue() || jeu.isPartieGagnee()){
            timer.stop();
            TypeJeu typeJeu = jeu.getTypeJeu();
            boolean victoire = jeu.isPartieGagnee();
            EcranFin ecranFinWindow = new EcranFin(typeJeu, this, victoire);
            ecranFinWindow.setVisible(true);
        }
    }

    public void build(TypeJeu typeJeu) {
        tempsEcoule = 0;
        labelTimer = new JLabel("Temps : 000");
        labelTimer.setFont(new Font("Arial", Font.BOLD, 24));
        labelTimer.setForeground(Color.BLACK);
        timer = new Timer(1000, e -> {
            tempsEcoule++;
            labelTimer.setText("Temps : " + String.format("%03d", tempsEcoule));
        });
        JPanel panneauPrincipal = new JPanel(new BorderLayout());
        JPanel panneauHaut = new JPanel();
        panneauHaut.add(labelTimer);
        panneauPrincipal.add(panneauHaut, BorderLayout.NORTH);
        JPanel centreJeu = new JPanel(new GridBagLayout());

        JPanel panneauJeu = new JPanel() {
            @Override
            public void doLayout() {
                super.doLayout();

                if (typeJeu.equals(TypeJeu.HEXAGONAL)) {
                    int largeur = (int) (getWidth() / (1.0 + (nbCases - 1) * 0.75));
                    int hauteur = (int) (getHeight() / (nbCases + 0.5));

                    largeur -= largeur % 4;
                    hauteur -= hauteur % 2;

                    int largeurGrille = (int) (largeur + (nbCases - 1) * (largeur * 0.75));
                    int hauteurGrille = (nbCases * hauteur) + (hauteur / 2);
                    int offsetX = (getWidth() - largeurGrille) / 2;
                    int offsetY = (getHeight() - hauteurGrille) / 2;

                    for (int i = 0; i < nbCases; i++) {
                        for (int j = 0; j < nbCases; j++) {
                            if (tab[i][j] != null) {
                                int x = (int) (j * (largeur * 0.75));
                                int y = i * hauteur;
                                if (j % 2 == 1) {
                                    y += hauteur / 2;
                                }
                                tab[i][j].setBounds(offsetX + x, offsetY + y, largeur, hauteur);
                            }
                        }
                    }
                }
            }
        };
        panneauPrincipal.add(panneauJeu, BorderLayout.CENTER);
        int tailleJeu = jeu.getWindowSize() - 50;
        panneauJeu.setPreferredSize(new Dimension(tailleJeu, tailleJeu));
        centreJeu.add(panneauJeu);
        panneauPrincipal.add(centreJeu, BorderLayout.CENTER);
        if (typeJeu.equals(TypeJeu.CARRE)) {
            panneauJeu.setLayout(new GridLayout(nbCases, nbCases));
            for (int i = 0; i < nbCases; i++) {
                for (int j = 0; j < nbCases; j++) {
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
        else if (typeJeu.equals(TypeJeu.HEXAGONAL)) {
            panneauJeu.setLayout(null);
            for (int i = 0; i < nbCases; i++) {
                for (int j = 0; j < nbCases; j++) {
                    HexagoneLabel jp = new HexagoneLabel();
                    tab[i][j] = jp;
                    panneauJeu.add(jp);
                    ajouterEcouteurSouris(jp, i, j);
                }
            }
        }

        setContentPane(panneauPrincipal);

        centreJeu.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int largeur = centreJeu.getWidth();
                int hauteur = centreJeu.getHeight();
                int tailleCarre = Math.min(largeur, hauteur) - 20;
                if (tailleCarre > 0) {
                    panneauJeu.setPreferredSize(new Dimension(tailleCarre, tailleCarre));
                    mettreAJourTaille(tailleCarre);
                    centreJeu.revalidate();
                    repaint();
                }
            }
        });


        this.setMinimumSize(new Dimension(300, 300));
        this.setSize(jeu.getWindowSize(), jeu.getWindowSize() + 30);
        this.setLocationRelativeTo(null);
    }

    private void mettreAJourTaille(int tailleDispo) {

        int tailleBase = tailleDispo / nbCases;
        int tailleIcone = (int) (tailleBase * 0.7);
        if (tailleIcone <= 0) {
            tailleIcone = 1;
        }

        Image imgDrapeau = imageDrapeau.getScaledInstance(tailleIcone, tailleIcone, Image.SCALE_SMOOTH);
        Image imgMine = imageMine.getScaledInstance(tailleIcone, tailleIcone, Image.SCALE_SMOOTH);
        ((ImageIcon) flagIcon).setImage(imgDrapeau);
        ((ImageIcon) bombeIcon).setImage(imgMine);

        for (int i = 0; i < 8; i++) {
            if (imagesNombres[i] != null) {
                Image imgNum = imagesNombres[i].getScaledInstance(tailleIcone, tailleIcone, Image.SCALE_SMOOTH);
                iconesNombres[i].setImage(imgNum);
            }
        }
    }

    public void relancerPartie(){
        jeu.reset();
        for(int i = 0; i < nbCases; i++){
            for (int j = 0; j < nbCases; j++){
                tab[i][j].setIcon(null);
                tab[i][j].setText("");
                tab[i][j].setBackground(Color.white);
            }
        }
        timer.stop();
        tempsEcoule = 0;
        labelTimer.setText("Temps : 000");
        this.repaint();
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
