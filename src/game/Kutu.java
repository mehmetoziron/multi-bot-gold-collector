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

/**
 *
 * @author Oziron
 */
public class Kutu extends JLabel{
    
    public int mGenislik = 20; 
    
    public Kutu()
    {
        setBounds(0,0,mGenislik,mGenislik);
    }
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        //System.out.println(getWidth());
        
        Graphics2D g2 = (Graphics2D)g;
        
        Rectangle2D rect = new Rectangle2D.Double(0, 0, getWidth(),getHeight());//mGenislik, mGenislik);
        
        g2.setStroke(new BasicStroke(3));
        
        //g2.setColor(Color.GRAY);
        
        g2.fill(rect);
        
        g2.setColor(Color.black);
        
        g2.draw(rect);
    }
    
    public void setPosition(int PosX, int PosY)
    {
        setBounds(PosX,PosY,mGenislik,mGenislik);
    }
//    public Kutu KutuOlustur(int X, int Y)
//    {
//        Kutu K = new Kutu();
//        
//        K.setBounds(X*20,Y*20,mGenislik,mGenislik);
//        
//        
//        return K;
//    }
    
}
