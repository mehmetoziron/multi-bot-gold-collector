package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JLabel;

public class AltinOlustur extends JLabel {

    public int mGenislik = 20;
    public int mAltinPuani;
    public boolean mMevcut = true;
    public boolean mGizliMi = true;
    public Graphics2D g2;
    Rectangle2D rect;

    public AltinOlustur(int mAltinPuani) {

        this.mAltinPuani = mAltinPuani;
        setBounds(0, 0, mGenislik, mGenislik);

    }
    public AltinOlustur(int mAltinPuani, boolean gizlimi) {

        mGizliMi = gizlimi;
        this.mAltinPuani = mAltinPuani;
        setBounds(0, 0, mGenislik, mGenislik);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g2 = (Graphics2D) g;
        
        rect = new Rectangle2D.Double(0, 0, getWidth(), getHeight());//mGenislik, mGenislik);

        g2.setStroke(new BasicStroke(3));

        if(mGizliMi)
            g2.setColor(new Color(55 + 10 * mAltinPuani, 55 + 10 * mAltinPuani, 0 ));
        else
            g2.setColor(new Color(255,100,0));
        g2.fill(rect);

        g2.setColor(Color.black);
        
        g2.drawString(""+mAltinPuani, 3, 15);
        
        g2.draw(rect);

    }

    public void setPosition(int PosX, int PosY) {
        setBounds(PosX, PosY, mGenislik, mGenislik);
    }

}
