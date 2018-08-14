/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Vector;

/**
 *
 * @author ShiroiMao asus
 */
public class SupportImpression {
    private double PLAQUE_SIZE = 100; //On utilise des plaque de 1m = 100cm pour simpifier
    private boolean grille[][];
    private int nombrePlaqueSurLongueur;
    
    
    public SupportImpression(double segment){
        //System.out.println("Plus grande longueur : " + segment);
        this.nombrePlaqueSurLongueur = nombreMaximumPlaque(segment,PLAQUE_SIZE)+1;
        if(nombrePlaqueSurLongueur > 1){
            this.grille = new boolean[nombrePlaqueSurLongueur][nombrePlaqueSurLongueur];
        }
    }
    
    public int getNbPlaqueSurLongueur(){
        return this.nombrePlaqueSurLongueur;
    }
    
    public boolean[][] getMatrice(){
        return this.grille;
    }
    public boolean getBooleanValue(int i, int j){
        if(nombrePlaqueSurLongueur > 1)
            return grille[i][j];
        else 
            return true;
        
    }
    
    /*renvoit le nombre maximum de plaque utile pour un object
    = carrée contenant le cercle de diametre = segment le plus long de l'object*/
    public static int nombreMaximumPlaque(double segment, double plaqueSize){
        int nb = 1;
        while(nb*plaqueSize < segment){
            nb++;
        }
        //System.out.println("Nombre minimum de plaque pour la hauteur : " + nb);
        return nb;
    }
    
    //Met touts les element de la plaque  à true
    public void initPlaqueImpression(){
        if(this.nombrePlaqueSurLongueur > 1){
            for(int i =0; i < this.nombrePlaqueSurLongueur ; i++ )
                for(int j = 0 ; j < this.nombrePlaqueSurLongueur; j++){
                    grille[i][j] = true;
                }
        }
        else
            ;
            
    }
    
    public Point2D getCenter(){
        Point2D p = new Point2D(nombrePlaqueSurLongueur * PLAQUE_SIZE/2,nombrePlaqueSurLongueur * PLAQUE_SIZE/2);
        return p;
        
    }

    //Adapte le support d'impression à la collection(Met à false les plaques non utltilsées du support d'impression)
    public void setNonUsedPlaque(Vector<Point> collection){
        if(nombrePlaqueSurLongueur > 1){
            for(int i =0; i < this.nombrePlaqueSurLongueur ; i++ ){
                for(int j = 0 ; j < this.nombrePlaqueSurLongueur; j++){
                    grille[i][j] = false;
                }
            }
            for(int i = 0; i < collection.size(); i++){
                int x = (int)Math.round(collection.get(i).getX()/PLAQUE_SIZE);
                int y = (int)Math.round(collection.get(i).getY()/PLAQUE_SIZE);
               grille[x][y] = true;
            }
        }
    }
        
    //retourne nombre de plaque utilisé par la collection
    public int nombreDePlaqueUtilisee(Vector<Point> collection){
        if(nombrePlaqueSurLongueur > 1){
            for(int i =0; i < this.nombrePlaqueSurLongueur ; i++ ){//Initialisations des cases de la matrice a faux
                for(int j = 0 ; j < this.nombrePlaqueSurLongueur; j++){
                    this.grille[i][j] = false;
                }
            }
            
            for(int i = 0; i < collection.size(); i++){// on met a vrai toute les cases par lequelles passe une arrete de la figure
                if(i == collection.size()-1){
                    //System.out.println(collection.get(i)+ "  " + collection.get(0));
                    this.setMatrice(collection.get(i),collection.get(0), this.grille);
                }
                else{
                    //System.out.println(collection.get(i)+ "  " + collection.get(i+1));
                    this.setMatrice(collection.get(i),collection.get(i+1), this.grille);
                }
            }
            
            int nb = 0;
            for(int i =0; i < this.nombrePlaqueSurLongueur ; i++ ){// on compte le nombre de case vrai
                for(int j = 0 ; j < this.nombrePlaqueSurLongueur; j++){
                    if(grille[i][j]){
                      nb++;  
                    }
                }
            }
            //System.out.println(nb+" plaques utilisée");
            return nb;
        }
        else
            //System.out.println("1 plaques utilisée");
            return 1;
    }
    
