/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


/**
 *
 * @author ShiroiMao asus
 */
public class FenetreFigure extends JFrame{
  private Figure2D forme;
  private AffichageFigure affichage;
  
  public FenetreFigure(String source, String dest, int nombreRotation)throws InterruptedException{
    
    forme = new Figure2D(source);
    affichage = new AffichageFigure(forme);
         
    this.setTitle(source);
    this.setSize(300+100*forme.getPlaque().getNbPlaqueSurLongueur(),50+100*forme.getPlaque().getNbPlaqueSurLongueur());
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setContentPane(affichage);
    this.revalidate();
    this.setVisible(true);
    
    affichage.revalidate();
    affichage.repaint();
    affichage.update(affichage.getGraphics());
    forme.chercheMeilleurGraphique(nombreRotation, affichage);
    forme.writeFigure(dest);
    
  }
}
