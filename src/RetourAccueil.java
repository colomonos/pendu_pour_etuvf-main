import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton Accueil
 */
public class RetourAccueil implements EventHandler<ActionEvent> {
    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     **/
    private Pendu vuePendu;

    /**
     * @param modelePendu modèle du jeu
     * @param vuePendu vue du jeu
     */
    public RetourAccueil(MotMystere modelePendu, Pendu vuePendu) {
        // A implémenter
        this.vuePendu = vuePendu;
        this.modelePendu = modelePendu;
    }


    /**
     * L'action consiste à retourner sur la page d'accueil. Il faut vérifier qu'il n'y avait pas une partie en cours
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // A implémenter
        Button o =(Button) actionEvent.getSource();
        String Textboutton = o.getText();

        if(Textboutton == ""){
            this.vuePendu.modeAccueil();
        }


        if(this.modelePendu.getNbErreursRestants() >0 || this.modelePendu.getNbLettresRestantes() > 0 ){
            Optional<ButtonType> reponse = this.vuePendu.popUpPartieEnCours().showAndWait();
            if (reponse.isPresent() && reponse.get().equals(ButtonType.YES)){
                this.vuePendu.modeAccueil();
                System.out.println("Ok !");
            }
            else{
                System.out.println("D'ac !");
            }}
        
        
        
    
    
}
}
