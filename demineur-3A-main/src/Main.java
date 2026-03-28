import javax.swing.*;
import java.awt.*;
import java.util.Observer;

public class Main {
    public static void main(String[] args) {
        int nbCases = 10;
        int windowSize = 500;
        Jeu j = new Jeu(windowSize, nbCases);
        JFrame mf = new MF(j);
        j.init();
        j.addObserver((Observer) mf);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                mf.setSize(windowSize,windowSize);
                mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mf.setVisible(true);

            }
        });
    }
}