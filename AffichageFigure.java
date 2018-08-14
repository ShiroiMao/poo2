/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ShiroiMao asus
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
 
public class AffichageFigure extends JPanel {
    private static int width = 100;//taille en pixel de la grille
    private static int height = 100;//taille en pixel de la grille
    
    private Figure2D forme;
    double rotation = 0.0;
    double nombrePlaque = 1;
    double nombreSoudure = 0;
    int numeroRotation =0;
    double tailleSoudure = 0;
    
    public AffichageFigure(Figure2D f){
        forme = f;
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.black);
        afficherFigure(g);
        
    }
    
     public void afficherFigure(Graphics g){
       afficherSupport(g);
       afficherValeur(g);
       afficherCollection(g);
        try {
            Thread.sleep(10);//laisser le temps à l'affichage (sinon bug affichage)
        } catch (InterruptedException ex) {
            Logger.getLogger(AffichageFigure.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
  
    public void afficherSupport(Graphics g){
      SupportImpression plaque = forme.getPlaque();
      for(int i = 0; i < plaque.getNbPlaqueSurLongueur(); i++){
          for(int j = 0; j < plaque.getNbPlaqueSurLongueur(); j++){
            if(plaque.getBooleanValue(i,j))
                g.drawRect(i*width,j*height,width,height);
          }
      }
    }
  
    /*public void afficherCollection(Graphics g){
      Vector<Point> collection=forme.getColletion();
      int tailleCollection = forme.getCollectionSize();
      int[] tabx = new int[tailleCollection];
      int[] taby = new int[tailleCollection];
        
        for(int i = 0 ; i < tailleCollection; i++){
            tabx[i]=(int)collection.get(i).getX();
            taby[i]=(int)collection.get(i).getY();
        }
        g.drawPolygon(tabx,taby, tailleCollection); 
    }*/
    
    public void afficherCollection(Graphics g){
      Graphics2D g2 = (Graphics2D)g;
      /*
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(Color.BLACK);
      g2.setStroke(new BasicStroke(1f));
      */
      Vector<Point> collection=forme.getColletion();
      Point p1;
      Point p2;
      for(int i = 0 ; i < collection.size(); i++){
          p1=collection.get(i);
          if(i == collection.size()-1){//gestion du dernier segment
              p2=collection.get(0);
          }
          else{
            p2=collection.get(i+1);
          }
          if(p2 instanceof PointBesier){
              CubicCurve2D c = new CubicCurve2D.Double();
              c.setCurve(p1.getX(), p1.getY(), p2.getX(),p2.getY(), collection.get(i+2).getX(), collection.get(i+2).getY(), collection.get(i+3).getX(), collection.get(i+3).getY());
              g2.draw(c);
              i+=2;
          }
          else{
              g2.draw(new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY()));
          }
      }
    }
    
     public void afficherValeur(Graphics g){
     g.drawString("Rotation numéro : "+this.numeroRotation, 20+100*forme.getPlaque().getNbPlaqueSurLongueur(), 20);//(fonction car en dehors de la grille
     g.drawString("Rotation : "+this.rotation, 20+100*forme.getPlaque().getNbPlaqueSurLongueur(), 35);
     g.drawString("Nombre plaque Utilisée : "+this.nombrePlaque, 20+100*forme.getPlaque().getNbPlaqueSurLongueur(), 50);
     g.drawString("Nombre de Soudure "+this.nombreSoudure, 20+100*forme.getPlaque().getNbPlaqueSurLongueur(), 65);
     g.drawString("Taille Soudure "+this.tailleSoudure, 20+100*forme.getPlaque().getNbPlaqueSurLongueur(), 80);
     
    }
    
}
