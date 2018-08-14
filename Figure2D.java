/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Graphics;
import java.util.Vector;
import javax.swing.JPanel;

/**
 *
 * @author ShiroiMao asus
 */
public class Figure2D{

/**
 *
 * @author ShiroiMao asus
 * @param <E>
 */
    //Data
    private Vector<Point> collection;//La figure 2D est représentée par une collection de point
    private SupportImpression plaque;
    private String source;
    
    //Fonctionement interne
    private static double INFINI = 120000;
    private int indicePointDiag1 = 0;
    private int indicePointDiag2 = 0;
    private int TEMPS_SLEEP = 0;
    
    public Figure2D(String source){
        this.source = source;
         XmlPart xml = new XmlPart();
         collection = xml.getCollectionFromXml(source);
         //System.out.print("Collection donnée par le xml : ");
         //this.afficherCollection();
         this.PremierRecadrage();
         //System.out.print("Collection après premier recadrage: ");
         //this.afficherCollection();
         plaque = new SupportImpression(this.distanceMax());
         this.centrerFigureSurSupport();
         
    }
    
    public void addPoint(double x, double y){
        Point2D p = new Point2D(x,y);
        this.collection.add(p);
    }
    
    public Vector<Point> getColletion(){
        return this.collection;
    }
    
    public SupportImpression getPlaque(){
        return this.plaque;
    }
    public void afficherCollection(){
        for(int i = 0; i < collection.size(); i++){
            System.out.print(collection.get(i).getX()+","+collection.get(i).getY()+" ");
        }
        System.out.println("");
    }
    
    /*********Information sur la figure*************/
    
    //retourne la distance entre 2 point de la collection
    private static double distance(Point a, Point b){
        return Math.sqrt( Math.pow(b.getX()-a.getX(),2) + Math.pow(b.getY()-a.getY(),2) );
    }
    
    int getCollectionSize(){
        return this.collection.size();
    }
    
    /*retourne la distance maximal entre deux point de la collection*/
    public double distanceMax(){
        double max = 0.0;
        double d;
        for(int i = 0; i < collection.size(); i++){         
            for(int j = 0; j < collection.size() ; j++){
                d = distance(collection.get(i),collection.get(j));
                if(d > max){
                    max = d;
                    this.indicePointDiag1 = i;
                    this.indicePointDiag2 = j;
                }
            }
        }
        //tem.out.println("Distance max : "+max);
        return max;
    }
    
    //Retourne le point du milieu du plus long segement de la figure = le "centre" de la figure
    public Point2D getCentreFigure(){
        double x = (collection.get(this.indicePointDiag1).getX()+collection.get(this.indicePointDiag2).getX())/2;
        double y = (collection.get(this.indicePointDiag1).getY()+collection.get(this.indicePointDiag2).getY())/2;
        return new Point2D(x,y);
    }
    
    
    /**********Déplacement de la figure*****************/
    
    //Met la figure au centre du support d'impression
    //(prend le milieu du plus long segment et le place au milieu du support)
    public void centrerFigureSurSupport(){
        //System.out.println("Centrer Figure Sur support");
        //this.afficherCollection();
        
        Point centre = this.plaque.getCenter();
        Point p = this.getCentreFigure();
        
        Point2D vecteur = this.vecteurDeDeplacement(p,centre);//(de p vers centre)
        this.deplacementSelonVecteur(vecteur);
        //this.afficherCollection();
        
        
    }
    
    //rotation de la figure autour du point p
    public void rotationFigure(double angle, Point2D p){
        Point2D pointRotation = new Point2D(p.getX(),p.getY());
        for(int i = 0; i < this.collection.size(); i++){
           this.collection.get(i).rotation(pointRotation, angle);//radiant
        }  
    }
    
    //déplace tous les points de la figure selon un vecteur
    public void deplacementSelonVecteur(Point2D vecteur){
        for(int i = 0; i < collection.size(); i++){
            collection.get(i).deplacer(vecteur);
        }
    }
    
    //retourne le vecteur de deplacement de p1 vers p2
    private Point2D vecteurDeDeplacement(Point p1, Point p2){
        return new Point2D(p2.getX()-p1.getX(),p2.getY()-p1.getY());      
    }
    
    //Deplace la figure de façon à ce qu'il n'y ai plus de coordonnée négative
    public void PremierRecadrage(){
        double xmin = INFINI;
        double ymin = INFINI;
        //On cherche le point le plus négatif
        for(int i = 0; i < collection.size(); i++){
            if(collection.get(i).getX() < xmin )
               xmin = collection.get(i).getX();
            if(collection.get(i).getY() < ymin)
               ymin = collection.get(i).getY();
        }
        //System.out.println("Xmin et Y min : "+xmin+" , "+ ymin);
        //Deplacer la figure de facon à ce qu'il n'y a plus de x négatif
        if(xmin< 0){
            Point2D vecteurX = new Point2D(xmin*(-1),0.0);
            this.deplacementSelonVecteur(vecteurX);
        }
        //deplacer la figure vers le haut (plus de y negatif)
        if(ymin< 0){
            Point2D vecteurX = new Point2D(0.0,ymin*(-1));
            this.deplacementSelonVecteur(vecteurX);
        }
    }
    public void recadrer(){
        //chercher le point le plus a gauche
        //deplacer la figure le plus a gauche possible
        //chercher le point le plus en haut
        //deplacer la figure le plus en haut possible
        double xmin = INFINI;
        double ymin = INFINI;
    
        //On cherche le point le plus petit x et le plus petit y
        for(int i = 0; i < collection.size(); i++){
            if(collection.get(i).getX() < xmin )
               xmin = collection.get(i).getX();
            if(collection.get(i).getY() < ymin)
               ymin = collection.get(i).getY();
        }
        //System.out.println("Xmin et Y min : "+xmin+" , "+ ymin);
        //Deplacer la figure le plus à droite possible
        if(xmin > 0){
            Point2D vecteurX = new Point2D(xmin*(-1),0.0);
            this.deplacementSelonVecteur(vecteurX);
        }
        //deplacer la figure vers le haut
        if(ymin > 0){
            Point2D vecteurX = new Point2D(0.0,ymin*(-1));
            this.deplacementSelonVecteur(vecteurX);
        }
        
    }
   
