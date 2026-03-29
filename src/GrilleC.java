import java.util.ArrayList;
import java.util.Random;

public class GrilleC extends Grille{
    public GrilleC(int taille){
        super(taille);
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
}
