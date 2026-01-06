/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JLabel;

public class Karakter extends JLabel {

    public int mGenislik = 20;
    static int BaslangicAltin;
    public int red, green, blue, mPuan = BaslangicAltin;
    public boolean mOynuyor = true;

    public int mToplamAdimSayisi = 0;
    public int mToplamHarcananAltin = 0;
    public int mToplananAltin = 0;

    public String mKarakter;

    public Karakter(int red, int green, int blue, String mKarakter) {

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.mKarakter = mKarakter;

        setBounds(0, 0, mGenislik, mGenislik);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //System.out.println(getWidth());
        Graphics2D g2 = (Graphics2D) g;

        Rectangle2D rect = new Rectangle2D.Double(0, 0, getWidth(), getHeight());//mGenislik, mGenislik);

        g2.setStroke(new BasicStroke(3));

        g2.setColor(new Color(this.red, this.green, this.blue));

        g2.fill(rect);

        g2.setColor(Color.black);

        g2.draw(rect);

        g2.drawString(mKarakter, 5, 15);
    }

}
