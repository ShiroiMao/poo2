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
public class DataParser {
    private Vector<Point> collection = new Vector<Point>();
    
    public DataParser(String data){
        String tab[] = data.split(" ");
        myParser(tab);
    }
    
    public Vector<Point> getCollection(){
        return this.collection;
    }

    public void myParser(String tab[]){
        boolean absolute = true;
        String valTab[] = tab[1].split(",");
        double relativeX = Double.parseDouble(valTab[0]);
        double relativeY = Double.parseDouble(valTab[1]);
        
        collection.add(new Point2D(relativeX,relativeY));//on ajoute le premier point
        if(tab[0].contentEquals("M")  ){
            absolute = true;
        }
        else if(tab[0].contentEquals("m")  ){
            absolute = false;
        }
        
        for(int i =2; i < tab.length-1 ; i++ ){
            if(tab[i].contentEquals("L")){
                 absolute = true;   
            }
            else if(tab[i].contentEquals("l")){
                 absolute = false;
            }
            else if(tab[i].contentEquals("H") ){
                 i++;
                 collection.add(new Point2D(Double.parseDouble(tab[i]), collection.get(collection.size()-1).getY()));
                 relativeX = Double.parseDouble(tab[i]);
                 relativeY = collection.get(collection.size()-2).getY();//-2 car on viens d'ajouter un point
            }
            else if(tab[i].contentEquals("h") ){ 
                 i++;
                 collection.add(new Point2D(Double.parseDouble(tab[i])+relativeX, collection.get(collection.size()-1).getY()));
                 relativeX = Double.parseDouble(tab[i])+relativeX;
                 relativeY = collection.get(collection.size()-2).getY();//-2 car on viens d'ajouter un point
            }
            else if(tab[i].contentEquals("V") ){
                 i++;
                 collection.add(new Point2D(collection.get(collection.size()-1).getX(),Double.parseDouble(tab[i])));
                 relativeX = collection.get(collection.size()-1).getX();
                 relativeY = Double.parseDouble(tab[i]);//-2 car on viens d'ajouter un point
            }
            else if(tab[i].contentEquals("v") ){ 
                 i++;
                 collection.add(new Point2D(collection.get(collection.size()-1).getX(),Double.parseDouble(tab[i])));
                 relativeX = collection.get(collection.size()-2).getX();//-2 car on viens d'ajouter un point
                 relativeY = Double.parseDouble(tab[i])+relativeY;
            }
            /********************Besier*******************************/
            else if(tab[i].contentEquals("C") ){
                 int j = 0;
                 i++;
                 while(!isLabel(tab[i])){
                    valTab = tab[i].split(",");
                    double x = Double.parseDouble(valTab[0]);
                    double y = Double.parseDouble(valTab[1]);
                    
                    collection.add(new PointBesier(x,y,j));
                    if(j==2){
                        relativeX = Double.parseDouble(valTab[0]);
                        relativeY = Double.parseDouble(valTab[1]);
                        j=-1;
                    }
                    i++;
                    j++;
                 }
                 i--;
            }
             else if(tab[i].contentEquals("c") ){
                 int j = 0;
                 i++;
                 while(!isLabel(tab[i])){
                    valTab = tab[i].split(",");
                    double x = Double.parseDouble(valTab[0]);
                    double y = Double.parseDouble(valTab[1]);
                    
                    collection.add(new PointBesier(x+relativeX,y+relativeY,j));
                    if(j==2){
                        relativeX = Double.parseDouble(valTab[0])+relativeX;
                        relativeY = Double.parseDouble(valTab[1])+relativeY;
                        j=-1;
                    }
                    i++;
                    j++;
                 }
                 i--;
            }
            /*********************************************************/
            else{
                valTab = tab[i].split(",");
                double x = Double.parseDouble(valTab[0]);
                double y = Double.parseDouble(valTab[1]);
                if(absolute){
                    collection.add(new Point2D(x,y));
                    relativeX = x;
                    relativeY = y;
                }
                else{
                    collection.add(new Point2D(x+relativeX,y+relativeY));
                    relativeX = x+relativeX;
                    relativeY = y+relativeY;
                }
            }
        }
        
        
    }
    boolean isLabel(String s){
        return s.contentEquals("L")||s.contentEquals("l")
               || s.contentEquals("V")||s.contentEquals("v")
               || s.contentEquals("C")||s.contentEquals("c")
               || s.contentEquals("H")||s.contentEquals("h")
               || s.contentEquals("Z")||s.contentEquals("z");
    }
}
