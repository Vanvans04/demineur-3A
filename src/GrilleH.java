import java.util.ArrayList;

public class GrilleH extends Grille{
    public GrilleH(int taille){
        super(taille);
    }

    @Override
    public ArrayList<Case> getVoisins(Case c){
        int[] posCase = getPos(c);
        int iVoisin, jVoisin;
        Case caseVoisine;
        int[] positionsI, positionsJ;
        ArrayList<Case> voisins = new ArrayList<>();

        if(posCase[1] % 2 == 0){
            // Haut, bas, haut-gauche, bas-gauche, haut-droit, bas-droit
            positionsI = new int[]{ -1, 1, -1,  0, -1,  0 };
            positionsJ = new int[]{  0, 0, -1, -1,  1,  1 };
        }
        else{
            // Haut, bas, haut-gauche, bas-gauche, haut-droit, bas-droit
            positionsI = new int[]{ -1, 1,  0,  1,  0,  1 };
            positionsJ = new int[]{  0, 0, -1, -1,  1,  1 };
        }

        for(int k=0; k<6; k++){
            iVoisin = posCase[0] + positionsI[k];
            jVoisin = posCase[1] + positionsJ[k];
            if (iVoisin >= 0 && jVoisin >= 0 && iVoisin < super.getTaille() && jVoisin < super.getTaille()) {
                caseVoisine = getCase(iVoisin, jVoisin);
                voisins.add(caseVoisine);
            }
        }
        return voisins;
    }
}