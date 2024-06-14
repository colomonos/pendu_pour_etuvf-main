import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;

import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;

/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {

    /*** Liste des atribut pour le paramtre*/
    private TextField min ;
    private TextField max ;
    private TextField error ;





    /*** modèle du jeu**/
    private MotMystere modelePendu;

    /*** Liste qui contient les images du jeu*/
    private ArrayList<Image> lesImages;

    /*** Liste qui contient les noms des niveaux*/    
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage

    /*** le dessin du pendu*/
    private ImageView dessin;

    /*** le mot à trouver avec les lettres déjà trouvé*/
    private Text motCrypte;

    /*** la barre de progression qui indique le nombre de tentatives*/
    private ProgressBar pg;

    /*** le clavier qui sera géré par une classe à implémenter*/
    private Clavier clavier;

    /*** le text qui indique le niveau de difficulté*/
    private Text leNiveau;

    /*** le chronomètre qui sera géré par une clasee à implémenter*/
    private Chronometre chrono;

    /*** le panel Central qui pourra être modifié selon le mode (accueil ou jeu)*/
    private BorderPane panelCentral;

    /*** le bouton Paramètre / Engrenage*/
    private Button boutonParametres;

    /*** le bouton Accueil / Maison*/    
    private Button boutonMaison;

    /*** le bouton Accueil / Maison*/    
    private Button boutonjeu;

    /*** le bouton qui permet de (lancer ou relancer une partie*/ 
    private Button bJouer;



    
    /*** initialise les attributs (créer le modèle, charge les images, crée le chrono ...)*/
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        // A terminer d'implementer
        this.panelCentral = new BorderPane();
        this.chrono = new Chronometre();
        this.leNiveau = new Text();
        this.bJouer=new Button("lancer partie");
        this.dessin =new ImageView();
        this.pg = new ProgressBar(0);
        this.niveaux = new ArrayList<>();
        this.niveaux.add("Facile");
        this.niveaux.add("Medium");
        this.niveaux.add("Difficile");
        this.niveaux.add("Exper");
        this.min = new TextField ();
        this.max = new TextField ();
        this.error = new TextField ();
        this.motCrypte = new Text(this.modelePendu.getMotCrypte());

        ImageView h = new ImageView("file:img/home.png");
        ImageView i = new ImageView("file:img/info.png");
        ImageView p = new ImageView("file:img/parametres.png");

        h.setFitHeight(20); h.setFitWidth(20);
        p.setFitHeight(20); p.setFitWidth(20);
        i.setFitHeight(20); i.setFitWidth(20);

        this.boutonMaison = new Button("",h);
        this.boutonjeu = new Button("",p);
        this.boutonParametres = new Button("",i);

        ControleurLettres controleurL = new ControleurLettres(this.modelePendu,this);
        this.clavier = new Clavier("abcdefghijklmnopqrstuvwxyz",controleurL);
        
        

    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        BorderPane fenetre = new BorderPane();
        fenetre.setTop(this.titre());
        fenetre.setCenter(this.panelCentral);
        return new Scene(fenetre, 800, 1000);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private Pane titre(){
        // A implementer ø
                 
        BorderPane banniere = new BorderPane();
        HBox listbutton = new HBox();   
        banniere.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(0), Insets.EMPTY)));    

        Label titre = new Label("jeu du pendu");
        titre.setPadding(new Insets(10));

        RetourAccueil acc =new RetourAccueil(this.modelePendu, this);
        boutonMaison.setOnAction(acc);




        
        ControleurInfos mj =new ControleurInfos(this);
        this.boutonParametres.setOnAction(mj);

        
        RetourAccueil pr =new RetourAccueil(this.modelePendu, this);
        this.boutonjeu.setOnAction(pr);

       
        
        

        listbutton.getChildren().addAll(this.boutonMaison, this.boutonjeu,this.boutonParametres);
        listbutton.setSpacing(10);
        listbutton.setPadding(new Insets(10));

        banniere.setLeft(titre);
        banniere.setRight(listbutton);
        
        return banniere;
    }

    // /**
     // * @return le panel du chronomètre
     // */
  private TitledPane leChrono(){
        // A implementer
        this.chrono = new Chronometre();   
        this.chrono.start();
        TitledPane res = new TitledPane("Chronomètre", this.chrono);
        res.setCollapsible(false);        
        return res;
       }

    // /**
     // * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     // *         de progression et le clavier 

     private Pane fenetreJeu(){
        // A implementer
        BorderPane bordcentre =new BorderPane();
        this.dessin.setImage(this.lesImages.get(0));
        VBox Vb =new VBox();
        VBox Vbc =new VBox();
        
        ControleurLancerPartie lancer =new ControleurLancerPartie(this.modelePendu, this);

        Button NouveauMoT = new Button("Nouveau mot");
        NouveauMoT.setOnAction(lancer);
        Vb.getChildren().addAll(this.leNiveau,this.leChrono(),NouveauMoT);
        
        
        TilePane clav = this.clavier;
        
        Vbc.getChildren().addAll(this.dessin,this.pg,clav);


        bordcentre.setCenter(Vbc);
        bordcentre.setTop(this.motCrypte);
        bordcentre.setBottom(clav);

        bordcentre.setRight(Vb);


        bordcentre.setPadding(new Insets(10, 50, 50, 50));

        Vbc.setPadding(new Insets(10, 50, 50, 50));
        Vb.setSpacing(70);
        
        


        return bordcentre;
    }

     // * @return la fenêtre paramètres sur laquelle on peut choisir les paramètres de jeu
     // */
    private Pane parametre(){
        // A implementer
        
        

        BorderPane cent = new BorderPane();
        Label intro = new Label("Voici les parametres \nvous pouvez choisir la taille des mot et le nombre d'erreur max");
        GridPane par = new GridPane();

        this.min.setPromptText("nombre de lettre minime");
        this.max.setPromptText("nombre de lettre max");
        this.error.setPromptText("nombre d'erreur possible'");

        Controleur_param param = new Controleur_param(this.modelePendu,this);

        Button bmin = new Button("nouveau min");
        bmin.setOnAction(param);
        Button bmax = new Button("nouveau max");
        bmax.setOnAction(param);
        Button berreur = new Button("nouveau max erreur");
        berreur.setOnAction(param);
        Button regledujeux = new Button("regle du jeux");
        regledujeux.setOnAction(param);
        
        cent.setPadding(new Insets(30, 70, 70, 70));

        par.add(this.min, 0, 0,1,1);
        par.add(bmin,1, 0,1,1);

        par.add(this.max, 0, 1,1,1);
        par.add(bmax, 1, 1,1,1);

        par.add(this.error, 0, 2,1,1);
        par.add(berreur, 1, 2,1,1);

        par.add(regledujeux, 0, 3,1,1);


        this.min.setPadding(new Insets(10, 20, 10, 10));
        bmin.setPadding(new Insets(10, 10, 10, 10));

        bmax.setPadding(new Insets(10, 10, 10, 10));

        berreur.setPadding(new Insets(10, 10, 10, 10));
        regledujeux.setPadding(new Insets(10, 10, 10, 10));

        par.setMargin(this.min, new Insets(10, 10, 10, 10));
        par.setMargin(bmin, new Insets(10, 10, 10, 10));
        //par.setMargin(this.max, new Insets(10, 10, 10, 10));
        par.setMargin(bmax, new Insets(10, 10, 10, 10));
        //par.setMargin(this.error, new Insets(10, 10, 10, 10));
        par.setMargin(berreur, new Insets(10, 10, 10, 10));
        par.setMargin(regledujeux, new Insets(10, 10, 10, 10));


        cent.setTop(intro);
        cent.setCenter(par);



        return cent;
    }

    public  String Gettexterror(){
        return this.error.getText();

    }

    public String Gettextmax(){
        return this.max.getText();

    }

    public String Gettextmin(){
        return this.min.getText();

    }




     // * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
     // */
     private Pane fenetreAccueil(){
        VBox centre =new VBox();

        TitledPane choix = new TitledPane();
        

        VBox bouttonrad =new VBox();

        final ToggleGroup group = new ToggleGroup();
        ControleurNiveau a = new ControleurNiveau(this.modelePendu);
        
        for (String b :this.niveaux){
            RadioButton c = new RadioButton();
            c.setText(b);
            c.setToggleGroup(group);
            c.setOnAction(a);
            bouttonrad.getChildren().add(c);
        }
        
        /* 
        RadioButton button1 = new RadioButton("Facile");
        RadioButton button2 = new RadioButton("Medium");
        RadioButton button3 = new RadioButton("Difficile");
        RadioButton button4 = new RadioButton("Exper");
        button2.setToggleGroup(group);
        button3.setToggleGroup(group);
        button4.setToggleGroup(group);
        button1.setOnAction(a);
        button2.setOnAction(a);
        button3.setOnAction(a);
        button4.setOnAction(a);
        

        bouttonrad.getChildren().addAll(button1, button2,button3,button4);
        */

        choix.setText("niveau de difficulter");
        choix.setContent(bouttonrad);

        centre.getChildren().addAll(this.bJouer,choix);

        

        bouttonrad.setPadding(new Insets(10, 50, 50, 50));
        centre.setPadding(new Insets(10, 50, 50, 50));
        centre.setSpacing(5.5);


        ControleurLancerPartie lancer =new ControleurLancerPartie(this.modelePendu, this);
        this.bJouer.setOnAction(lancer);

         return centre;
     }

    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire){
        for (int i=0; i<this.modelePendu.getNbErreursMax()+1; i++){
            File file = new File(repertoire+"/pendu"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    public void modeAccueil(){
        // A implementer
        this.panelCentral.setCenter(fenetreAccueil()); //mettre les partie quest ce qui vas changer
        this.boutonMaison.setDisable(true);
        this.boutonParametres.setDisable(false);
        this.boutonjeu.setDisable(false);
        



    }
    
    public void modeJeu(){
        // A implementer
        this.panelCentral.setCenter(fenetreJeu());
        this.boutonjeu.setDisable(true);
        this.boutonMaison.setDisable(false);
        this.boutonParametres.setDisable(false);

    }
    
    public void modeParametres(){
        // A implémenter
        this.panelCentral.setCenter(parametre()); //mettre les partie quest ce qui vas changer
        this.boutonMaison.setDisable(false);
        this.boutonParametres.setDisable(true);
        this.boutonjeu.setDisable(false);
        

    }

    /** lance une partie */
    public void lancePartie(){
        // A implementer
        this.modeJeu();
        
    }


    public void rinit(){
        int niveau = this.modelePendu.getNiveau();
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, niveau, 10);
        ControleurLettres controleurL = new ControleurLettres(this.modelePendu,this);
        this.clavier = new Clavier("abcdefghijklmnopqrstuvwxyz",controleurL);
    }


    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        // A implementer
        
        float restant = ((this.modelePendu.getNbErreursRestants())/(this.modelePendu.getNbErreursMax()));

        
        if (restant == 1 ) {this.dessin.setImage(this.lesImages.get(0));                    this.pg.setProgress(0);}
        if (restant < 1 && restant > 0.9 ) {this.dessin.setImage(this.lesImages.get(1));    this.pg.setProgress(0.1f);}
        if (restant <  0.9 && restant > 0.8) {this.dessin.setImage(this.lesImages.get(2));  this.pg.setProgress(0.2f);}
        if (restant <  0.8 && restant > 0.7) {this.dessin.setImage(this.lesImages.get(3));  this.pg.setProgress(0.3f);}
        if (restant <  0.7 && restant > 0.6) {this.dessin.setImage(this.lesImages.get(4));  this.pg.setProgress(0.4f);}
        if (restant <  0.6 && restant > 0.5) {this.dessin.setImage(this.lesImages.get(5));  this.pg.setProgress(0.5f);}
        if (restant <  0.5 && restant > 0.4) {this.dessin.setImage(this.lesImages.get(6));  this.pg.setProgress(0.6f);}
        if (restant <  0.4 && restant > 0.3) {this.dessin.setImage(this.lesImages.get(7));  this.pg.setProgress(0.7f);}
        if (restant <  0.3 && restant > 0.2) {this.dessin.setImage(this.lesImages.get(8));  this.pg.setProgress(0.8f);}
        if (restant <  0.2 && restant > 0.1) {this.dessin.setImage(this.lesImages.get(9));  this.pg.setProgress(0.9f);}
        if (restant == 0) {this.dessin.setImage(this.lesImages.get(10));                    this.pg.setProgress(1);}


        this.motCrypte.setText(this.modelePendu.getMotCrypte());
        System.out.println(this.motCrypte);
        

        if (this.modelePendu.perdu()) {
            popperdu();
        }
        if (this.modelePendu.gagne()) {
            popUpgagner();
        }
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
        // A implémenter
        return null; // A enlever
    }

    public Alert popUpLancerPartie(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie vas commencer!\n Etes-vous sûr de vouloir jouer ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }


    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }
       
    public Alert popperdu(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"vous avez perdu");
        alert.setTitle("c'est perdu");
        return alert;
    }
    public Alert popUpgagner(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"vous avez gagner");
        alert.setTitle("c'est gagner");
        return alert;
    }

    public Alert popUpReglesDuJeu(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert de type INFORMATION");
        alert.setHeaderText("regle: le jeu pendu");
        alert.setContentText("deviner le mot c'est gagner\ntrop de mauvaise lettre c'est perdu\ncliquer sur le clavier pour choisir une lettre");    
        return alert;
    }
    
    public Alert popUpMessageGagne(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("c'est gagné,c'est gagné");
        alert.setHeaderText("c'est gagné,oui c'est gagné");
        alert.setContentText("c'est gagné,c'est gagné\nc'est gagné,c'est gagné\nc'est gagné,c'est gagné");
        return alert;
    }
    
    public Alert popUpMessagePerdu(){
        // A implementer    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        return alert;
    }

    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        stage.setScene(this.laScene());
        this.modeAccueil();
        stage.show();
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
