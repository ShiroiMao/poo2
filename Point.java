
/**
 *
 * @author ShiroiMao asus
 */
public interface Point{
    
    public double getX();
    public double getY();
    public void setX(double x);
    public void setY(double y);
    public String toString();
    
    public double distance(Point p);
    
    //deplace un point selon un vecteur (le vecteur est representé par un point de coordonnée x,y)
    public void deplacer(Point2D vecteur);
    
    /*Rotation du point autour de p selon un angle alpha en degrès*/ 
    public void rotation(Point2D p, double alpha);
         
}
