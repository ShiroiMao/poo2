/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *;

/**
 *
 * @author ShiroiMao asus
 */
public class PointBesier implements Point {
    private double x;
    private double y;
    int numero;//0 1 ou 2
    
    
    public PointBesier(double x, double y, int numero){
        this.x = x;
        this.y = y;
        this.numero = numero;
    }
    
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public int getNumero(){
        return numero;
    }
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y=y;
    }
    public String toString(){
        return "Point Besier de coordonnée "+this.x+" , "+this.y;
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
        
	double xM, yM, x, y;
	double angle =alpha* Math.PI / 180;
	xM = this.x - O.getX();
	yM = this.y - O.getY();
	x = xM * Math.cos (angle) + yM * Math.sin (angle) + O.getX();
	y = - xM * Math.sin (angle) + yM * Math.cos (angle) + O.getY();
        this.x = x;
        this.y=y;
        
    } 
    
}
