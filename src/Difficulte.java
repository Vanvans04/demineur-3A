public enum Difficulte {
    FACILE(10, 0.10),
    MOYEN(16, 0.15),
    DIFFICILE(20, 0.20);

    private final int nbCases;
    private final double pourcentageBombes;

    Difficulte(int nbCases, double pourcentageBombes) {
        this.nbCases = nbCases;
        this.pourcentageBombes = pourcentageBombes;
    }

    public int getNbCases() {
        return nbCases;
    }

    public double getPourcentageBombes() {
        return pourcentageBombes;
    }
}