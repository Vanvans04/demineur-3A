import java.util.ArrayList;
import java.util.Random;

public class GrilleC extends Grille{
    public GrilleC(int taille){
        super(taille);
    }

    @Override
    public Case getCase(int i, int j) {
        if(i < 0 || j < 0 || i >= super.getTaille() || j >= super.getTaille()){
            return null;
        }
        int pos = (i*super.getTaille()+j);
        return super.getCases().get(pos);
    }

    public int[] getPos(Case c) {
        int pos = super.getCases().indexOf(c);
        if (pos == -1) {
            return null;
        }
        int taille = super.getTaille();
        int i = pos / taille;
        int j = pos % taille;

        return new int[]{i, j};
    }

    @Override
    public ArrayList<Case> getVoisins(Case c){
        int[] posCase = getPos(c);
        int iVoisin, jVoisin;
        Case caseVoisine;
        ArrayList<Case> voisins = new ArrayList<>();
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                iVoisin = posCase[0]+i;
                jVoisin = posCase[1]+j;
                if (iVoisin >= 0 && jVoisin >= 0 && iVoisin < super.getTaille() && jVoisin < super.getTaille() && !(iVoisin == posCase[0] && jVoisin == posCase[1])) {
                    caseVoisine = getCase(iVoisin, jVoisin);
                    voisins.add(caseVoisine);
                }
            }
        }
        return voisins;
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
}
