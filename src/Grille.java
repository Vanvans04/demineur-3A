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

    public Case getCase(int i, int j) {
        if(i < 0 || j < 0 || i >= taille || j >= taille){
            return null;
        }
        int pos = (i*taille+j);
        return getCases().get(pos);
    }

    public int[] getPos(Case c) {
        int pos = cases.indexOf(c);
        if (pos == -1) {
            return null;
        }
        int i = pos / taille;
        int j = pos % taille;

        return new int[]{i, j};
    }

    public void placerBombes(int nbBombes, Case premierClic){
        Random random = getRandom();
        int taille = getTaille();
        ArrayList<Case> cases = getCases();
        ArrayList<Case> premieresCases = getVoisins(premierClic);
        premieresCases.add(premierClic);

        while(nbBombes > 0){
            int nombre = random.nextInt(taille * taille);
            Case c = cases.get(nombre);
            if(!c.isBombe() && !premieresCases.contains(c)){
                nbBombes--;
                c.setBombe(true);
            }
        }
    }

    public abstract ArrayList<Case> getVoisins(Case c);

    public ArrayList<Case> getCases(){
        return cases;
    }

    public int getTaille() {
        return taille;
    }

    public Random getRandom() {
        return random;
    }
}
