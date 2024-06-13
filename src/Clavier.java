import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.Chart;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle ;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Génère la vue d'un clavier et associe le contrôleur aux touches
 * le choix ici est d'en faire un héritié d'un TilePane
 */
public class Clavier extends TilePane {
    /**
     * il est conseillé de stocker les touches dans un ArrayList
     */
    private List<Button> clavier;

    
    
    /**
     * constructeur du clavier
     * @param touches une chaine de caractères qui contient les lettres à mettre sur les touches
     * @param actionTouches le contrôleur des touches
     * @param tailleLigne nombre de touches par ligne
     */
    public Clavier(String touches, EventHandler<ActionEvent> actionTouches) {
        // A implémenter

        
        this.clavier = new ArrayList<>();

        for (int i=0 ; i<touches.length() ; i++){
            String lettre = String.valueOf(touches.charAt(i));
            Circle cercle = new Circle(10);
            cercle.setFill(Color.RED);
            Button b = new Button(lettre,cercle);
            this.clavier.add(b);
        }

    }




    /**
     * permet de désactiver certaines touches du clavier (et active les autres)
     * @param touchesDesactivees une chaine de caractères contenant la liste des touches désactivées
     */
    public void desactiveTouches(Set<String> touchesDesactivees){
        // A implémenter
        for (String lettre :touchesDesactivees){
            for (Button b :this.clavier){
                String Textboutton = b.getText();
                if (Textboutton == lettre){b.setDisable(true);}
                else{b.setDisable(false);}
            }
            }
    }
}
