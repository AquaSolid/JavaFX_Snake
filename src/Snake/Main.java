package Snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(Controller.class);
        Parent root = fxmlLoader.load(this.getClass().getResource("../Snake/Menu.fxml"));
        primaryStage.setTitle("Snake");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }


}

/*
 Specific Project Thoughts

 Measure twice, cut once!
 C'est la vie:
 - Menu:
    1. Single Player
    2. Two Player (Soon)
    3. Instructions
    4. Exit

 Requirements:
 1. Snake = Player-controlled object
 2. Snacks = Computer-RNG objects
 3. Surface = Computer-initially generated object
 4. Points = Automated score keeper
 5. Time = Automated timer

 Actions:
 1. Eating = The snake consumes a snack.
 2. Point keeping.
 3. Time keeping
 4. Game keeper = is the game over and if not, should it be?

 Architecture:
 - Each of the requirements shall require their own separate class.
 Classes:
   1. Snake.class
   2. Snacks.class
   3. Surface.class (Points and Time shall be labels inside)
   **Perhaps Snake and Snacks should be one alongside with the game logic.

*/