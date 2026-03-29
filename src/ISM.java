import javax.swing.*;

public class ISM implements IStrategie {
    @Override
    public void decouvrir(Case c, Grille grille) {
        for (int i = 0; i < grille.getTaille(); i++) {
            for (int j = 0; j < grille.getTaille(); j++) {
                grille.getCase(i, j).setVisible(true);
            }
        }
    }
}
