public class Case {
    private boolean visible, drapeau, bombe;
    private int nbVP;
    private IStrategie strategie;

    public Case(IStrategie strategie){
        visible = false;
        drapeau = false;
        bombe = false;
        this.strategie = strategie;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isDrapeau() {
        return drapeau;
    }

    public void setDrapeau(boolean drapeau) {
        this.drapeau = drapeau;
    }

    public boolean isBombe() {
        return bombe;
    }

    public void setBombe(boolean b){
        bombe = b;
    }

    public void setStrategie(IStrategie strategie){
        this.strategie = strategie;
    }

    public int getNbVP() {
        return nbVP;
    }

    public void setNbVP(int nbVP) {
        this.nbVP = nbVP;
    }

    public IStrategie getStrategie() {
        return strategie;
    }
}