    private void setMatrice(Point p1, Point p2,boolean[][] matrice){
        double a = 0.0;
        double b = 0.0;
        int x1=getIndiceOnPlaque(p1.getX());
        int y1=getIndiceOnPlaque(p1.getY());
        int x2=getIndiceOnPlaque(p2.getX());
        int y2=getIndiceOnPlaque(p2.getY());
        Vector<Point2D> tab = new Vector<Point2D>();
        int x;
        int y;
        double p1x = Math.min(p1.getX(),p2.getX());
        double p2x = Math.max(p1.getX(),p2.getX());
        double p1y = Math.min(p1.getY(),p2.getY());
        double p2y = Math.max(p1.getY(),p2.getY());
        
        matrice[x1][y1] = true;
        matrice[x2][y2] = true;
        
        //System.out.println("x1:"+x1+ " x2:"+x2 +" y1:"+y1 +" y2:"+y2);
        
        if(x1!=x2 || y1!=y2){   //Si les points ne sont pas sur la meme plaque
            
            a = (p2.getY()-p1.getY())/(p2.getX()-p1.getX());// On choppe l'équation de la droite y = ax+b
            b = p1.getY()-a*p1.getX();
            
            
            //On cherche ensuite les solution des équations y%100 = 0 et x%100
            for(double i = p1x+100-(p1x%100);i < p2x; i+= 100){
                double i2 = i-0.00000000001;//Pour metre la plaque précedante à vrai car sinon on ne prend que celle en dessous à droite
                tab.add(new Point2D(i2,a*i2+b));
                tab.add(new Point2D(i,a*i+b));
                //System.out.println("Solution : "+i +" , "+(a*i+b));
            }
            for(double i = p1y+100-(p1y%100);i < p2y; i+= 100){
                double i2 = i-0.00000000001;
                tab.add(new Point2D(((i2-b)/a),i2));
                tab.add(new Point2D(((i-b)/a),i));
                //System.out.println("Solution : "+(i-b)/a +" , "+i);
            }
            //------------------------------------------------Et on les met dans le tableau tab
            //System.out.println("Dans le tableau il y a : "+tab.size()+" point");
            
            
            
            //pour chaque solution on met a true la plaque correspondante
            for(int i = 0; i < tab.size(); i++){
                x = getIndiceOnPlaque(tab.get(i).getX());
                y = getIndiceOnPlaque(tab.get(i).getY());
                matrice[x][y]=true;
                //System.out.println("x : "+x+" y : "+y);
            }
        }
        
    }

    public int nombreSoudure(Vector<Point> collection){
        if(nombrePlaqueSurLongueur > 1){
            int nbSoudure = 0;
            //obetnir un tableau de tous les point de la figure entre deux plaques
            Vector<Point> tableau = new Vector<Point>();
            for(int i = 0; i < collection.size(); i++){
                if(i == collection.size()-1){
                    this.getOnSoudurePoint(collection.get(i),collection.get(0), tableau);
                }
                else{
                    this.getOnSoudurePoint(collection.get(i),collection.get(i+1), tableau);
                }
            }//--------------------------------------------------->tableau contient tous les points situés sur une soudure
            
            Vector<Point> tabX = new Vector<Point>();
            Vector<Point> tabY = new Vector<Point>();
            
            for(int i = 0 ; i < tableau.size() ; i++){
                if( tableau.get(i).getX()%100 > 99.99999 || tableau.get(i).getX()%100 < 0.00001 ){//Si abs mod 100 == 0 on ajute a tabX
                    tabX.add(tableau.get(i));
                }
                
                else if(tableau.get(i).getY()%100 > 99.99999 || tableau.get(i).getY()%100 < 0.00001 ){
                    tabY.add(tableau.get(i));//sinon on ajoute a tabY
                }
                else{
                    System.out.println("Pas dans le tableau : "+tableau.get(i));
                }
                
            }
            
            //on tri les tableau par ordre croissant de  valeur et de 100
            triCroissantX(tabX);
            triCroissantYApresX(tabX);
            triCroissantY(tabY);
            triCroissantXApresY(tabY);
            
            //On compte les segments ainsi formé
            
            if(tabX.size()!=1)
            for(int i = 0; i < tabX.size(); i+=2){
                nbSoudure+=nombreDePlaqueTraverse(tabX.get(i).getY() , tabX.get(i+1).getY());  
            }
            if(tabY.size()!=1)
            for(int i = 0; i < tabY.size(); i+=2){
                nbSoudure+=nombreDePlaqueTraverse(tabY.get(i).getX() , tabY.get(i+1).getX());  
            }
            
            return nbSoudure;
        }
        else
            return 0;
    }
    
    private int nombreDePlaqueTraverse(double a , double b){//a < b
        int compteur = 1;
        int plaqueAct = getIndiceOnPlaque(b);
        if(getIndiceOnPlaque(a) != getIndiceOnPlaque(b)){
            return 1 + nombreDePlaqueTraverse(a,b-100);
        }
        return 1;
    }
     
