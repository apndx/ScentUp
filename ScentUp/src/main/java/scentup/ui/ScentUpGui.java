package scentup.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
import scentup.domain.UserScent;

/**
 * This is the graphic user interface for ScentUp
 *
 * @author apndx
 */
public class ScentUpGui extends Application {

    private ScentUpService scentUpService;
    private VBox scentNodes;
    private VBox browseableScentNodes;
    private VBox userScentNodes;
    private Scene sceneLoggedIn;
    private Scene newUserScene;
    private Scene loginScene;
    private Scene newScentScene;
    private Scene browseScene;
    private Label menuLabel;
    private Label browseMenuLabel;

    @Override
    public void init() throws ClassNotFoundException, FileNotFoundException, IOException {

        Properties properties = new Properties();
        //file in the root folder
        File config = new File("config.properties");

        if (!config.exists()) {
            config.createNewFile();
            Path path = Paths.get("config.properties");
            Files.write(path, Arrays.asList("database=ScentUp.db"), Charset.forName("UTF-8"));
        }

        properties.load(new FileInputStream("config.properties"));
        String databaseFromConfig = properties.getProperty("database");
        File file = new File(databaseFromConfig);
        Database database = new Database("jdbc:sqlite:" + file.getAbsolutePath());
        database.init();

        //creating Daos
        UserDao users = new UserDao(database);
        ScentDao scents = new ScentDao(database);
        UserScentDao userScents = new UserScentDao(database);

        scentUpService = new ScentUpService(users, scents, userScents);
        menuLabel = new Label();
        browseMenuLabel = new Label();
    }

    /**
     * Renders the scent list of the UserScents for the user logged in
     *
     */
    public void redrawUserHasScentsList() {

        scentNodes.getChildren().clear();

        List<Scent> scentsUserHas = scentUpService.getScentsUserHas();
        scentsUserHas.forEach(scent -> {
            scentNodes.getChildren().add(createScentNode(scent));
        });
    }

    /**
     * Renders the userScent list of the UserScents for the user logged in
     *
     */
    public void redrawUserHasUserScentsList() {

        userScentNodes.getChildren().clear();

        List<UserScent> userScentsUserHas = scentUpService.getUserScentListforUser();
        userScentsUserHas.forEach(userScent -> {
            userScentNodes.getChildren().add(createUserScentNode(userScent));
        });
    }

    /**
     * Creates a scent node for add the new scent scene
     *
     * @param scent the scent that is node is created for
     * @return Node returns a scent node
     */
    public Node createScentNode(Scent scent) {
        HBox box = new HBox(10);
        Label label = new Label(scent.toString());
        label.setMinHeight(28);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(0, 5, 0, 5));

