/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class XmlPart{

/***************LECTURE PART****************************************/
    public Vector<Point> getCollectionFromXml(String source){//***************************A recoder ****************************************
        String data = getDataFromXmlFile(source);
        DataParser parseur = new DataParser(data);
        return parseur.getCollection();
        
    }
    
    public String getDataFromXmlFile(String source){
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      try {
         factory.setValidating(true);
         DocumentBuilder builder = factory.newDocumentBuilder();
         File fileXML = new File(source);
         Document xml;
         try {
            xml = builder.parse(fileXML);
            Element root = xml.getDocumentElement();
            String[] data = description(root, "").split("=");
            System.out.println("Lecture "+source+": d=\""+data[1]);//-----------------ligne debug
            return data[1];
         } catch (SAXParseException e) { }
      } catch (ParserConfigurationException e) {
         e.printStackTrace();
      } catch (SAXException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return "Failed reading file in XmlPart.getDataFromXmlFile";
    }
   

   public static String description(Node n, String tab){
      String str = new String();
      //Nous nous assurons que le nœud passé en paramètre est une instance d'Element
      //juste au cas où il s'agisse d'un texte ou d'un espace, etc.
      if(n instanceof Element){
         
         //Nous sommes donc bien sur un élément de notre document
         //Nous castons l'objet de type Node en type Element
         Element element = (Element)n;
         
         //nous contrôlons la liste des attributs présents
         if(n.getAttributes() != null && n.getAttributes().getLength() > 0){
            
            //nous pouvons récupérer la liste des attributs d'un élément
            NamedNodeMap att = n.getAttributes();
            int nbAtt = att.getLength();
            
            //nous parcourons tous les nœuds pour les afficher
            for(int j = 0; j < nbAtt; j++){
               Node noeud = att.item(j);
               //On récupère le nom de l'attribut et sa valeur grâce à ces deux méthodes
               if(noeud.getNodeName() == "d")
                    str += " " + noeud.getNodeName() + " =" + noeud.getNodeValue();
            }
         }
         
         //La méthode getChildNodes retournant le contenu du nœud + les nœuds enfants
         //Nous récupérons le contenu texte uniquement lorsqu'il n'y a que du texte, donc un seul enfant
         if(n.getChildNodes().getLength() == 1)
              str += n.getTextContent();
         
         //Nous allons maintenant traiter les nœuds enfants du nœud en cours de traitement
         int nbChild = n.getChildNodes().getLength();
         //récupération de la liste des nœuds enfants
         NodeList list = n.getChildNodes();
         String tab2 = tab ;
         
         //nous parcourons la liste des nœuds
         for(int i = 0; i < nbChild; i++){
            Node n2 = list.item(i);
            if (n2 instanceof Element){
               str += description(n2, tab2);
            }
         }
      }
      return str;
   }
   /***************************Lecture Part**************************/
   
   /***************************Ecriture PART************************/
   void writeFigure2DInXml(String source,String nomFichierSortie,Vector<Point> collection, SupportImpression plaque){
       writeDataInXml(source,nomFichierSortie,collection);
   }
   
   //permet d'obtenir la ligne de donnée à partir de la collection
   // "M x1,y1 x2,y2 ...... xn,yn Z"
   String getStringDataFromVector(Vector<Point> collection){
       boolean besierPrec = false;
       String data = "\"M ";
       for(int i = 0; i < collection.size(); i++){
           //System.out.println(collection.get(i).getClass().getName());
           if(collection.get(i) instanceof PointBesier){
               if(((PointBesier)collection.get(i)).getNumero() == 0){
                data = data+"C ";
                besierPrec = true;
               }
           }
           else if(besierPrec){
               data = data+"L";
               besierPrec = false;
           }
           
           
           data = data + collection.get(i).getX() +","+collection.get(i).getY()+" ";
       }
       data +="Z\"";
       return data;
   }
   
   void writeDataInXml(String source,String nomFichierSortie, Vector<Point> collection){
       String data = getStringDataFromVector(collection); //"m x1,y1 x2,y2 ...... xn,yn Z"
       //Creer le xml et écrire la ligne data dans la balise path
       File fsource = new File(source);
       File fdest = new File(nomFichierSortie);
       copier(fsource,fdest);
       remplacer("d="+data,nomFichierSortie);
   }
   
   
   void writeSupportInXml(String nomDuFichierXml, SupportImpression support){
       //creer les balises lines dans le xml correspondant au support d'impression
   }
   /*****************************************************************************
    * ***************************************************************************/

   public static boolean copier(File source, File dest) { 
	try (InputStream sourceFile = new java.io.FileInputStream(source);  
	            OutputStream destinationFile = new FileOutputStream(dest)) { 
	        // Lecture par segment de 0.5Mo  
	        byte buffer[] = new byte[512 * 1024]; 
	        int nbLecture; 
	        while ((nbLecture = sourceFile.read(buffer)) != -1){ 
	            destinationFile.write(buffer, 0, nbLecture); 
	        } 
	    } catch (IOException e){ 
	        e.printStackTrace(); 
	        return false; // Erreur 
	    } 
	    return true; // Résultat OK   
	}
    public static void remplacer(String nvlchaine, String fichier){
        try{
                System.out.println("Ecriture: "+fichier + ": "+nvlchaine);
		File entree = new File(fichier);
		File sortie = new File("temp.txt");
		BufferedReader br = new BufferedReader(new FileReader(entree));
		BufferedWriter bw = new BufferedWriter(new FileWriter(sortie));
		String ligne="";
		String trouve="";
		int resultat=0;
 
		while ((ligne = br.readLine()) != null){
		 /* Comme le fichier est très mal foutu, je récupère chaque ligne 
		 et j'enlève tous les espaces, qui sont inutiles à mes yeux */
		 trouve=ligne.replace(" ","");

		 /* Ensuite je cherche quelle ligne je dois modifier, donc je cherche
		 quelle ligne commence par "d=" (il n'y en a qu'une seule dans tout
		 le fichier */
		 resultat = trouve.indexOf("d=");
		 
		 if(resultat == 0) { /* si j'ai trouvé ma ligne, je remplace */
		     bw.write(nvlchaine+"\n");
		     bw.flush();
		 }else{ /* sinon j'écris normalement la ligne que j'ai lue, sans 
			modification */
		     bw.write(ligne+"\n");
		     bw.flush();
		 }
		}
		bw.close();
		br.close();
                
                entree.delete();
		sortie.renameTo(new File(fichier));
            }
            catch(IOException e){
            }
      
    }

}


   