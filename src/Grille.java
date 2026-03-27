import java.util.ArrayList;
import java.util.Random;

public abstract class Grille {
    private ArrayList<Case> cases;
    private int taille;
    private Random random;

    public Grille(int taille){
        this.taille = taille;
        cases = new ArrayList<>();
        IStrategie isl = new ISL();
        for(int i = 0; i < (taille * taille); i++){
            cases.add(new Case(isl));
        }
        this.random = new Random();
    }

    public void calculerNbVP(Case c){
        int total = 0;
        ArrayList<Case> voisins = getVoisins(c);
        for(Case v : voisins){
            if(v.isBombe()){
                total++;
            }
        }
        c.setNbVP(total);
    }

    public abstract ArrayList<Case> getVoisins(Case c);

    public abstract Case getCase(int i, int j);

    public ArrayList<Case> getCases(){
        return cases;
    }

    public int getTaille() {
        return taille;
    }

    public abstract int[] getPos(Case c);

    public abstract void placerBombes(int nbBombes, Case premiereCase);

    public Random getRandom() {
        return random;
    }
}