        box.getChildren().addAll(label, spacer);
        return box;
    }

    /**
     * Renders the scent list of the scents the logged in user does not have
     *
     */
    public void redrawUserHasNotScentsList() {

        browseableScentNodes.getChildren().clear();

        List<Scent> scentsUserHasNot = scentUpService.getScentsUserHasNot();
        scentsUserHasNot.forEach(scent -> {
            browseableScentNodes.getChildren().add(createScentNodeToBrowse(scent));
        });
    }

    /**
     * Creates a scent node for add the browse scene
     *
     * @param scent the scent that is node is created for
     * @return Node returns a scent node
     */
    public Node createScentNodeToBrowse(Scent scent) {
        HBox box = new HBox(10);
        Label label = new Label(scent.toString());
        label.setMinHeight(28);

        Button button = new Button("I want");
        button.setOnAction((ActionEvent e) -> {
            try {
                scentUpService.createUserScent(scentUpService.getLoggedIn().getUserId(), scent.getScentId(),
                        2, 1);
            } catch (SQLException ex) {
                Logger.getLogger(ScentUpGui.class.getName()).log(Level.SEVERE, null, ex);
            }
            //redrawUserHasScentsList();
            redrawUserHasNotScentsList();
            redrawUserHasUserScentsList();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(0, 5, 0, 5));

        box.getChildren().addAll(label, spacer, button);
        return box;
    }

    /**
     * Creates a userScent node for the user collection
     *
     * @param userScent the userScent that is node is created for
     * @return Node returns a userScent node
     */
    public Node createUserScentNode(UserScent userScent) {
        HBox box = new HBox(10);
        Label label = new Label(userScent.toString());
        label.setMinHeight(28);

        MenuItem menuItem1 = new MenuItem("dislike");
        MenuItem menuItem2 = new MenuItem("neutral");
        MenuItem menuItem3 = new MenuItem("love");

        MenuButton menuButton = new MenuButton(userScent.userScentPreferenceString(userScent.getPreference()),
                null, menuItem1, menuItem2, menuItem3);

        menuItem1.setOnAction(event -> {
            userScent.setPreference(1);
            try {
                scentUpService.changePreference(userScent, 1);
            } catch (SQLException ex) {
                Logger.getLogger(ScentUpGui.class.getName()).log(Level.SEVERE, null, ex);
            }
            redrawUserHasNotScentsList();
            redrawUserHasUserScentsList();

        });

        menuItem2.setOnAction(event -> {
            userScent.setPreference(2);
            try {
                scentUpService.changePreference(userScent, 2);
            } catch (SQLException ex) {
                Logger.getLogger(ScentUpGui.class.getName()).log(Level.SEVERE, null, ex);
            }
            redrawUserHasNotScentsList();
            redrawUserHasUserScentsList();

        });

        menuItem3.setOnAction(event -> {
            userScent.setPreference(3);
            try {
                scentUpService.changePreference(userScent, 3);
            } catch (SQLException ex) {
                Logger.getLogger(ScentUpGui.class.getName()).log(Level.SEVERE, null, ex);
            }
            redrawUserHasNotScentsList();
            redrawUserHasUserScentsList();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(0, 5, 0, 5));

        box.getChildren().addAll(label, spacer, menuButton);
        return box;
    }

    @Override
    public void start(Stage primaryStage) {

        // place for all stuff on the first page
        VBox loginPane = new VBox(10);
        Label loginMessage = new Label();

        // stuff needed to login, add user's collection page
        HBox loginGroup = new HBox(10);
        TextField userNameLogin = new TextField("username");
        Button loginButton = new Button("ScentIn");  //loginbutton
        loginGroup.setSpacing(20);
        loginGroup.getChildren().addAll(userNameLogin, loginButton);

        loginButton.setOnAction(e -> {

            // first setup
            String username = userNameLogin.getText();
            menuLabel.setText("Welcome " + username + "! Here is your current collection: ");
            try {
                if (scentUpService.login(username)) {
                    loginMessage.setText("");
                    redrawUserHasNotScentsList();
                    redrawUserHasUserScentsList();
                    primaryStage.setScene(sceneLoggedIn);
                    userNameLogin.setText("");
                } else {
                    loginMessage.setText("user does not exist");
                    loginMessage.setTextFill(Color.RED);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ScentUpGui.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        // add a new scent takes to another page, button for it
        Button newScentButton = new Button("Add a new Scent");  //add a new scent button

        newScentButton.setOnAction((event) -> {

            userNameLogin.setText("");
            primaryStage.setScene(newScentScene);

        });

        Button addNewUserButton = new Button("Add a new User");
        addNewUserButton.setOnAction((event) -> {

            userNameLogin.setText("");
            primaryStage.setScene(newUserScene);

        });

        loginPane.getChildren().addAll(loginMessage, loginGroup, addNewUserButton, newScentButton);
        loginScene = new Scene(loginPane);

        
        
        
        // stuff needed for add new user page
        VBox newUserPane = new VBox(10);

        HBox newUsernamePane = new HBox(10);     //usernamestuff here
        newUsernamePane.setPadding(new Insets(10));
        TextField userAddInput = new TextField();
        Label newUsernameLabel = new Label("username");
        newUsernameLabel.setPrefWidth(100);
        newUsernamePane.getChildren().addAll(newUsernameLabel, userAddInput);

        HBox newNamePane = new HBox(10);
        newNamePane.setPadding(new Insets(10));
        TextField newNameInput = new TextField();
        Label newNameLabel = new Label("name");
        newNameLabel.setPrefWidth(100);
        newNamePane.getChildren().addAll(newNameLabel, newNameInput);

        Label creationMessage = new Label();

        Button createUserButton = new Button("Add a new User");  //add a new user button
        Button outFromCreateUserButton = new Button("back");
        createUserButton.setPadding(new Insets(10));
        outFromCreateUserButton.setPadding(new Insets(10));
        outFromCreateUserButton.setOnAction(e -> {

            primaryStage.setScene(loginScene);
        });
        createUserButton.setOnAction((event) -> {
            String username = userAddInput.getText();
            String name = newNameInput.getText();

            if (username.length() < 5 || name.length() < 2) {
                creationMessage.setText("username or name too short");
                creationMessage.setTextFill(Color.RED);
            } else {
                try {
                    if (scentUpService.createUser(username, name)) {
                        creationMessage.setText("");
                        loginMessage.setText("new user created");
                        loginMessage.setTextFill(Color.GREEN);
                        primaryStage.setScene(loginScene);
                    } else {
                        creationMessage.setText("username has to be unique");
                        creationMessage.setTextFill(Color.RED);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ScentUpGui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        newUserPane.getChildren().addAll(creationMessage, newUsernamePane, newNamePane, createUserButton, outFromCreateUserButton);
        newUserScene = new Scene(newUserPane, 400, 250);

        
        
        
        // stuff for new scent page
        VBox newScentPane = new VBox(10); // group pane for all the elements

        HBox newScentNamePane = new HBox(10);  // scent name
        newScentNamePane.setPadding(new Insets(10));
        TextField ScentNameInput = new TextField();
        Label newScentNameLabel = new Label("Name");
        newScentNameLabel.setPrefWidth(100);
        newScentNamePane.getChildren().addAll(newScentNameLabel, ScentNameInput);

        HBox newScentBrandPane = new HBox(10);  // scent brand
        newScentBrandPane.setPadding(new Insets(10));
        TextField scentBrandInput = new TextField();
        Label newScentBrandLabel = new Label("Brand");
        newScentBrandLabel.setPrefWidth(100);
        newScentBrandPane.getChildren().addAll(newScentBrandLabel, scentBrandInput);

        HBox newScentTimeOfDayPane = new HBox(10);
        ToggleGroup scentTimeofDayChoices = new ToggleGroup();   // scent time of day
        Label newTimeOLabel = new Label("When to use this?");
        RadioButton day = new RadioButton("day");
        RadioButton night = new RadioButton("night");
        day.setToggleGroup(scentTimeofDayChoices);
        night.setToggleGroup(scentTimeofDayChoices);
        day.setSelected(true);  //  is this needed?
        newScentTimeOfDayPane.getChildren().addAll(newTimeOLabel, day, night);

        HBox newScentSeasonPane = new HBox(10);
        ToggleGroup scentSeasonChoices = new ToggleGroup();   // scent season
        Label newSeasonLabel = new Label("Season?");
        RadioButton winter = new RadioButton("winter");
        RadioButton spring = new RadioButton("spring");
        RadioButton summer = new RadioButton("summer");
        RadioButton autumn = new RadioButton("autumn");
        winter.setToggleGroup(scentSeasonChoices);
        spring.setToggleGroup(scentSeasonChoices);
        summer.setToggleGroup(scentSeasonChoices);
        autumn.setToggleGroup(scentSeasonChoices);
        winter.setSelected(true);
        newScentSeasonPane.getChildren().addAll(newSeasonLabel, winter, spring, summer, autumn);

        HBox newScentGenderPane = new HBox(10);
        ToggleGroup scentGenderChoices = new ToggleGroup();   // scent gender
        Label newGenderLabel = new Label("Gender stereotypes?");
        RadioButton female = new RadioButton("female");
        RadioButton male = new RadioButton("male");
        RadioButton unisex = new RadioButton("unisex");
        female.setToggleGroup(scentGenderChoices);
        male.setToggleGroup(scentGenderChoices);
        unisex.setToggleGroup(scentGenderChoices);
        female.setSelected(true);
        newScentGenderPane.getChildren().addAll(newGenderLabel, female, male, unisex);

        Button outOfScentCreation = new Button("back");
        outOfScentCreation.setPadding(new Insets(10));

        outOfScentCreation.setOnAction(e -> {

            if (scentUpService.getLoggedIn() == null) {
                primaryStage.setScene(loginScene);
            } else {

                primaryStage.setScene(browseScene);
            }

        });

        Button createTheScentButton = new Button("I'm ready, let's do it!");
        createTheScentButton.setPadding(new Insets(10));

        createTheScentButton.setOnAction((event) -> {
            String scentname = ScentNameInput.getText();
            String scentbrand = scentBrandInput.getText();

            if (scentname.length() < 1 || scentbrand.length() < 1) {
                creationMessage.setText("username or name too short");
                creationMessage.setTextFill(Color.RED);
            } else {
                try {
                    if (!scentUpService.doesScentExist(scentname, scentbrand)) {
                        Integer timeOfDay = 0;
                        Integer season = 0;
                        Integer gender = 0;
                        //luodaan scent
                        if (scentTimeofDayChoices.getSelectedToggle() == day) {
                            timeOfDay = 1;
                        } else {
                            timeOfDay = 2;
                        }
                        if (scentSeasonChoices.getSelectedToggle() == winter) {
                            season = 1;
                        } else if (scentSeasonChoices.getSelectedToggle() == spring) {
                            season = 2;
                        } else if (scentSeasonChoices.getSelectedToggle() == summer) {
                            season = 3;
                        } else {
                            season = 4;
                        }

                        if (scentGenderChoices.getSelectedToggle() == female) {
                            gender = 1;
                        } else if (scentGenderChoices.getSelectedToggle() == male) {
                            gender = 2;
                        } else {
                            gender = 3;
                        }
                        Scent scentAdded = new Scent(null, scentname, scentbrand, timeOfDay,
                                season, gender);
                        scentUpService.createScent(scentAdded);
                        creationMessage.setText("");

                        loginMessage.setTextFill(Color.GREEN);
                        if (scentUpService.getLoggedIn() == null) {
                            ScentNameInput.setText("");
                            scentBrandInput.setText("");
                            loginMessage.setText("new scent added");
                            primaryStage.setScene(loginScene);
                        } else {
                            ScentNameInput.setText("");
                            scentBrandInput.setText("");
                            redrawUserHasNotScentsList();
                            primaryStage.setScene(browseScene);
                        }

                    } else {
                        creationMessage.setText("scent has to be unique");
                        creationMessage.setTextFill(Color.RED);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ScentUpGui.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        newScentPane.getChildren().addAll(newScentNamePane, newScentBrandPane,
                newScentTimeOfDayPane, newScentSeasonPane, newScentGenderPane, createTheScentButton, outOfScentCreation);  // putting  all scent stuff together

        newScentScene = new Scene(newScentPane, 400, 300);

        
        
        
        
        // main scene, logged in
        ScrollPane scentScrollbar = new ScrollPane();
        BorderPane mainPane = new BorderPane(scentScrollbar);
        sceneLoggedIn = new Scene(mainPane, 500, 250);

        HBox menuPane = new HBox(10);
        Region menuSpacer = new Region();
        HBox.setHgrow(menuSpacer, Priority.ALWAYS);
        Button logoutButton = new Button("ScentOut");
        Button browseButton = new Button("Browse");
        menuPane.getChildren().addAll(menuLabel, menuSpacer, browseButton, logoutButton);
        logoutButton.setOnAction(e -> {
            scentUpService.logout();
            primaryStage.setScene(loginScene);
        });

        browseButton.setOnAction(e -> {
            browseMenuLabel.setText("Choose a new scent for your collection");
            primaryStage.setScene(browseScene);

        });

        // this does userscent list
        userScentNodes = new VBox(10);
        userScentNodes.setMaxWidth(500);
        userScentNodes.setMinWidth(280);
        redrawUserHasUserScentsList();
        scentScrollbar.setContent(userScentNodes);
        mainPane.setTop(menuPane);
        
        
        
        
        //browse page 
        ScrollPane browseScrollbar = new ScrollPane();
        BorderPane browsePane = new BorderPane(browseScrollbar);
        browseScene = new Scene(browsePane);

        HBox browseMenuPane = new HBox(10);
        Region browseMenuSpacer = new Region();
        HBox.setHgrow(browseMenuSpacer, Priority.ALWAYS);
        Button outBrowseButton = new Button("Back");
        Button addToDatabase = new Button("It's not on the list");

        browseMenuPane.getChildren().addAll(browseMenuLabel, browseMenuSpacer, addToDatabase, outBrowseButton);

        outBrowseButton.setOnAction(e -> {

            primaryStage.setScene(sceneLoggedIn);
        });

        //add a new scent because what you want is not on the list
        addToDatabase.setOnAction(e -> {

            primaryStage.setScene(newScentScene);
        });

        browseableScentNodes = new VBox(10);
        browseableScentNodes.setMaxWidth(500);
        browseableScentNodes.setMinWidth(280);
        redrawUserHasNotScentsList();
        redrawUserHasUserScentsList();

        browseScrollbar.setContent(browseableScentNodes);
        browsePane.setTop(browseMenuPane);

        
        
        
        // seutp primary stage
        primaryStage.setTitle("ScentUp");
        primaryStage.setScene(loginScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {

            if (scentUpService.getLoggedIn() != null) {
                e.consume();
            }
        });
    }

    @Override
    public void stop() {
        // closing procedures
        System.out.println("Closing ScentUp");
    }

    public static void main(String[] args) {
        launch(args);
    }

}
