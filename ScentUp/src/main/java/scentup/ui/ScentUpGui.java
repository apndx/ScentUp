/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scentup.ui;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import scentup.dao.Database;
import scentup.dao.ScentDao;
import scentup.dao.UserDao;
import scentup.dao.UserScentDao;
import scentup.domain.Scent;
import scentup.domain.ScentUpService;

/**
 *
 * @author hdheli
 */
public class ScentUpGui extends Application {

    private ScentUpService scentUpService;
    
    private VBox scentNodes;
    private Scene scentScene;
    private Scene newUserScene;
    private Scene loginScene;
    private Label menuLabel;

    @Override
    public void init() throws ClassNotFoundException {
        File file = new File("ScentUp.db");
        Database database = new Database("jdbc:sqlite:" + file.getAbsolutePath());
        database.init();

        UserDao users = new UserDao(database);
        ScentDao scents = new ScentDao(database);
        UserScentDao userScents = new UserScentDao(database);
      
        scentUpService = new ScentUpService(users, scents, userScents);
        menuLabel = new Label();
    }

    public void redrawUserHasScentsList() {
        // this needs to be implemented
   
        scentNodes.getChildren().clear();    

        List<Scent> scentsUserHas = scentUpService.getScentsUserHas();
        scentsUserHas.forEach(scent -> {
           scentNodes.getChildren().add(createScentNode(scent));
        });
    }
    
        public Node createScentNode(Scent scent) {
        HBox box = new HBox(10);
        Label label  = new Label(scent.toString());
        label.setMinHeight(28);
        //Button button = new Button("done");
//        button.setOnAction(e->{
//            todoService.markDone(todo.getId());
//            redrawTodolist();
//        });
                
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(0,5,0,5));
        
        box.getChildren().addAll(label, spacer);
        return box;
               
        }
    
    

    @Override
    public void start(Stage primaryStage) {

       

        // place for all stuff on the first page
        VBox loginPane = new VBox(10);

        //this is a way to put an image, not good for a background
        //Image rose = new Image("file:rose.jpg");
        //ImageView pic = new ImageView(rose);
        //Pane screen = new Pane();
        //screen.getChildren().add(pic);
        // a better solution for a backgroundimage? nope
//        BackgroundImage myBI = new BackgroundImage(new Image("file:rose.jpg", 32, 32, false, true),
//                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
//                BackgroundSize.DEFAULT);
        // stuff needed to add new user
        TextField userAdd = new TextField("username");
        TextField nameAdd = new TextField("name");

        Button newUserButton = new Button("Add a new User");  //add a new user button
        newUserButton.setOnAction((event) -> {

            System.out.println("Painettu!");
        });

        HBox addUserGroup = new HBox(10);
        addUserGroup.setSpacing(20);
        addUserGroup.getChildren().addAll(newUserButton);

        // stuff needed to login
        //componentGroup.getChildren().add(loginButton);
        HBox loginGroup = new HBox(10);

        TextField userNameLogin = new TextField("username");
        Button loginButton = new Button("ScentIn");  //loginbutton
        Label loginMessage = new Label();

        loginGroup.setSpacing(20);
        loginGroup.getChildren().addAll(userNameLogin, loginButton);

        loginButton.setOnAction(e -> {

            // this does not really work atm
            // todo!!
            String username = userNameLogin.getText();
            menuLabel.setText("Welcome " + username + "! Here is your current collection: ");
            //menuLabel.setText(username + " logged in...");
            try {
                if (scentUpService.login(username)) {
                    loginMessage.setText("");
                    redrawUserHasScentsList();
                    primaryStage.setScene(scentScene);
                    userNameLogin.setText("");
                } else {
                    loginMessage.setText("use does not exist");
                    loginMessage.setTextFill(Color.RED);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ScentUpGui.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Painettu!");
        });

        // add a new scent takes to another page, button for it
        Button newScentButton = new Button("Add a new Scent");  //add a new scent button
        newUserButton.setOnAction((event) -> {

            System.out.println("Painettu!");
        });

        HBox scentGroup = new HBox(10);
        scentGroup.getChildren().addAll(newScentButton);

        // this is where we put things together
        loginPane.getChildren().addAll(loginGroup, addUserGroup, newScentButton);

        loginScene = new Scene(loginPane);

        //scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        //primaryStage.setScene(scene);
        //primaryStage.show();
        
        
        
        // seutp primary stage
        
        primaryStage.setTitle("ScentUp");
        primaryStage.setScene(loginScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
            System.out.println("closing");
            System.out.println(scentUpService.getLoggedIn());
            if (scentUpService.getLoggedIn()!=null) {
                e.consume();   
            }
            
        });
        
        
        
         // main scene
        
        ScrollPane scentScrollbar = new ScrollPane();       
        BorderPane mainPane = new BorderPane(scentScrollbar);
        scentScene = new Scene(mainPane, 300, 250);
        
        HBox menuPane = new HBox(10);    
        Region menuSpacer = new Region();
        HBox.setHgrow(menuSpacer, Priority.ALWAYS);
        Button logoutButton = new Button("logout"); 
          menuPane.getChildren().addAll(menuLabel, menuSpacer, logoutButton);
        logoutButton.setOnAction(e->{
            scentUpService.logout();
            primaryStage.setScene(loginScene);
        });  
        
        scentNodes = new VBox(10);
        scentNodes.setMaxWidth(280);
        scentNodes.setMinWidth(280);
        redrawUserHasScentsList();
        
        scentScrollbar.setContent(scentNodes);
        mainPane.setTop(menuPane);
          

    }

    @Override
    public void stop() {
        // closing procedures
        System.out.println("Closing ScentUp");
    }

}