    public double tailleSoudure(Vector<Point> collection){
        if(nombrePlaqueSurLongueur > 1){
            double tailleSoudure=0.0;
            //obetnir un tableau de tous les point de la figure entre deux plaques
            Vector<Point> tableau = new Vector<Point>();
            for(int i = 0; i < collection.size(); i++){
                if(i == collection.size()-1){
                    this.getOnSoudurePoint(collection.get(i),collection.get(0), tableau);
                }
                else{
                    this.getOnSoudurePoint(collection.get(i),collection.get(i+1), tableau);
                }
            }//--------------------------------------------------->tableau contient tous les points situés sur une soudure
            
            Vector<Point> tabX = new Vector<Point>();
            Vector<Point> tabY = new Vector<Point>();
            
            for(int i = 0 ; i < tableau.size() ; i++){
                if( tableau.get(i).getX()%100 > 99.99999 || tableau.get(i).getX()%100 < 0.00001 ){//Si abs mod 100 == 0 on ajute a tabX
                    tabX.add(tableau.get(i));
                }
                
                else if(tableau.get(i).getY()%100 > 99.99999 || tableau.get(i).getY()%100 < 0.00001 ){
                    tabY.add(tableau.get(i));//sinon on ajoute a tabY
                }
                else{
                    System.out.println("Pas dans le tableau : "+tableau.get(i));
                }
                
            }
            
            //on tri les tableau par ordre croissant de  valeur et de 100
            triCroissantX(tabX);
            triCroissantYApresX(tabX);
            triCroissantY(tabY);
            triCroissantXApresY(tabY);
           
           
            if(tabX.size()!=1)
            for(int i = 0; i < tabX.size(); i+=2){
                tailleSoudure+=Math.abs(tabX.get(i+1).getY()-tabX.get(i).getY());    
            }
            if(tabY.size()!=1)
            for(int i = 0; i < tabY.size(); i+=2){
                tailleSoudure+=Math.abs(tabY.get(i+1).getX()-tabY.get(i).getX());  
            }
            return tailleSoudure;
        }
        else
            return 0;
        
    }
    void triCroissantX(Vector<Point> tab){
        Point temp;
        for(int i= 0; i < tab.size(); i++){
            for(int j= i+1; j < tab.size(); j++){
                if(tab.get(j).getX() < tab.get(i).getX()){
                    temp = tab.get(i);
                    tab.set(i, tab.get(j));
                    tab.set(j, temp);
                }
            }
        }
        
    }
    void triCroissantY(Vector<Point> tab){
        Point temp;
        for(int i= 0; i < tab.size(); i++){
            for(int j= i+1; j < tab.size(); j++){
                if(tab.get(j).getY() < tab.get(i).getY()){
                    temp = tab.get(i);
                    tab.set(i, tab.get(j));
                    tab.set(j, temp);
                }
            }
        }
    }
    
    void triCroissantXApresY(Vector<Point> tab){
        Point temp;
        for(int i= 0; i < tab.size(); i++){
            for(int j= i+1; j < tab.size(); j++){
                if(tab.get(j).getX() < tab.get(i).getX() && tab.get(j).getY() == tab.get(i).getY()){
                    temp = tab.get(i);
                    tab.set(i, tab.get(j));
                    tab.set(j, temp);
                }
            }
        }
        
    }
    void triCroissantYApresX(Vector<Point> tab){
        Point temp;
        for(int i= 0; i < tab.size(); i++){
            for(int j= i+1; j < tab.size(); j++){
                if(tab.get(j).getY() < tab.get(i).getY() && tab.get(j).getX() == tab.get(i).getX()){
                    temp = tab.get(i);
                    tab.set(i, tab.get(j));
                    tab.set(j, temp);
                }
            }
        }
        
    }

    
    
    void getOnSoudurePoint(Point p1,Point p2 ,Vector<Point> tableau){
        double a = 0.0;
        double b = 0.0;
        int x1=getIndiceOnPlaque(p1.getX());
        int y1=getIndiceOnPlaque(p1.getY());
        int x2=getIndiceOnPlaque(p2.getX());
        int y2=getIndiceOnPlaque(p2.getY());
        double p1x = Math.min(p1.getX(),p2.getX());
        double p2x = Math.max(p1.getX(),p2.getX());
        double p1y = Math.min(p1.getY(),p2.getY());
        double p2y = Math.max(p1.getY(),p2.getY());

        if(x1!=x2 || y1!=y2){   //Si les points ne sont pas sur la meme plaque
            
            a = (p2.getY()-p1.getY())/(p2.getX()-p1.getX());// On choppe l'équation de la droite y = ax+b
            b = p1.getY()-a*p1.getX();
            
            //On cherche ensuite les solution des équations y%100 = 0 et x%100 (les points sur les soudures)
            for(double i = p1x+100-(p1x%100);i < p2x; i+= 100){ //X
                tableau.add(new Point2D(i,a*i+b));
            }
            for(double i = p1y+100-(p1y%100);i < p2y; i+= 100){//Y
                tableau.add(new Point2D(((i-b)/a),i));
            }   
            //et on les ajoutes à tableau 
        }
       return;
    }
    private int getIndiceOnPlaque(double value){
        return Math.abs((int)(Math.floor(value/PLAQUE_SIZE)));
    }
}
