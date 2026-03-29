import javax.swing.*;

public class GestionnaireJeu {
    private static GestionnaireJeu instance;

    private GestionnaireJeu() {}

    public static GestionnaireJeu getInstance() {
        if (instance == null) {
            instance = new GestionnaireJeu();
        }
        return instance;
    }

    public void lancerJeu(TypeJeu typeJeu) {
        int nbCases = 10;
        int windowSize = 500;
        Jeu j = new Jeu(windowSize, nbCases, typeJeu);
        JFrame mf = new MF(j);
        j.init();
        j.addObserver((java.util.Observer) mf);
        mf.setSize(windowSize, windowSize);
        mf.setVisible(true);
    }
}
