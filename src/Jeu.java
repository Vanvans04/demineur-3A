import java.util.ArrayList;
import java.util.Observable;

public class Jeu extends Observable {
    private int i,j, windowSize, nbCases;
    private Grille grille;
    private boolean premierCoup;
    private TypeJeu typeJeu;
    private boolean partiePerdue, partieGagnee;
    private double pourcentage;

    public Jeu(int windowSize, int nbCases, TypeJeu typeJeu, double pourcentage) {
        this.windowSize = windowSize;
        this.nbCases = nbCases;
        this.premierCoup = true;
        this.typeJeu = typeJeu;
        this.partiePerdue = false;
        this.partieGagnee = false;
        this.pourcentage = pourcentage;
    }

    public void init(){
        if(this.typeJeu.equals(TypeJeu.CARRE)){
            this.grille = new GrilleC(nbCases);
        }
        else if(this.typeJeu.equals(TypeJeu.HEXAGONAL)){
            this.grille = new GrilleH(nbCases);
        }
    }

    public void reset() {
        this.premierCoup = true;
        this.partiePerdue = false;
        this.partieGagnee = false;
        this.init();
        setChanged();
        notifyObservers();
    }

    public void updateCase(int i, int j, Coup coup){
        this.i = i;
        this.j = j;
        Case c = grille.getCase(i,j);

        if(verifierCase(c, coup)){
            if(coup.equals(Coup.JOUE)) {
                if (premierCoup) {
                    premierCoup = false;
                    int taille = grille.getTaille();
                    grille.placerBombes((int) ((taille * taille) * 0.15), c);

                    for (Case caseVP : grille.getCases()) {
                        grille.calculerNbVP(caseVP);
                        if (!caseVP.isBombe()) {
                            caseVP.setStrategie(new ISL());
                        } else {
                            caseVP.setStrategie(new ISM());
                        }
                    }
                }
                decouvrirCase(c);
            }
            else if (coup.equals(Coup.DRAPEAU)) {
                c.setDrapeau(!c.isDrapeau());
            }
            setChanged();
            notifyObservers(coup);
        }
    }

    public boolean verifierCase(Case caseVerif, Coup coup){
        if (caseVerif.isVisible()) {
            return false;
        }

        if (coup.equals(Coup.JOUE)) {
            return !caseVerif.isDrapeau();
        }
        else return coup.equals(Coup.DRAPEAU);
    }

    public void decouvrirCase(Case c){
        c.getStrategie().decouvrir(c, grille);
        if(c.isBombe()){
            partiePerdue = true;
        }
        else{
            verifierVictoire();
        }
    }

    private void verifierVictoire() {
        boolean gagne = true;
        for (Case c : grille.getCases()) {
            if (!c.isBombe() && !c.isVisible()) {
                gagne = false;
                break;
            }
        }
        if (gagne) {
            this.partieGagnee = true;
        }
    }

    public void setLeft(int i, int j){
        updateCase(i,j,Coup.JOUE);
    }

    public void setRight(int i, int j){
        updateCase(i,j,Coup.DRAPEAU);
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public int getNbCases() {
        return nbCases;
    }

    public boolean isBombe(int i, int j) {
        return grille.getCase(i, j).isBombe();
    }

    public int getNbVP(int i, int j) {
        return grille.getCase(i, j).getNbVP();
    }

    public boolean isVisible(int i, int j) {
        return grille.getCase(i, j).isVisible();
    }

    public boolean isDrapeau(int i, int j) {
        return grille.getCase(i, j).isDrapeau();
    }

    public TypeJeu getTypeJeu() {
        return typeJeu;
    }

    public boolean isPartiePerdue() {
        return partiePerdue;
    }
}
