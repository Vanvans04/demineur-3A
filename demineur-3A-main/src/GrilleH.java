import java.util.ArrayList;

public class GrilleH extends Grille{
    public GrilleH(int taille){
        super(taille);
    }

    @Override
    public Case getCase(int i, int j) {
        // A modifier
        return null;
    }

    public int[] getPos(Case c) {
        // A modifier
        return null;
    }

    @Override
    public ArrayList<Case> getVoisins(Case c){
        return new ArrayList<Case>(); // Temporaire
    }

    @Override
    public void placerBombes(int nbBombes, Case premiereCase) {
        // A remplir
    }

}