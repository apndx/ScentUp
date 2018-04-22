/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scentup.ui;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import scentup.dao.Database;
import scentup.dao.ScentDao;
import scentup.dao.UserDao;
import scentup.dao.UserScentDao;
import scentup.domain.ScentUpService;

/**
 *
 * @author hdheli
 */
public class ScentUpGui extends Application {

    private ScentUpService scentUpService;

    @Override
    public void init() throws ClassNotFoundException {
        File file = new File("ScentUp.db");
        Database database = new Database("jdbc:sqlite:" + file.getAbsolutePath());
        database.init();

        UserDao users = new UserDao(database);
        ScentDao scents = new ScentDao(database);
        UserScentDao userScents = new UserScentDao(database);

        scentUpService = new ScentUpService(users, scents, userScents);

    }

    @Override
    public void start(Stage firstStage) {

        firstStage.setTitle("ScentUp");

        //this is a way to put an image, not good for a background
        //Image rose = new Image("file:rose.jpg");
        //ImageView pic = new ImageView(rose);
        //Pane screen = new Pane();
        //screen.getChildren().add(pic);
        // a better solution for a backgroundimage? nope
//        BackgroundImage myBI = new BackgroundImage(new Image("file:rose.jpg", 32, 32, false, true),
//                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
//                BackgroundSize.DEFAULT);
        //add a new user button
        Button newUserButton = new Button("Add a new User");
        newUserButton.setOnAction((event) -> {

            System.out.println("Painettu!");
        });

        // username and name textfields
        TextField userName = new TextField("username");
        TextField name = new TextField("name");

        HBox componentGroup = new HBox();
       

        //componentGroup.getChildren().add(loginButton);
        componentGroup.setSpacing(20);
        componentGroup.getChildren().addAll(userName,  name, newUserButton);

        Scene scene = new Scene(componentGroup);

        //scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        firstStage.setScene(scene);
        firstStage.show();

    }

    @Override
    public void stop() {
        // closing procedures
        System.out.println("Closing ScentUp");
    }

}