    //Cherche la meilleur position sur la plaque d'impression et gère l'affichage
    public void chercheMeilleurGraphique(int nombreDivision, AffichageFigure affichage) throws InterruptedException{
       double degresDivision = 360.0/nombreDivision;//defini l'angle des rotations
       double scoreDEfficacitee = INFINI;
       int indiceMeilleurAngle = 0;
       this.centrerFigureSurSupport();
       Vector<Point> save = this.recopieCollection(); //on creer une copie de la figure 
       
       for(int i = 0; i < nombreDivision; i++){//On va tester n recadrement different sur la copie de la figure
           //System.out.println("Rotation : "+i);
           for(int j = 0; j < save.size(); j++){//reset de la position de la figure
               this.collection.get(j).setX(save.get(j).getX());
               this.collection.get(j).setY(save.get(j).getY());
           }
           
           this.rotationFigure(i*degresDivision, this.getCentreFigure());
           this.recadrer();
           //this.afficherCollection();
           
           //affichage---------------------------------------------------------
           int nbPlaque =  this.plaque.nombreDePlaqueUtilisee(this.collection);
           affichage.nombrePlaque = nbPlaque;
           affichage.nombreSoudure = this.plaque.nombreSoudure(this.collection);
           affichage.rotation = i*degresDivision;
           affichage.tailleSoudure = this.plaque.tailleSoudure(this.collection);
           affichage.numeroRotation = i;
           affichage.update(affichage.getGraphics());
           Thread.sleep(TEMPS_SLEEP);
           //------------------------------------------Affichage
           
           double eff = INFINI;
           eff = this.efficacitee3();
           //System.out.println("Score rotation :"+ eff + "avec angle : "+i*degresDivision+ " degres");
           //System.out.println();
           if(scoreDEfficacitee > eff){
               scoreDEfficacitee = eff;
               indiceMeilleurAngle = i;
           }
       }
       //ici on update la figure avec le meilleur angle
       //System.out.println("Meilleur rotation = rotation "+indiceMeilleurAngle);
       this.collection = save;     
       this.rotationFigure(degresDivision*indiceMeilleurAngle,this.getCentreFigure());//rotation de la figure garentie sans débordement
       this.recadrer();//on remet la figure le plus en haut et le plus à droite possible
       
        //------------------------------Affichage
       int nbPlaqueUsed = this.plaque.nombreDePlaqueUtilisee(this.collection);
       affichage.nombrePlaque = nbPlaqueUsed;
       affichage.nombreSoudure = this.plaque.nombreSoudure(this.collection);
       affichage.rotation = indiceMeilleurAngle*degresDivision;
       affichage.numeroRotation = indiceMeilleurAngle;
       affichage.tailleSoudure = this.plaque.tailleSoudure(this.collection);
       affichage.update(affichage.getGraphics());
       Thread.sleep(TEMPS_SLEEP);
        //-------------------------------Affichage//
    }
    
    //retourne une nouvelle collection copie de la collection de la figure (this)
    Vector<Point> recopieCollection(){//ne marche qu'avec POINT2D
        Vector<Point> collec = new Vector<Point>();
        for(int i= 0; i < this.collection.size(); i++){
            if(collection.get(i) instanceof PointBesier)
                collec.add(new PointBesier(collection.get(i).getX(),collection.get(i).getY(),((PointBesier)collection.get(i)).getNumero()) );
            else
                collec.add(new Point2D(collection.get(i).getX(),collection.get(i).getY()));
        }
        return collec;     
    }
    
    //regarde juste le ombre de laque utilisée
    double efficacitee1(){
        return 1000.0 * this.plaque.nombreDePlaqueUtilisee(this.collection);
    }
    
    //regarde le nombre de plaqu utilisée et le nombre de soudure
    double efficacitee2(){
        int nbPlaque = this.plaque.nombreDePlaqueUtilisee(this.collection);
        int nbSoudure = this.plaque.nombreSoudure(this.collection);
        return 1000.0 * nbPlaque +  nbSoudure;
    }
    
    double efficacitee3(){
        int nbPlaque = this.plaque.nombreDePlaqueUtilisee(this.collection);
        int nbSoudure = this.plaque.nombreSoudure(this.collection);
        double tailleSoudure = this.plaque.tailleSoudure(this.collection);
        return 1000.0 * nbPlaque +  nbSoudure*10 + tailleSoudure/100;
    }
    /***********Ecriture du document xml***************/

    
    public void writeFigure(String nomFichierSortie){
        XmlPart xml = new XmlPart();
        xml.writeFigure2DInXml(this.source,nomFichierSortie, this.getColletion(), this.getPlaque());
    }
}
