public class ISM implements IStrategie {
    @Override
    public void decouvrir(Case c, Grille grille){
        c.setVisible(true);
        System.out.println("Vous avez perdu !");
    }
}