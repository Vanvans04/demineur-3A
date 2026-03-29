import javax.swing.*;
import java.awt.*;

public class HexagoneLabel extends JLabel {
    private Polygon hexagone;

    public HexagoneLabel() {
        super();
        setOpaque(false);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }

    private void calculerHexagone(){
        int largeur = getWidth();
        int hauteur = getHeight();
        int[] x = {largeur/4, largeur*3/4, largeur, largeur*3/4, largeur/4, 0};
        int[] y = {0, 0, hauteur/2, hauteur, hauteur, hauteur/2};
        hexagone = new Polygon(x,y,6);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (hexagone == null || hexagone.getBounds().width != getWidth()) {
            calculerHexagone();
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (getBackground() != null) {
            g2.setColor(getBackground());
            g2.fillPolygon(hexagone);
        }
        if (getBackground() != null) {
            g2.setColor(getBackground());
            g2.fillPolygon(hexagone);
        }
        g2.setColor(Color.BLACK);
        g2.drawPolygon(hexagone);
        super.paintComponent(g);
    }

    @Override
    public boolean contains(int x, int y) {
        if (hexagone == null){
            return super.contains(x, y);
        }
        return hexagone.contains(x, y);
    }
}