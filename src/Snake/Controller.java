package Snake;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller{

    // Menu Buttons
    @FXML private Button buttonSinglePlayer;
    @FXML private Button buttonTwoPlayer;
    @FXML private Button buttonInstructions;
    @FXML private Button buttonExit;
    // Instructions Buttons
    @FXML private Button buttonBackToMenu;

    public void actionSinglePlayer(ActionEvent event) throws Exception {
        Snake snake = new Snake();
        snake.startSnake((Stage) buttonSinglePlayer.getScene().getWindow());

        /*Stage stage = (Stage) buttonSinglePlayer.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(Snake.class);
        Parent root = fxmlLoader.load(getClass().getResource("../Snake/Snake.fxml"));
        stage.setTitle("Snake");
        stage.setScene(new Scene(root, 600, 400));*/
        System.out.print("Single Player ");
    }

    public void actionTwoPlayer(ActionEvent event) throws Exception {
        Stage stage = (Stage) buttonTwoPlayer.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(Snake.class);
        Parent root = fxmlLoader.load(getClass().getResource("../Snake/Snake.fxml"));
        stage.setTitle("Snake");
        stage.setScene(new Scene(root, 600, 400));
        System.out.print("Two Player ");
    }

    public void actionInstructions(ActionEvent event) throws Exception {
        Stage stage = (Stage) buttonInstructions.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(Controller.class);
        Parent root = fxmlLoader.load(getClass().getResource("../Snake/Instructions.fxml"));
        stage.setTitle("Snake");
        stage.setScene(new Scene(root, 600, 400));
        System.out.print("Instructions ");
    }

    public void actionExit(ActionEvent event) {
        Stage stage = (Stage) buttonExit.getScene().getWindow();
        stage.close();
        System.out.print("Exit ");
    }

    public void actionBackToMenu(ActionEvent event) throws Exception {
        Stage stage = (Stage) buttonBackToMenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(Controller.class);
        Parent root = fxmlLoader.load(getClass().getResource("../Snake/Menu.fxml"));
        stage.setTitle("Snake");
        stage.setScene(new Scene(root, 600, 400));
        System.out.print("Back To Menu From Instructions ");
    }

}

/*
    Technical Thoughts:
    1. Remember fx:controller="Snake.Controller"
                        (Project's Name.Controller Class)
    2. In start() getResource("../Snake/Menu.fxml"));
     - Specify the .fxml file's location

*/