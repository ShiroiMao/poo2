/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author ShiroiMao asus
 */
public class Point2D implements Point {
    private double x;
    private double y;
    
    public Point2D(){
        this.x = 0.0;
        this.y = 0.0;
    }
    public Point2D(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y=y;
    }
    public String toString(){
        return "Point de coordonnée "+this.x+" , "+this.y;
    }
    
    public void deplacer(Point2D vecteur){
        this.x=this.x + vecteur.getX();
        this.y=this.y + vecteur.getY();
    }
    
    public double distance(Point p){
        return Math.sqrt( Math.pow(p.getX()-this.getX(),2) + Math.pow(p.getY()-this.getY(),2) );
    }
    
    /*Rotation du point autour de p selon un angle alpha*/ 
    public void rotation(Point2D O, double alpha){
        /*
        double newx = p.getX()+(this.distance(p)*Math.cos(alpha));//vérifier que cos est en degrès
        double newy = p.getY()+(this.distance(p)*Math.sin(alpha));
        this.x = newx;
        this.y = newy;
        */
       
	double xM, yM, x, y;
	double angle =alpha* Math.PI / 180;
	xM = this.x - O.x;
	yM = this.y - O.y;
	x = xM * Math.cos (angle) + yM * Math.sin (angle) + O.x;
	y = - xM * Math.sin (angle) + yM * Math.cos (angle) + O.y;
        this.x = x;
        this.y=y;
        
    }
    
    
}
