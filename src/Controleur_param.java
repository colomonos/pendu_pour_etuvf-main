
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;


/**
 * Contrôleur du chronomètre
 */
public class Controleur_param implements EventHandler<ActionEvent> {

   
    private MotMystere modelePendu;
    /**
     * vue du jeu
     **/
    private Pendu vuePendu;

    
    public Controleur_param (MotMystere modelePendu,Pendu vuePendu){
        this.modelePendu=modelePendu;
        this.vuePendu=vuePendu;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
       Button o =(Button) actionEvent.getSource();
        String Textboutton = o.getText();

        if (Textboutton == "regle du jeux") {
            this.vuePendu.popUpReglesDuJeu().showAndWait();
        }

        if (Textboutton == "nouveau min") {
            try{
            Integer valeur = Integer.valueOf(this.vuePendu.Gettexterror());
            }
            catch(NullPointerException z){
                Integer valeur = 3;
            }
            
            
        }
        if (Textboutton == "nouveau max") {
            try{
            Integer valeur = Integer.valueOf(this.vuePendu.Gettextmax());
            }
            catch(NullPointerException z){
                Integer valeur = 7;
            }
            
        }
        if (Textboutton == "nouveau max erreur") {
            try{
            Integer valeur = Integer.valueOf(this.vuePendu.Gettexterror());
            }
            catch(NullPointerException z){
                Integer valeur = 14;
            }

        }

}
}