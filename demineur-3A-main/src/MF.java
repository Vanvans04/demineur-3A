import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

public class MF extends JFrame implements Observer {
    Jeu jeu;
    JLabel[][] tab;
    Icon flagIcon;
    int nbCases;

    public MF(Jeu j){
        nbCases = 10;
        tab = new JLabel[nbCases][nbCases];
        this.jeu = j;
        URL flagURL = getClass().getResource("/resources/flag.png");
        ImageIcon iconImage = new ImageIcon(flagURL);
        Image image = iconImage.getImage().getScaledInstance((jeu.getWindowSize()/nbCases)-nbCases, (jeu.getWindowSize()/nbCases)-nbCases, Image.SCALE_SMOOTH);
        flagIcon = new ImageIcon(image);
        build();
    }

    @Override
    public void update(Observable o, Object arg) {
        for (int i = 0; i < nbCases; i++) {
            for (int j = 0; j < nbCases; j++) {

                if (jeu.isVisible(i, j)) {
                    if (jeu.isBombe(i, j)) {
                        tab[i][j].setBackground(Color.RED);
                        tab[i][j].setText("B");
                    } else {
                        tab[i][j].setBackground(Color.LIGHT_GRAY);
                        int nbVP = jeu.getNbVP(i, j);
                        if (nbVP > 0) {
                            tab[i][j].setText(String.valueOf(nbVP));
                        }
                    }
                } else if (jeu.isDrapeau(i, j)) {
                    tab[i][j].setIcon(flagIcon);
                }
            }
        }
    }

    public void build(){
        setLayout(new GridLayout(nbCases,nbCases));
        for(int i=0;i<nbCases;i++){
            for(int j=0;j<nbCases;j++){
                JLabel jp = new JLabel();
                jp.setHorizontalAlignment(SwingConstants.CENTER);
                jp.setVerticalAlignment(SwingConstants.CENTER);
                jp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                jp.setOpaque(true);
                tab[i][j] = jp;
                add(jp);
                int finalJ = j;
                int finalI = i;
                jp.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(SwingUtilities.isLeftMouseButton(e)){
                            jeu.setLeft(finalI, finalJ);
                        }
                        else if(SwingUtilities.isRightMouseButton(e)){
                            jeu.setRight(finalI, finalJ);
                        }
                    }
                });
                pack();
            }
        }
    }
}
