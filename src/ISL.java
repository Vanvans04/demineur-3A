import java.util.ArrayList;

public class ISL implements IStrategie {
    @Override
    public void decouvrir(Case c, Grille grille){
        c.setVisible(true);
        if (c.getNbVP() == 0) {
            ArrayList<Case> voisins = grille.getVoisins(c);
            for(Case v : voisins){
                if(!v.isVisible() && !v.isDrapeau()){
                    v.getStrategie().decouvrir(v, grille);
                }
            }
        }
    }
}