

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
 
public class Fenetre extends JFrame{
    
  private String src;
  private JButton bouton = new JButton("Run");
  private JTextField jtf = new JTextField("source.svg");
  private JTextField jtf2 = new JTextField("360");
  private JTextField jtf3 = new JTextField("destination.svg");
  private JLabel label = new JLabel("Source : ");
  private JLabel labelNombre = new JLabel("Nombre de division");
  private JPanel control = new JPanel();
  private JPanel erreur = new JPanel();
  private JLabel strErreur = new JLabel("");
  
    public Fenetre(){
      
        this.setTitle("Poo2 projet");
        this.setSize(800, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        Font police = new Font("Arial", Font.TYPE1_FONT, 14);
        jtf.setFont(police);
        jtf.setPreferredSize(new Dimension(150, 30));
        jtf2.setFont(police);
        jtf2.setPreferredSize(new Dimension(150, 30));
        jtf3.setFont(police);
        jtf3.setPreferredSize(new Dimension(150, 30));
        
        control.add(label);
        control.add(jtf);
        control.add(labelNombre);
        control.add(jtf2);
        control.add(bouton);
        control.add(jtf3);
        control.add(bouton);
        
        bouton.addActionListener(new BoutonListener());//Ce sont maintenant nos classes internes qui écoutent nos boutons 
        
        control.add(strErreur);
        this.setContentPane(control);
        this.setVisible(true);
    }
    
    class BoutonListener implements ActionListener{
        //Redéfinition de la méthode actionPerformed()
        public void actionPerformed(ActionEvent arg0) {
            src = jtf.getText();
            File f = new File(src);
            try{
                int nb = (int)Integer.parseInt(jtf2.getText());

                if(src.contentEquals(jtf3.getText())){
                    strErreur.setText("Interdiction d'écrire sur le fichier à lire");
                }
                else if(!svgFile(src)){
                    strErreur.setText("Le fichier à lire n'est pas au format svg");
                }
                else if(!svgFile(jtf3.getText())){
                    strErreur.setText("Le nom du fichier à écrire ne comprend pas l'extention \".svg\"");
                }
                else if(!f.exists() || f.isDirectory()){
                    strErreur.setText("Le fichier à lire n'existe pas ou est un répertoire");
                }
                else if(jtf3.getText().contentEquals("") || jtf2.getText().contentEquals("") || jtf.getText().contentEquals("")){
                    strErreur.setText("Un des champs est vide !");
                }
                else if(nb < 0){
                    strErreur.setText("Le nombre de division doit être un entier positif");
                }
                else if(nb < 0){
                    go(src,jtf3.getText(),1);
                }
                else{
                    go(src,jtf3.getText(),nb);
                    strErreur.setText("");
                }
            }catch(NumberFormatException e){
                strErreur.setText("Le nombre de division doit être un entier positif");       
            }
            control.repaint();
        }
    }
    private boolean svgFile(String s){
        if(s.length() < 5)
            return false;
        else{
            return (s.charAt(s.length()-1) == 'g') && (s.charAt(s.length()-2) == 'v') && (s.charAt(s.length()-3) == 's') && (s.charAt(s.length()-4) == '.');
        }
    }
    
    private void go(String src,String dest, int nombreRotation){
      try {
          FenetreFigure fen = new FenetreFigure(src,dest,nombreRotation);
      } catch (InterruptedException ex) {
          Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
}