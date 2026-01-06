package game;

import java.awt.BasicStroke;
import java.awt.Color; 
import java.awt.Graphics;
import java.awt.Graphics2D; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException; 
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Harita extends JLabel {

    public int mEn;
    public int mBoy;
    public int mAltinSayisi;
    public int mGizliAltinSayisi;
    public int basladi = 0;
    public int mBittiKontrol = 4;

    public int mSiraKimde = 0;
    public int maaliyet = 5;
    public boolean ilkHedef = true;

    public int aAdres = 0;
    public int aUzaklik = 0;

    public int mGizliAltinKaldiMi = 0;

    public int bAdres = 0;
    public int bUzaklik = 0;
    public int bMaaliyet = 0;

    public int cAdres = 0;
    public int cUzaklik = 0;
    public int cMaaliyet = 0;

    public int dAdres = 0;
    public int dUzaklik = 0;
    public int dMaaliyet = 0;
    public int dkontrol = 0;

    static int altinyuzdesi;
    static int hamle;

    static int gizlialtinyuzdesi;

    public Timer mTimer = null;

    public Karakter A = new Karakter(255, 0, 0, "A");
    public Karakter B = new Karakter(0, 255, 0, "B");
    public Karakter C = new Karakter(0, 0, 255, "C");
    public Karakter D = new Karakter(0, 255, 255, "D");
    public Karakter T = new Karakter(255, 255, 255, "T");

    public Random mRandom = null;
    public ArrayList<AltinOlustur> Altin = new ArrayList<AltinOlustur>();
    public ArrayList<Kutu> Kutular = new ArrayList<Kutu>();

    public Harita() {
        setFocusable(true);
        mEn = AnaPencere.mGenislik;
        mBoy = AnaPencere.mYukseklik;
        
        

        mRandom = new Random(System.currentTimeMillis());
        mTimer = new Timer(300, new TimerKontrol());
        mTimer.start();

        add(A);
        B.setBounds(mEn - 20, 0, 20, 20);
        add(B);
        C.setBounds(mEn - 20, mBoy - 20, 20, 20);
        add(C);
        D.setBounds(0, mBoy - 20, 20, 20);
        add(D);
        T.setBounds(mEn, mBoy - 20, 20, 20);
        add(T);

        mAltinSayisi = ((mEn / 20 * mBoy / 20) * altinyuzdesi / 100);
        mGizliAltinSayisi = mAltinSayisi * gizlialtinyuzdesi / 100;
        mAltinSayisi -= mGizliAltinSayisi;

        AltinEkle();

        for (int i = 0; i < Altin.size(); i++) {
            add(Altin.get(i));
        }

        KutuEkle();

        for (int i = 0; i < mEn * mBoy / 400; i++) {
            add(Kutular.get(i));
        }

    }

    public void yazdir(Karakter H, int X, int Y) throws IOException {

        String karakterismi = H.mKarakter + ".txt";

        String str = "X ekseni --> " + ((X / 20) + 1) + "          Y ekseni --> " + ((Y / 20) + 1) + "\n";

        File file = new File(karakterismi);
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter bWriter = new BufferedWriter(fileWriter);

        bWriter.write(str);
        bWriter.close();

    }

    class TimerKontrol implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (ilkHedef) {
                ilkHedef = false;
                AHedef();
                BHedef();
                CHedef();
                DHedef();
            }
            if (basladi == 1) {
                AHedef();
                basladi++;
            } else if (basladi == hamle + 1) {
                BHedef();
                basladi++;
            } else if (basladi == 2 * hamle + 1) {
                CHedef();
                basladi++;
            } else if (basladi == 3 * hamle + 1) {
                DHedef();
                basladi++;
            }

            SiraKimde();
        }

    }

    public void SiraKimde() {
        if (mSiraKimde % (4 * hamle) >= hamle * 3 && mSiraKimde % (4 * hamle) < hamle * 4) {
            if (D.mOynuyor) {
                if (dkontrol != 1) {
                    Hareket(D, dAdres);
                }
            }
        } else if (mSiraKimde % (4 * hamle) >= hamle * 2 && mSiraKimde % (4 * hamle) < hamle * 3) {
            if (C.mOynuyor) {
                Hareket(C, cAdres);
            }
        } else if (mSiraKimde % (4 * hamle) >= hamle && mSiraKimde % (4 * hamle) < hamle * 2) {
            if (B.mOynuyor) {
                Hareket(B, bAdres);
            }
        } else if (mSiraKimde % (4 * hamle) >= 0 && mSiraKimde % (4 * hamle) < hamle) {
            if (A.mOynuyor) {
                Hareket(A, aAdres);
            }
        }

        mSiraKimde++;
    }

    public void Hareket(Karakter H, int adres) {
        int adim = 1;

        if (!(Altin.get(adres).mMevcut)) {
            if (H == A) {
//                System.out.println("    A alındı " + Altin.get(adres).getX() + "  " + Altin.get(adres).getX());
                AHedef();
                mSiraKimde--;
                return;
            }
            if (H == B) {
//                System.out.println("    B alındı " + Altin.get(adres).getX() + "  " + Altin.get(adres).getX());
                BHedef();
                mSiraKimde--;
                return;
            }
            if (H == C) {
//                System.out.println("    C alındı " + Altin.get(adres).getX() + "  " + Altin.get(adres).getX());
                CHedef();
                mSiraKimde--;
                return;
            }
            if (H == D) {
//                System.out.println("    D alındı " + Altin.get(adres).getX() + "  " + Altin.get(adres).getX());
                DHedef();
                mSiraKimde--;
                return;
            }
        }
        for (int i = 0; i < adim; i++) {

            int Y = 0;
            int X = 0;
            if (H.mPuan <= 0) {
                H.mOynuyor = false;
                JOptionPane.showMessageDialog(labelFor, H.mKarakter + " has no gold!\nGold: " + H.mPuan);
                mBittiKontrol--;
                if (mBittiKontrol == 1) {
                    bitti();
                }
                return;
            }
            H.mPuan -= maaliyet;
            if (H.getX() != Altin.get(adres).getX()) {
                X = Altin.get(adres).getX() - H.getX();
                if (X < 0) {
                    H.setBounds(H.getX() - 20, H.getY(), 20, 20);
                } else {
                    H.setBounds(H.getX() + 20, H.getY(), 20, 20);
                }
                try {
                    yazdir(H, H.getX(), H.getY());
                    H.mToplamAdimSayisi++;
                    H.mToplamHarcananAltin += 5;
                } catch (IOException ex) {
                    Logger.getLogger(Harita.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (H.getY() != Altin.get(adres).getY()) {
                Y = Altin.get(adres).getY() - H.getY();
                if (Y < 0) {
                    H.setBounds(H.getX(), H.getY() - 20, 20, 20);
                } else {
                    H.setBounds(H.getX(), H.getY() + 20, 20, 20);
                }
                try {
                    yazdir(H, H.getX(), H.getY());
                    H.mToplamAdimSayisi++;
                    H.mToplamHarcananAltin += 5;
                } catch (IOException ex) {
                    Logger.getLogger(Harita.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (int s = 0; s < Altin.size(); s++) // Gizlinin üzerinden geçtiğinde Görünür yap.
            {
                if (Altin.get(s).mMevcut && H.getY() == Altin.get(s).getY() && H.getX() == Altin.get(s).getX()) {
                    if (!Altin.get(s).mGizliMi) {
                        Altin.get(s).mGizliMi = true;
                        Altin.get(adres).g2.setColor(new Color(55 + 10 * Altin.get(adres).mAltinPuani, 55 + 10 * Altin.get(adres).mAltinPuani, 0));
                        Altin.get(adres).g2.fill(Altin.get(adres).rect);
                    } else {
                        Altin.get(s).mMevcut = false;
                        remove(Altin.get(s));
                    }
                }
            }

            if (H.getY() == Altin.get(adres).getY() && H.getX() == Altin.get(adres).getX()) {
                H.mPuan += Altin.get(adres).mAltinPuani;
                H.mToplananAltin += Altin.get(adres).mAltinPuani;

                Altin.get(adres).mMevcut = false;
                remove(Altin.get(adres));
                if (H == A) {
                    AHedef();
                    adres = aAdres;
                    break;
                } else if (H == B) {
                    BHedef();
                    adres = bAdres;
                    break;
                } else if (H == C) {
                    CHedef();
                    adres = cAdres;
                    break;
                } else if (H == D) {
                    DHedef();
                    adres = dAdres;
                    break;
                }
            }

//            H.getX()/20;
        }

    }

    public void DHedef() {
        int i;
        dkontrol = 0;
        dMaaliyet = -2500;
        D.mPuan -= 10;
        D.mToplamHarcananAltin += 20;
        for (i = 0; i < Altin.size(); i++) {

            if (Altin.get(i).mMevcut && Altin.get(i).mGizliMi) {
                dkontrol++;
                double dtur, tur;
                int X, Y, XY;
                X = Math.abs((Altin.get(i).getX() - D.getX()) / 20);

                Y = Math.abs((Altin.get(i).getY() - D.getY()) / 20);

                XY = X + Y;
                if (aAdres == i) {
                    tur = (double) aUzaklik / hamle;
                    if (tur > aUzaklik / hamle) {
                        tur = aUzaklik / hamle + 1;
                    }
                    dtur = (double) XY / hamle;
                    if (dtur > XY / hamle) {
                        dtur = XY / hamle + 1;
                    }
                    if (dtur >= tur) {
                        continue;
                    }
                }
                if (bAdres == i) {
                    tur = (double) bUzaklik / hamle;
                    if (tur > bUzaklik / hamle) {
                        tur = bUzaklik / hamle + 1;
                    }
                    dtur = (double) XY / hamle;
                    if (dtur > XY / hamle) {
                        dtur = XY / hamle + 1;
                    }
                    if (dtur >= tur) {
                        continue;
                    }
                }
                if (cAdres == i) {
                    tur = (double) cUzaklik / hamle;
                    if (tur > cUzaklik / hamle) {
                        tur = cUzaklik / hamle + 1;
                    }
                    dtur = (double) XY / hamle;
                    if (dtur > XY / hamle) {
                        dtur = XY / hamle + 1;
                    }
                    if (dtur >= tur) {
                        continue;
                    }
                }
                XY *= -maaliyet;
                XY += Altin.get(i).mAltinPuani;
                if (dMaaliyet == -2500 || dMaaliyet < XY) {
                    dMaaliyet = XY;
                    dUzaklik = X + Y;
                    dAdres = i;
                }
            }
        }
        if (dMaaliyet == -2500 && dkontrol == 0) {
            bitti();
        } else if (dMaaliyet == -2500) {
            return;
        } else {
//            System.out.println(dUzaklik + " birim uzaklıkta  " + dAdres + " Altin arraylistindeki sırası");
//            System.out.println("X = " + Altin.get(dAdres).getX() + "  Y = " + Altin.get(dAdres).getY());
        }

    }

    public void BHedef() {
        int i;
        bMaaliyet = -2500;
        B.mPuan -= 10;
        B.mToplamHarcananAltin += 10;
        for (i = 0; i < Altin.size(); i++) {

            if (Altin.get(i).mMevcut && Altin.get(i).mGizliMi) {

                int X, Y, XY;
                X = Math.abs((Altin.get(i).getX() - B.getX()) / 20);

                Y = Math.abs((Altin.get(i).getY() - B.getY()) / 20);

                XY = X + Y;
                XY *= -maaliyet;
                XY += Altin.get(i).mAltinPuani;
                if (bMaaliyet == -2500 || bMaaliyet < XY) {
                    bMaaliyet = XY;
                    bAdres = i;
                    bUzaklik = X + Y;
                }
            }
        }
        if (bMaaliyet == -2500) {
             bitti();
        } else {
//            System.out.println(bUzaklik + " birim uzaklıkta  " + bAdres + " Altin arraylistindeki sırası");
//            System.out.println("X = " + Altin.get(bAdres).getX() + "  Y = " + Altin.get(bAdres).getY());
        }

    }

    public void CHedef() {

        int k;
        int Uzaklik = 0;
        int adres = 0;

//        if (mGizliAltinKaldiMi == 0) {
        for (int s = 0; s < 2; s++) {

            for (k = 0; k < Altin.size(); k++) {

                if (Altin.get(k).mMevcut && !Altin.get(k).mGizliMi) {
//                        System.out.println("mevcut ve gizli ");

                    int X, Y, XY;
                    X = Math.abs((Altin.get(k).getX() - C.getX()) / 20);

                    Y = Math.abs((Altin.get(k).getY() - C.getY()) / 20);

                    XY = X + Y;

                    if (Uzaklik == 0 || Uzaklik > XY) {
                        Uzaklik = XY;
                        adres = k;
                    }

                }

            }

//                System.out.println("XXXXXXXXXXXXXXXXXX" + Altin.get(adres).getX() + "  YYYYYYYYYYYY  " + Altin.get(adres).getY() + "UZAKLIK" + Uzaklik);
            Altin.get(adres).mGizliMi = true;

//                System.out.println(Altin.get(adres).mGizliMi);
            Altin.get(adres).g2.setColor(new Color(55 + 10 * Altin.get(adres).mAltinPuani, 55 + 10 * Altin.get(adres).mAltinPuani, 0));

            Altin.get(adres).g2.fill(Altin.get(adres).rect);
            Tarama();
            Uzaklik = 0;
        }
//        }

        if (Uzaklik == 0) {

            mGizliAltinKaldiMi++;

        }

        int i;
        cMaaliyet = -2500;
        C.mPuan -= 10;
        C.mToplamHarcananAltin += 15;
        for (i = 0; i < Altin.size(); i++) {

            if (Altin.get(i).mMevcut && Altin.get(i).mGizliMi) {

                int X, Y, XY;
                X = Math.abs((Altin.get(i).getX() - C.getX()) / 20);

                Y = Math.abs((Altin.get(i).getY() - C.getY()) / 20);

                XY = X + Y;
                XY *= -maaliyet;
                XY += Altin.get(i).mAltinPuani;
                if (cMaaliyet == -2500 || cMaaliyet < XY) {
                    cMaaliyet = XY;
                    cUzaklik = X + Y;
                    cAdres = i;
                }
            }
        }
        if (cMaaliyet == -2500) {
            bitti();
        } else {
//            System.out.println(cUzaklik + " birim uzaklıkta  " + cAdres + " Altin arraylistindeki sırası");
//            System.out.println("X = " + Altin.get(cAdres).getX() + "  Y = " + Altin.get(cAdres).getY());
        }

    }

    public void Tarama() {
        for (int i = 0; i < Altin.size(); i++) {
            int X = Altin.get(i).getX();
            int Y = Altin.get(i).getY();
            T.setBounds(X, Y, 20, 20);
        }
        T.setBounds(mEn, mBoy - 20, 20, 20);
    }

    public void AHedef() {
        int i;
        aUzaklik = 0;
        A.mPuan -= 10;
        A.mToplamHarcananAltin += 5;
        for (i = 0; i < Altin.size(); i++) {

            if (Altin.get(i).mMevcut && Altin.get(i).mGizliMi) {

                int X, Y, XY;
                X = Math.abs((Altin.get(i).getX() - A.getX()) / 20);

                Y = Math.abs((Altin.get(i).getY() - A.getY()) / 20);

                XY = X + Y;

                if (aUzaklik == 0 || aUzaklik > XY) {
                    aUzaklik = XY;
                    aAdres = i;
                }
            }
//            if (Altin.isEmpty()) {
//                mTimer.stop();
//                return;
//            }
        }

        if (aUzaklik == 0) {
            bitti();
        } else {
//            System.out.println(aUzaklik + " birim uzaklıkta  " + aAdres + " Altin arraylistindeki sırası");
//            System.out.println("X = " + Altin.get(aAdres).getX() + "  Y = " + Altin.get(aAdres).getY());
        }

    }

    public void KutuEkle() {

        int en = mEn / 20;
        int boy = mBoy / 20;

        for (int i = 0; i < en; i++) {
            for (int j = 0; j < boy; j++) {
                Kutu K = new Kutu();
                K.setBounds(i * 20, j * 20, 20, 20);

                Kutular.add(K);
            }

        }

    }

    public void AltinEkle() {
        int kontrol = 0;
        for (int i = 0; i < mAltinSayisi + mGizliAltinSayisi; i++) {
            kontrol = 0;
            int posX = (Math.abs(mRandom.nextInt()) % mEn) / 20;
            int posY = (Math.abs(mRandom.nextInt()) % mBoy) / 20;
            posX *= 20;
            posY *= 20;

            // if() Buraya A;B;C;D nin ve Diğer altinlarin yeni altınla çakışması korordinatlarıyla kontrol edilecek
            if ((A.getX() == posX && A.getY() == posY)
                    || (B.getX() == posX && B.getY() == posY)
                    || (C.getX() == posX && C.getY() == posY)
                    || (D.getX() == posX && D.getY() == posY)) {
//                System.out.println("x: " + posX + " y: " + posY);
                i--;
                continue;
            }

            for (int j = 0; j < Altin.size(); j++) {
                //System.out.println("Altın X: "+Altin.get(j).getX()+"Altın Y: "+Altin.get(j).getY()+"yeni X: "+posX+"yeni Y: "+posY);    
                if (Altin.get(j).getX() == posX && Altin.get(j).getY() == posY) {
                    i--;
                    kontrol++;
                    break;
                }

            }
            if (kontrol == 1) {
                continue;
            }

//             ekran tarama matris mantığında 20*i; 20*j mantığında koordinat kontrolü ile yapılacak
            if (i < mAltinSayisi) {
                int puan = ((Math.abs(mRandom.nextInt()) % 4) + 1) * 5;

                AltinOlustur X = new AltinOlustur(puan);
                X.setBounds(posX, posY, 20, 20);
                Altin.add(X);

            } else {
                int puan = ((Math.abs(mRandom.nextInt()) % 4) + 1) * 5;
                AltinOlustur X = new AltinOlustur(puan, false);
                X.setBounds(posX, posY, 20, 20);
                Altin.add(X);
            }
        }

        for (int i = 0; i < Altin.size(); i++) {
//            System.out.println("X: " + Altin.get(i).getX() + "Y: " + Altin.get(i).getY());
        }
        //System.out.println(posX+"x"+posY);

        //Kutu K = Altin.get(Altin.size()-1).KutuOlustur(posX,posY);
        //Liste.add(K);
        //add(K);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //System.out.println(getWidth());
        Graphics2D g2 = (Graphics2D) g;

        Rectangle2D rect = new Rectangle2D.Double(0, 0, getWidth(), getHeight());

        g2.setColor(Color.red);

        g2.setStroke(new BasicStroke(2));

        g2.draw(rect);
    }

    public void bitti() {
        JOptionPane.showMessageDialog(labelFor, "Game Over"
                + "\nA's Gold Amount = " + A.mPuan + "\nB's Gold Amount = " + B.mPuan + "\nC's Gold Amount = "
                + +C.mPuan + "\nD's Gold Amount = " + D.mPuan + ""
                + "\nA's Move Count = " + A.mToplamAdimSayisi + "\nB's Move Count = " + B.mToplamAdimSayisi + ""
                + "\nC's Move Count = " + C.mToplamAdimSayisi + "\nD's Move Count = " + D.mToplamAdimSayisi + ""
                + "\nA Gold Spent = " + A.mToplamHarcananAltin + "\nB Gold Spent = " + B.mToplamHarcananAltin + ""
                + "\nC Gold Spent = " + C.mToplamHarcananAltin + "\nD Gold Spent = " + D.mToplamHarcananAltin
                + "\nA Gold Collected = " + A.mToplananAltin + "\nB Gold Collected = " + B.mToplananAltin + ""
                + "\nC Gold Collected = " + C.mToplananAltin + "\nD Gold Collected = " + D.mToplananAltin);
        mTimer.stop();
    }
}
